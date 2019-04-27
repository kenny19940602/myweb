package cn.jl.myweb.mapper;

import cn.jl.myweb.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

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

	/**
	 * 修改用户密码
	 * @param uid 用户的uid
	 * @param password 用户要更改的密码
	 * @param modifiedUser 更改用户名
	 * @param modifiedTime 更改时间
	 * @return 受影响的行数
	 */
	Integer updatePassword (
			@Param("uid") Integer uid,
			@Param("password") String password,
			@Param("modifiedUser") String modifiedUser,
			@Param("modifiedTime") Date modifiedTime);

	/**
	 * 根据用户uid查找用户数据
	 * @param uid 要查找用户信息的uid
	 * @return 查找到匹配的用户信息则返回信息，如果没有查询到用户信息则返回null
	 */
	User findByUid(Integer uid);

}
