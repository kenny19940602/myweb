package cn.jl.myweb.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;

import cn.jl.myweb.service.ex.InsertException;
import cn.jl.myweb.service.ex.PasswordNotMatchException;
import cn.jl.myweb.service.ex.ServiceException;
import cn.jl.myweb.service.ex.UserNotFoundException;
import cn.jl.myweb.service.ex.UsernameDuplicateException;
import cn.jl.myweb.util.ResponseResult;

/**
 *	控制器类的基类 
 */
public abstract class BaseController {

	/**
	 *	相应结果的状态：成功
	 */
	public static final Integer SUCCESS = 200;
	
	/**
	 * 	统一处理异常
	 */
	@ExceptionHandler(ServiceException.class)
	public ResponseResult<Void> handleException(Throwable e){
		ResponseResult<Void> rr = new ResponseResult<Void>();
		rr.setMessage(e.getMessage());
		if(e instanceof UsernameDuplicateException) {
			rr.setState(400);//用户名已被占用异常
		}else if(e instanceof UserNotFoundException) {
			rr.setState(401);//用户名不存在
		}else if(e instanceof PasswordNotMatchException) {
			rr.setState(402);//用户密码输入有误
		}else if(e instanceof InsertException) {
			rr.setState(500);//插入数据异常
		}
		return rr;
		
	}
}
