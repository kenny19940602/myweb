package cn.jl.myweb.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.jl.myweb.entity.Address;
import cn.jl.myweb.service.IAddressService;
import cn.jl.myweb.util.ResponseResult;

/**
 * 处理用户地址的控制器层
 */
@RestController
@RequestMapping("address")
public class AddressController extends BaseController {
	
	@Autowired
	private IAddressService service;
	
	@RequestMapping("insert")
	public ResponseResult<Void> addAddress(Address address,HttpSession session) {
		Integer uid = getUidFromSession(session);
		address.setUid(uid);
		String username = session.getAttribute("username").toString();
		service.addAddress(address, username);
		return new ResponseResult<>(SUCCESS);
	}
	
	@RequestMapping("/")
	public ResponseResult<List<Address>> findAddressByUid(HttpSession session){
		Integer uid = getUidFromSession(session);
		return new ResponseResult<List<Address>>(SUCCESS, service.getByUid(uid));
	}
	
	@RequestMapping("{aid}/set_default")
	public ResponseResult<Void> setDefault(@PathVariable("aid") Integer aid,HttpSession session){
		service.setDefaultAddress(getUidFromSession(session), aid);
		return new ResponseResult<Void>(SUCCESS);
	}
	
	@RequestMapping("{aid}/delete")
	public ResponseResult<Void> delete(@PathVariable("aid") Integer aid,HttpSession session){
		service.delete(getUidFromSession(session), aid);
		return new ResponseResult<Void>(SUCCESS);
	}
	
	@RequestMapping("update")
	public ResponseResult<Void> update(Address address,HttpSession session){
		System.out.println(address);
		Integer uid = getUidFromSession(session);
		String username = session.getAttribute("username").toString();
		service.setAddress(address, username, uid);
		return new ResponseResult<Void>(SUCCESS);
	}

}
