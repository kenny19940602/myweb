package cn.jl.myweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



import cn.jl.myweb.entity.User;
import cn.jl.myweb.service.IUserService;
import cn.jl.myweb.util.ResponseResult;

import javax.servlet.http.HttpSession;

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
		return new ResponseResult<>(SUCCESS, "恭喜你注册成功！");
	}
	
	@RequestMapping("login")
	public ResponseResult<User> login(String username,String password,HttpSession session){
		User data = service.login(username,password);
		session.setAttribute("uid",data.getUid());
		session.setAttribute("username",data.getUsername());
		return new  ResponseResult<>(SUCCESS, "登录成功！",data);
	}

	@RequestMapping("info")
	public ResponseResult<User> showUserInfo(HttpSession session){
		Integer uid = getUidFromSession(session);
		User user = service.getByUid(uid);
		return new ResponseResult<>(SUCCESS,user);

	}
	@RequestMapping("change_password")
	public ResponseResult<Void> setPassword(@RequestParam("old_password")String oldPassword,
											@RequestParam("new_password")String newPassword,
											HttpSession session){
		Integer uid = getUidFromSession(session);
		String username = session.getAttribute("username").toString();
		service.setPassword(uid,username,oldPassword,newPassword);
		return new ResponseResult<>(SUCCESS);
	}

	@RequestMapping("change_info")
	public ResponseResult<Void> changeUserInfo(User user,HttpSession session){
		Integer uid = getUidFromSession(session);
		user.setUid(uid);
		service.changeUserInfo(user);
		return new ResponseResult<>(SUCCESS);
	}

}
