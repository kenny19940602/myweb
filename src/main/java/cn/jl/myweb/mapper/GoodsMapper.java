package cn.jl.myweb.mapper;

import java.util.List;

import cn.jl.myweb.entity.Goods;

/**
 * 商品信息的持久层接口
 */
public interface GoodsMapper {
	
	/**
	 * 查找热销商品
	 * @return 热销商品
	 */
	List<Goods> findHotGoods();
	
	/**
	 * 根据id查询商品详情
	 * @param id 要查询商品id
	 * @return 查找到匹配的商品详情则返回信息，如果没有查询到用户信息则返回null
	 */
	Goods findById(Long id);

}
