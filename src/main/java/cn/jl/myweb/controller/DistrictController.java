package cn.jl.myweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.jl.myweb.entity.District;
import cn.jl.myweb.service.IDistrictService;
import cn.jl.myweb.util.ResponseResult;

/**
 * 省/市/区数据的控制器层
 */
@RestController
@RequestMapping("district")
public class DistrictController extends BaseController {
	
	@Autowired
	private IDistrictService service;
	
	@RequestMapping("/")
	public ResponseResult<List<District>> findByParent(String parent){
		return new ResponseResult<List<District>>(SUCCESS,service.getByParent(parent));
	}
	
	

}
