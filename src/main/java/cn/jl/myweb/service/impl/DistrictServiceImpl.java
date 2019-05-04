package cn.jl.myweb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.jl.myweb.entity.District;
import cn.jl.myweb.mapper.DistrictMapper;
import cn.jl.myweb.service.IDistrictService;

/**
 * 省/市/区数据的业务层实现类
 */
@Service
public class DistrictServiceImpl implements IDistrictService{
	
	@Autowired
	private DistrictMapper mapper;

	@Override
	public List<District> getByParent(String parent) {
		return findByParent(parent);
	}
	
	@Override
	public District getByCode(String code) {
		return findByCode(code);
	}
	
	/**
	 * 获取所有省/某省所有市/某市所有区的列表
	 * @param parent 获取省列表时，使用86；获取市列表时，使用省的代号；获取区列表时，使用市的代号
	 * @return 所有省/某省所有市/某市所有区的列表
	 */
	private List<District> findByParent(String parent){
		return mapper.findByParent(parent);
	}

	/**
	 * 根据代号获取省/市/区的信息
	 * @param code 代号
	 * @return 省/市/区的信息
	 */
	private District findByCode(String code) {
		return mapper.findByCode(code);
	}
	

}
