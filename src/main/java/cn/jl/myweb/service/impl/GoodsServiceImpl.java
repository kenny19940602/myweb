package cn.jl.myweb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.jl.myweb.entity.Goods;
import cn.jl.myweb.mapper.GoodsMapper;
import cn.jl.myweb.service.IGoodsService;

/**
 * 商品的业务层实现类
 */
@Service
public class GoodsServiceImpl implements IGoodsService {

	@Autowired
	private GoodsMapper mapper;
	
	@Override
	public List<Goods> getHotGoods() {
		return findHotGoods();
	}

	@Override
	public Goods getById(Long id) {
		return findById(id);
	}
	
	/**
	 * 查找热销商品
	 * @return 热销商品
	 */
	private List<Goods> findHotGoods(){
		return mapper.findHotGoods();
	}

	/**
	 * 根据id查询商品详情
	 * @param id 要查询商品id
	 * @return 查找到匹配的商品详情则返回信息，如果没有查询到用户信息则返回null
	 */
	private Goods findById(Long id) {
		return mapper.findById(id);
	}
	
}
