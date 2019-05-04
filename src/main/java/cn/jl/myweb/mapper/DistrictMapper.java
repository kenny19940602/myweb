package cn.jl.myweb.mapper;

import java.util.List;

import cn.jl.myweb.entity.District;

/**
 * 省/市/区数据的持久层接口
 */
public interface DistrictMapper {

	/**
	 * 根据省查找 省/市/区数据
	 * @param parent 省
	 * @return 市/区数据 
	 */
	List<District> findByParent(String parent);
	
	/**
	 * 根据代号获取省/市/区的信息
	 * @param code 代号
	 * @return 省/市/区的信息
	 */
	District findByCode(String code);
}
