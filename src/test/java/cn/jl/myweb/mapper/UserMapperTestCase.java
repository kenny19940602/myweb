package cn.jl.myweb.mapper;

import cn.jl.myweb.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;


/**
 * 用户数据持久层测试类
 * @author JinLong
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTestCase {
	
	@Autowired
	public UserMapper mapper;
	
	@Test
	public void insert() {
		User user = new User();
		user.setUsername("jinlong11");
		user.setPassword("1234");
		Integer rows = mapper.insert(user);
		System.out.println("rows: "+rows);
	}
	
	@Test
	public void findByName() {
		User user = mapper.findByUsername("jinlong11");
		System.out.println(user);
	}

	@Test
	public void findByUid(){
		User user = mapper.findByUid(7);
		System.out.println(user);
	}
	
	@Test
	public void downLoadUsers(){
		List<User> users = mapper.downLoadUsers();
		System.out.println(users);
	}


	@Test
	public void updatePassword(){
		Integer rows = mapper.updatePassword(7,"123456","jinlong",new Date() );
		System.out.println(rows);
	}
	
	@Test
	public void updateAvatar() {
	    Integer uid = 9;
	    String avatar = "这里应该是头像的路径";
	    String modifiedUser = "超级管理员";
	    Date modifiedTime = new Date();
	    Integer rows = mapper.updateAvatar(uid, avatar, modifiedUser, modifiedTime);
	    System.err.println("rows=" + rows);
	}

}
