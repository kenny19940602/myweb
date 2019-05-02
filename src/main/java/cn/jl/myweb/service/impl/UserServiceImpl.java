package cn.jl.myweb.service.impl;

import java.beans.IntrospectionException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.ibatis.annotations.Param;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import cn.jl.myweb.entity.ExcelBean;
import cn.jl.myweb.entity.User;
import cn.jl.myweb.mapper.UserMapper;
import cn.jl.myweb.service.IUserService;
import cn.jl.myweb.service.ex.InsertException;
import cn.jl.myweb.service.ex.PasswordNotMatchException;
import cn.jl.myweb.service.ex.UpdateException;
import cn.jl.myweb.service.ex.UserNotFoundException;
import cn.jl.myweb.service.ex.UsernameDuplicateException;
import cn.jl.myweb.util.DateUtils;
import cn.jl.myweb.util.ExcelUtils;
import cn.jl.myweb.util.Log;

/**
 * 处理用户数据的业务层实现类
 */
@Service
public class UserServiceImpl implements IUserService{

	@Autowired
	private UserMapper mapper;
	
	@Override
	public void reg(User user) throws InsertException, UsernameDuplicateException {
		// 根据尝试注册的用户名查询用户数据
		String username = user.getUsername();
		User result = findByUsername(username);
		// 检查用户名是否被占用：如果查询到数据，则表示被占用，如果查询结果为null，则表示用户名没有被占用
		if(result==null) {
			// 设置is_delete
			user.setIsDelete(0);
			// 设置4项日志
			user.setCreatedUser(username);
			user.setCreatedTime(new Date());
			user.setModifiedUser(username);
			user.setModifiedTime(new Date());
			// 生成随机盐
			String salt = UUID.randomUUID().toString().toUpperCase();
			// 执行密码加密，得到加密后的密码
			String md5Password = getMd5Password(salt,user.getPassword());
			// 将盐和加密后的密码封装到user中
			user.setSalt(salt);
			user.setPassword(md5Password);
			// 执行注册
			insert(user);
		}else {
			// 已占用：抛出UsernameDuplicateException
			throw new UsernameDuplicateException("注册失败！您尝试注册的用户名(" + username + ")已经被占用！");
			
		}
	}
	
	@Override
	public User login(String username, String password) throws UserNotFoundException, PasswordNotMatchException {
		 // 根据参数username查询用户：User findByUsername(String username)
		User user = findByUsername(username);
	    // 判断查询结果是否为null
		if(user==null) {
			// 是：抛出UserNotFoundException
			throw new UserNotFoundException("登录失败！尝试登录的用户不存在！");
		}
		// 判断is_delete是否标记为已删除：isDelete属性值是否为1
		if(user.getIsDelete()==1) {
			// 是：抛出UserNotFoundException
			throw new UserNotFoundException("登录失败！尝试登录的用户不存在！");
		}
		// 从查询结果中获取盐值
		String salt = user.getSalt();
		// 对参数password执行加密
		String md5Password = getMd5Password(salt, password);
	    // 判断查询结果中的密码与刚加密结果是否一致
		if(user.getPassword().equals(md5Password)) {
			// 是：
			// -- 返回查询结果
			user.setPassword(null);
			user.setSalt(null);
			user.setIsDelete(null);
			return user;
		}
		// 否：抛出PasswordNotMatchException
		throw new PasswordNotMatchException("登录失败！密码错误！");
			
	}

    @Override
    public User getByUid(Integer uid) {
        User result = findByUid(uid);
        if(result==null){
            throw  new UserNotFoundException("获取用户信息失败！尝试访问的用户不存在！");
        }
        if(result.getIsDelete().equals(1)){
            throw  new UserNotFoundException("获取用户信息失败！尝试访问的用户不存在！");
        }
        result.setIsDelete(null);
        result.setPassword(null);
        result.setSalt(null);
        return result;
    }

	@Override
	public void setPassword(Integer uid, String username, String oldPassword, String newPassword) throws UserNotFoundException, PasswordNotMatchException, UpdateException {
		User result = findByUid(uid);
		if(result==null){
			throw  new UserNotFoundException("用户修改密码失败！请检查登录是否超时！");
		}
		if(result.getIsDelete().equals(1)){
			throw  new UserNotFoundException("用户修改密码失败！请检查登录是否超时！");
		}
		String salt = result.getSalt();
		String oldMd5Password = getMd5Password(salt,oldPassword);
		if(!oldMd5Password.equals(result.getPassword())){
			throw  new PasswordNotMatchException("用户修改密码失败！用户密码错误！");
		}
		String newMd5Password = getMd5Password(salt,newPassword);
		updatePassword(uid,newMd5Password,username,new Date());

	}

    @Override
    public void changeUserInfo(User user) throws UserNotFoundException, UpdateException {
        User result = findByUid(user.getUid());
        if(result==null){
            throw new UserNotFoundException("修改用户信息失败！请检查登录是否超时！");
        }
        if(result.getIsDelete().equals(1)){
            throw new UserNotFoundException("修改用户信息失败！请检查登录是否超时！");
        }
        user.setModifiedUser(result.getUsername());
        user.setModifiedTime(new Date());
        updateUserInfo(user);
    }
    
    @Override
	public void changeAvatar(Integer uid, String avatar) throws UserNotFoundException, UpdateException {
    	User result = findByUid(uid);
    	if(result==null) {
    		throw new UserNotFoundException("修改头像失败！尝试访问的用户不存在！");
    	}
    	if(result.getIsDelete().equals(1)){
            throw new UserNotFoundException("修改头像失败！尝试访问的用户不存在！");
        }
    	updateAvatar(uid, avatar, result.getUsername(), new Date());
		
	}

    @Override
	public XSSFWorkbook exportUsersInfoExcel() {
    	XSSFWorkbook book=null;
		try {
			List<User> usersInfo = downLoadUsers();
			List<ExcelBean> ems=new ArrayList<>();
			Map<Integer,List<ExcelBean>>map=new LinkedHashMap<>();
			ems.add(new ExcelBean("用户uid","uid",0));
			ems.add(new ExcelBean("用户名","username",0));
			ems.add(new ExcelBean("密码","password",0));
			ems.add(new ExcelBean("盐值","salt",0));
			ems.add(new ExcelBean("性别","gender",0));
			ems.add(new ExcelBean("电话","phone",0));
			ems.add(new ExcelBean("邮箱","email",0));
			ems.add(new ExcelBean("头像","avatar",0));
			ems.add(new ExcelBean("是否删除","isDelete",0));
			ems.add(new ExcelBean("创建人","createdUser",0));
			ems.add(new ExcelBean("创建时间","createdTime",0));
			ems.add(new ExcelBean("修改人","modifiedUser",0));
			ems.add(new ExcelBean("修改时间","modifiedTime",0));
			map.put(0, ems);
//		List<User> afterChangeList=changeBuyerStatus(creditInfoList);
			book=ExcelUtils.createExcelFile(User.class, usersInfo, map, "用户数据表");
		} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException | ClassNotFoundException
				| IntrospectionException e) {
			Log.error("UserServiceImpl exportUserInfoExcel 失败！");
		}
		return book;
	};

	public void uploadUsersInfoExcel(InputStream in, MultipartFile file) throws Exception {
		List<List<Object>> listob = ExcelUtils.getBankListByExcel(in,file.getOriginalFilename());  
		List<User> usersInfo=new ArrayList<User>();
		for (int i = 0; i < listob.size(); i++) {  
	            List<Object> ob = listob.get(i);  
	            User user= new User();
//	            #{username}, #{password},
//				#{salt}, #{gender},
//				#{phone}, #{email},
//				#{avatar}, #{isDelete},
//				#{createdUser}, #{createdTime},
//				#{modifiedUser}, #{modifiedTime}
				user.setUsername(String.valueOf(ob.get(0)));
				user.setPassword(String.valueOf(ob.get(1)));
				user.setSalt(ob.get(2)+"");
				user.setGender(Integer.parseInt(String.valueOf(ob.get(3))));
				user.setPhone(String.valueOf(ob.get(4)));
				user.setEmail(String.valueOf(ob.get(5)));
				user.setAvatar(ob.get(6)+"");
				user.setIsDelete(Integer.parseInt(String.valueOf(ob.get(7))));
				user.setCreatedUser(String.valueOf(ob.get(8)));
				user.setCreatedTime(DateUtils.convertStrStyle(DateUtils.DEFAULT_DATE_STYLE, String.valueOf(ob.get(9))));
				user.setModifiedUser(String.valueOf(ob.get(10)));
				user.setModifiedTime(DateUtils.convertStrStyle(DateUtils.DEFAULT_DATE_STYLE, String.valueOf(ob.get(11))));
				usersInfo.add(user);
				System.out.println(user);
	        }
		for (User user : usersInfo) {
			mapper.insert(user);
		}
	}
    /**
	 * 获得MD5摘要算法后的密码
	 * @param salt 加密的盐值
	 * @param password 原始密码
	 * @return MD5后的摘要算法的密码
	 */
	private String getMd5Password(String salt, String password) {
		// 加密规则：使用“盐+密码+盐”作为原始数据，执行5次加密
		String md5Password = salt + password + salt;
		for (int i = 0; i < 5; i++) {
			md5Password = DigestUtils.md5DigestAsHex(md5Password.getBytes()).toUpperCase();
		}
		return md5Password;
	}

	/**
	 * 插入用户数据
	 * @param user 要插入的用户数据
	 */
	private void insert(User user) {
		Integer rows = mapper.insert(user);
		if(rows!=1) {
			throw new InsertException();
		}
	}

	/**
	 * 根据用户名查找用户数据
	 * @param username 要查找的用户名
	 */
	private User findByUsername(String username) {return mapper.findByUsername(username);}

	/**
	 * 根据用户uid查找用户数据
	 * @param uid 要查找用户信息的uid
	 * @return 查找到匹配的用户信息则返回信息，如果没有查询到用户信息则返回null
	 */
	private User findByUid(Integer uid){return mapper.findByUid(uid);}

	/**
	 * 导出所有用户数据
	 * @return 所有的用户数据
	 */
	private List<User> downLoadUsers(){return mapper.downLoadUsers();}
	
	/**
	 * 修改用户密码
	 * @param uid 用户的uid
	 * @param password 用户要更改的密码
	 * @param modifiedUser 更改用户名
	 * @param modifiedTime 更改时间
	 */
	private void updatePassword (
			Integer uid,
			String password,
			String modifiedUser,
			Date modifiedTime){
			Integer rows = mapper.updatePassword(uid,password,modifiedUser,modifiedTime);
			if(rows!=1){
				Log.error("修改用户数据发生异常");
				throw new UpdateException("发生未知错误！请重试！");
			}
	}

    /**
            * 修改用户信息
     * @param user 要修改的用户信息
     */
    private void updateUserInfo (User user){
        Integer rows = mapper.updateUserInfo(user);
        if(rows!=1){
            Log.error("修改用户信息发生异常");
            throw new UpdateException("发生未知错误！请重试！");
        }
    }
    
    /**
	 * 更新用户头像
	 * @param uid 要更新头像的用户uid
	 * @param avatar 要更新的头像
	 * @param modifiedUser 修改人
	 * @param modifiedTime 修改时间
	 * @return
	 */
	private void updateAvatar(
		    @Param("uid") Integer uid, 
		    @Param("avatar") String avatar, 
		    @Param("modifiedUser") String modifiedUser, 
		    @Param("modifiedTime") Date modifiedTime) {
		Integer rows = mapper.updateAvatar(uid, avatar, modifiedUser, modifiedTime);
		if(rows!=1) {
			throw new UpdateException("修改用户数据时出现未知错误！");
		}
	}


	

	
}
