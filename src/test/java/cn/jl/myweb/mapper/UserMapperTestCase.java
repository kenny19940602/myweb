package cn.jl.myweb.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.jl.myweb.entity.User;

import java.util.Date;


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
	public void updatePassword(){
		Integer rows = mapper.updatePassword(7,"123456","jinlong",new Date() );
		System.out.println(rows);
	}
	@Test
	public void updateUserInfo(){
		User user = new User();
		user.setPhone("136xxxx8006");
		user.setEmail("kenny19940602@163.com");
		user.setGender(1);
		user.setUid(5);
		user.setModifiedUser("超级管理员");
		user.setModifiedTime(new Date());
		Integer rows = mapper.updateUserInfo(user);
		System.out.println(rows);
	}

}
