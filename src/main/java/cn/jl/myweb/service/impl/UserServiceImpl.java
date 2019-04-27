package cn.jl.myweb.service.impl;

import cn.jl.myweb.entity.User;
import cn.jl.myweb.mapper.UserMapper;
import cn.jl.myweb.service.IUserService;
import cn.jl.myweb.service.ex.*;
import cn.jl.myweb.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

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
	 * 修改用户密码
	 * @param uid 用户的uid
	 * @param password 用户要更改的密码
	 * @param modifiedUser 更改用户名
	 * @param modifiedTime 更改时间
	 * @return 受影响的行数
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
}
