package cn.jl.myweb.service;

import java.util.List;

import cn.jl.myweb.entity.Goods;

/**
 * 热销商品的业务层接口
 */
public interface IGoodsService {

	 /**
     * 获取热销商品列表
     * @return 热销商品列表
     */
	List<Goods> getHotGoods();
	
	/**
	 * 根据id查询商品详情
	 * @param id 要查询商品id
	 * @return 查找到匹配的商品详情则返回信息，如果没有查询到用户信息则返回null
	 */
	Goods getById(Long id);
}
