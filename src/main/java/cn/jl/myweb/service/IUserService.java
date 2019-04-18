package cn.jl.myweb.service;


import cn.jl.myweb.entity.User;
import cn.jl.myweb.service.ex.InsertException;
import cn.jl.myweb.service.ex.UsernameDuplicateException;

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

}
