package cn.jl.myweb.controller;

import cn.jl.myweb.controller.ex.FileContentTypeException;
import cn.jl.myweb.controller.ex.FileEmptyException;
import cn.jl.myweb.controller.ex.FileIOException;
import cn.jl.myweb.controller.ex.FileIllegalStateException;
import cn.jl.myweb.controller.ex.FileSizeException;
import cn.jl.myweb.controller.ex.FileUploadException;
import cn.jl.myweb.service.ex.*;
import org.springframework.web.bind.annotation.ExceptionHandler;

import cn.jl.myweb.util.ResponseResult;

import javax.servlet.http.HttpSession;

/**
 *	控制器类的基类 
 */
public abstract class BaseController {

	/**
	 *	相应结果的状态：成功
	 */
	public static final Integer SUCCESS = 200;

	/**
	 * 从Session获取当前登录的用户id
	 * @param session HttpSession对象
	 * @return 当前登录的用户id
	 */
	protected final Integer getUidFromSession(HttpSession session){
		return Integer.valueOf(session.getAttribute("uid").toString());
	}
	/**
	 * 	统一处理异常
	 */
	@ExceptionHandler({ServiceException.class,FileUploadException.class})
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
		}else if(e instanceof UpdateException){
			rr.setState(501);//修改数据异常
		}else if(e instanceof FileEmptyException) {
			rr.setState(600);//文件为空的异常
		}else if(e instanceof FileSizeException) {
			rr.setState(601);//文件过大或过小的异常 
		}else if(e instanceof FileContentTypeException) {
			rr.setState(602);//文件类型不匹配的异常
		}else if(e instanceof FileIllegalStateException) {
			rr.setState(603);//文件状体的异常
		}else if(e instanceof FileIOException) {
			rr.setState(604);//文件IO异常
		}
		return rr;
		
	}
}
