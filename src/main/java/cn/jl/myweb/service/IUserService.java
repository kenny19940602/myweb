package cn.jl.myweb.service;


import cn.jl.myweb.entity.User;
import cn.jl.myweb.service.ex.*;

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

}
