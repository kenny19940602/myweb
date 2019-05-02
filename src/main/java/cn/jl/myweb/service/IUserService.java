package cn.jl.myweb.service;


import cn.jl.myweb.entity.User;
import cn.jl.myweb.service.ex.*;

import java.io.InputStream;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

/**
 * 处理用户数据的业务层接口
 */
public interface IUserService {
	
	/**
	 * 用户注册
	 * @param user 注册的用户信息
	 * @throws InsertException 插入数据异常
	 * @throws UsernameDuplicateException 用户名已被占用异常
	 */
	void reg(User user)throws InsertException,UsernameDuplicateException;
	
	/**
	 * 用户登录
	 * @param username 登录的用户名
	 * @param password 登录的密码
	 * @return 用户的数据
	 * @throws UserNotFoundException 用户名不存在异常
	 * @throws PasswordNotMatchException 用户密码不正确异常
	 */
	User login(String username,String password) throws UserNotFoundException,PasswordNotMatchException;

	void  setPassword(Integer uid,String username,String oldPassword,String newPassword)throws UserNotFoundException,PasswordNotMatchException, UpdateException;

	/**
	 * 修改用户信息
	 * @param user 要修改的用户信息
	 * @throws UserNotFoundException 用户不存在的异常
	 * @throws UpdateException 修改用户密码的异常类
	 */
	void changeUserInfo(User user)throws  UserNotFoundException,UpdateException;

	/**
	 * 根据用户uid查询用户信息
	 * @param uid 要查询用户的uid
	 * @return 用户的信息
	 */
	User getByUid(Integer uid);

	/**
	 * 更新个人头像
	 * @param avatar 头像路径
	 * @throws UserNotFoundException 用户数据不存在
	 * @throws UpdateException 更新数据异常
	 */
	void changeAvatar(Integer uid, String avatar) 
	        throws UserNotFoundException, 
	            UpdateException;
	/**
	 * 导出所有用户数据
	 * @return 所有用户数据表格
	 */
	XSSFWorkbook exportUsersInfoExcel();
	
	/**
	 * 导入用户数据表
	 * @param in 文件流
	 * @param file 文件
	 * @throws Exception 导入异常
	 */
	void uploadUsersInfoExcel(InputStream in, MultipartFile file) throws Exception;

}
