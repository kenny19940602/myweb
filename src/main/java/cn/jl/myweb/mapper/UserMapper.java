package cn.jl.myweb.mapper;

import cn.jl.myweb.entity.User;

/**
 * 处理用户数据的持久层接口
 */
public interface UserMapper {
	
	/**
	 * 插入用户数据
	 * @param user 要插入的用户数据
	 * @return 受影响的行数
	 */
	Integer insert(User user);

	/**
	 * 根据用户名查找用户数据
	 * @param username 要查找的用户名
	 * @return 匹配的用户数据，如果没有匹配的数据，则返回null
	 */
	User findByUsername(String username);

}
