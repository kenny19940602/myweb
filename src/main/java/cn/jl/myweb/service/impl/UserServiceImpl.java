package cn.jl.myweb.service.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.jl.myweb.entity.User;
import cn.jl.myweb.mapper.UserMapper;
import cn.jl.myweb.service.IUserService;
import cn.jl.myweb.service.ex.InsertException;
import cn.jl.myweb.service.ex.UsernameDuplicateException;

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
	};

	/**
	 * 根据用户名查找用户数据
	 * @param username 要查找的用户名
	 */
	private User findByUsername(String username) {
		return mapper.findByUsername(username);
	};
}
