package cn.jl.myweb.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.jl.myweb.entity.User;
import cn.jl.myweb.service.ex.ServiceException;

/**
 * 用户数据业务层测试类
 * @author JinLong
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTestCase {
	
	@Autowired
	public IUserService service;
	
	@Test
	public void reg() {
		try {
			User user = new User();
			user.setUsername("jin1");
			user.setPassword("123411");
			user.setGender(1);
			user.setPhone("13800138008");
			user.setEmail("upper@tedu.cn");
			user.setAvatar("http://www.tedu.cn/upper.png");
			service.reg(user);
		} catch (ServiceException e) {
			System.out.println(getClass().getName());
			System.err.println(e.getMessage());
		}
	}

	@Test
	public void changePassword() {
		try {
			Integer uid = 4;
			String username = "超级管理员";
			String oldPassword = "123411";
			String newPassword = "8888";
			service.setPassword(uid, username, oldPassword, newPassword);
			System.err.println("OK.");
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}

}
