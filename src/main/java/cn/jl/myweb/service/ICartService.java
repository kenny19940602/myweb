package cn.jl.myweb.service;

import java.util.List;

import cn.jl.myweb.entity.Cart;
import cn.jl.myweb.service.ex.AccessDeniedException;
import cn.jl.myweb.service.ex.CartNotFoundException;
import cn.jl.myweb.service.ex.DeleteException;
import cn.jl.myweb.service.ex.InsertException;
import cn.jl.myweb.service.ex.UpdateException;
import cn.jl.myweb.vo.CartVO;

/**
 * 处理购物车数据的业务层接口
 */
public interface ICartService {

	 /**
     * 将用户选中的商品添加到购物车
     * @param username 当前登录的用户的用户名
     * @param cart 购物车数据
     * @throws InsertException 插入数据异常
     * @throws UpdateException 更新数据异常
     */
    void addToCart(String username, Cart cart) throws InsertException, UpdateException;
    
    /**
     * 查询用户购物车信息
     * @param uid 要查询用户的uid
     */
    /**
     * 查询用户购物车信息
     * @param uid uid 要查询用户的uid
     * @return 匹配的购物车的信息，如果没有匹配的信息，则返回null
     */
    List<CartVO> getCartList(Integer uid);
    
    /**
     * 增加购物车商品数量
     * @param uid 用户uid 
     * @param username 用户名
     * @param cid 购物车cid
     */
    void addNum(Integer uid, String username, Integer cid) throws CartNotFoundException,AccessDeniedException,UpdateException;
    
    /**
     * 增加购物车商品数量
     * @param uid 用户uid 
     * @param username 用户名
     * @param cid 购物车cid
     */
    void redNum(Integer uid, String username, Integer cid) throws CartNotFoundException,AccessDeniedException,UpdateException;
    
    /**
     * 查询用户订单数据
     * @param cids 要查寻的用户订单数据
     * @return 匹配的订单数据数据，如果没有匹配的数据，则返回null
     */
    List<CartVO> getByCids(Integer[] cids);
    
    /**
     * 删除购物车数据
     * @param cid 要删除的购物车cid
     */
    void deleteByCid(Integer cid) throws DeleteException;
}
