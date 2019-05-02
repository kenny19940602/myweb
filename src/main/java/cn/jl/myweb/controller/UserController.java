package cn.jl.myweb.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.jl.myweb.controller.ex.FileContentTypeException;
import cn.jl.myweb.controller.ex.FileEmptyException;
import cn.jl.myweb.controller.ex.FileIOException;
import cn.jl.myweb.controller.ex.FileIllegalStateException;
import cn.jl.myweb.controller.ex.FileSizeException;
import cn.jl.myweb.entity.User;
import cn.jl.myweb.service.IUserService;
import cn.jl.myweb.util.DateUtils;
import cn.jl.myweb.util.Log;
import cn.jl.myweb.util.ResponseResult;

/**
 * 处理用户数据的控制器层
 */
@RestController
@RequestMapping("user")
public class UserController extends BaseController{
	
	//确认上传文件的名称 ：UPLOAD_DIR
	private static final String UPLOAD_DIR = "upload";
	//确认上传的最大大小
	private static final long UPLOAD_MAX_SIZE = 1*1024*1024;
	//确认允许上传的类型的列表
	private static final List<String> UPLOAD_CONTENT_TYPES = new ArrayList<>();
	
	static {
		UPLOAD_CONTENT_TYPES.add("image/bmp");
		UPLOAD_CONTENT_TYPES.add("image/gif");
		UPLOAD_CONTENT_TYPES.add("image/jpeg");
		UPLOAD_CONTENT_TYPES.add("image/png");
	}

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
		return new ResponseResult<User>(SUCCESS,user);

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
	
	@RequestMapping("change_avatar")
	public ResponseResult<String> changeAvatar(
	    HttpServletRequest request, 
	    @RequestParam("file") MultipartFile file) {
	    // 检查文件是否为空
		if(file.isEmpty()) {
			// 为空：抛出异常：FileEmptyException
			throw new FileEmptyException("上传文件为空！");
		}
		// 检查文件大小
		if(file.getSize()>UPLOAD_MAX_SIZE) {
			// 超出范围(> UPLOAD_MAX_SIZE)：抛出异常：FileSizeException
			throw new FileSizeException("上传文件过大！");
		}
		// 检查文件类型
		if(!UPLOAD_CONTENT_TYPES.contains(file.getContentType())) {
			// 类型不符(contains()为false)：抛出异常：FileContentTypeException
			throw new FileContentTypeException("上传类型不匹配！");
		}
	    // 确定文件夹路径：request.getServletContext().getRealPath(UPLOAD_DIR);
		String parentPath = request.getServletContext().getRealPath(UPLOAD_DIR);
	    // 创建上传文件夹的File对象parent
		File parent = new File(parentPath);
	    // 检查文件夹是否存在，如果不存在，则创建
		if(!parent.exists()) {
			parent.mkdirs();
		}
	    // 获取原文件名：file.getOriginalFilename()
		String originalFilename = file.getOriginalFilename();
	    // 从原文件名中得到扩展名
		String suffix = "";
		int beginIndex = originalFilename.lastIndexOf(".");
		if(beginIndex>0) {
			suffix = originalFilename.substring(beginIndex);
		}
	    // 确定文件名：uuid/nanoTime/...
		String filename = System.nanoTime()+suffix;
	    // 创建dest对象：new File(parent, filename);
		File dest = new File(parent,filename);
		try {
			// 执行上传：file.transferTo(dest);
			file.transferTo(dest);
		} catch (IllegalStateException e) {
			// catch:IllegalStateException：抛出FileIllegalStateException
			throw new FileIllegalStateException();
		} catch (IOException e) {
			// catch:IOException：抛出FileIOException
			throw new FileIOException();
		}
		// 获取uid：getUidFromSession(request.getSession());
		Integer uid = getUidFromSession(request.getSession());
	    // 生成avatar：/UPLOAD_DIR/文件名.扩展名
		String avatar = "/"+UPLOAD_DIR+"/"+filename;
	    // 执行更新：userService.changeAvatar(uid, avatar);
		System.out.println(avatar);
		service.changeAvatar(uid, avatar);
	    // 返回成功
		return new ResponseResult<String>(SUCCESS,"",avatar);
	}
	
	@RequestMapping("download")
    public ResponseResult<Void> downLoadPayerCreditInfoExcel(HttpServletRequest request,HttpServletResponse response){
		try {
			response.reset();
	        String dateStr = DateUtils.convterDataStyle(DateUtils.SIMPLE_DATE_STYLE, new Date());
	        String fileName = URLEncoder.encode("用户数据表", "UTF-8")+dateStr;
	        // 指定下载的文件名
	        response.setHeader("Content-Disposition", "attachment;filename=" +fileName+".xlsx");
	        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
	        response.setHeader("Pragma", "no-cache");
	        response.setHeader("Cache-Control", "no-cache");
	        response.setDateHeader("Expires", 0);
	        XSSFWorkbook workbook=null;
			//导出Excel对象
			workbook = service.exportUsersInfoExcel();
			OutputStream output;
			output = response.getOutputStream();
            BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);
            bufferedOutPut.flush();
            workbook.write(bufferedOutPut);
            bufferedOutPut.close();
            return null;
		} catch (Exception e) {
			Log.error("UserController downLoadUsers is error");
		}
		return new ResponseResult<>(SUCCESS);
	}
    @RequestMapping("upload")  
    public ResponseResult<Void> uploadPayerCreditInfoExcel(@RequestParam("file") MultipartFile file) throws Exception {  
        if(file.isEmpty()){  
            throw new Exception("文件不存在！");  
        }  
        InputStream in = file.getInputStream();
        service.uploadUsersInfoExcel(in, file);
        in.close();
        return new ResponseResult<>(SUCCESS);
	}



}
