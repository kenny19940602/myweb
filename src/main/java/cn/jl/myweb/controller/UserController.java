package cn.jl.myweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.jl.myweb.entity.User;
import cn.jl.myweb.service.IUserService;
import cn.jl.myweb.util.ResponseResult;

/**
 * 处理用户数据的控制器层
 */
@RestController
@RequestMapping("user")
public class UserController extends BaseController{
	
	@Autowired
	private IUserService service;
	
	@RequestMapping("reg")
	public ResponseResult<Void> reg(User user) {
		service.reg(user);
		return new ResponseResult<Void>(SUCCESS, "恭喜你注册成功！");
	}
	
	@RequestMapping("login")
	public ResponseResult<User> login(String username,String password){
		return new  ResponseResult<User>(SUCCESS, "登录成功！", service.login(username, password));
	}

}
