package cn.jl.myweb.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.jl.myweb.entity.Cart;
import cn.jl.myweb.mapper.CartMapper;
import cn.jl.myweb.service.ICartService;
import cn.jl.myweb.service.ex.AccessDeniedException;
import cn.jl.myweb.service.ex.CartNotFoundException;
import cn.jl.myweb.service.ex.DeleteException;
import cn.jl.myweb.service.ex.InsertException;
import cn.jl.myweb.service.ex.UpdateException;
import cn.jl.myweb.vo.CartVO;

/**
 * 添加购物车的业务层实现类
 */
@Service
public class CartServiceImpl implements ICartService {

	@Autowired
	private CartMapper mapper;

	@Override
	public void addToCart(String username, Cart cart) throws InsertException, UpdateException {
		// 根据uid和gid去查询数据：findByUidAndGid(cart.getUid(), cart.getGid())
		Cart result = findByUidAndGid(cart.getUid(), cart.getGid());
	    // 判断查询结果是否为null
		if(result==null) {
			// 是：向参数cart中封装日志数据
			cart.setModifiedUser(username);
			cart.setModifiedTime(new Date());
			cart.setCreatedUser(username);
			cart.setCreatedTime(new Date());
			// -- 插入数据：insert(cart)
			insert(cart);
		}else {
			// 否：计算新的num值：num = cart.getNum() + result.getNum();
			Integer num = cart.getNum()+result.getNum();
			// -- updateNum(result.getCid(), num)
			updateNum(result.getCid(), num, username, new Date());
		}
	}
	
	@Override
	public List<CartVO> getCartList(Integer uid) {
		return findByUid(uid);
	}
	
	@Override
	public void addNum(Integer uid, String username, Integer cid)
			throws CartNotFoundException, AccessDeniedException, UpdateException {
		// 根据参数cid查询数据：findByCid(cid)
		Cart result = findByCid(cid);
	    // 判断查询结果是否为null
		if(result==null) {
			// 是：CartNotFoundException
			throw new CartNotFoundException("增加商品数量错误！尝试访问的数据不存在！");
		}
		// 判断查询结果中的uid与当前登录的用户id(参数uid)是否不一致
		if(!uid.equals(result.getUid())) {
			// 是：AccessDeniedException
			throw new AccessDeniedException("增加商品数量错误！数据归属错误！");
		}
		// 暂不实现：判断商品的状态、库存等，即某商品是否可以存在于购物车中
		// 将查询结果中的商品数量加1
		Integer num = result.getNum()+1;
		// 执行更新：updateNum(cid, num, modifiedUser, modifiedTime)
		updateNum(cid, num, username, new Date());
		
	}
	
	@Override
	public void redNum(Integer uid, String username, Integer cid)
			throws CartNotFoundException, AccessDeniedException, UpdateException {
		// 根据参数cid查询数据：findByCid(cid)
		Cart result = findByCid(cid);
		// 判断查询结果是否为null
		if(result==null) {
			// 是：CartNotFoundException
			throw new CartNotFoundException("增加商品数量错误！尝试访问的数据不存在！");
		}
		// 判断查询结果中的uid与当前登录的用户id(参数uid)是否不一致
		if(!uid.equals(result.getUid())) {
			// 是：AccessDeniedException
			throw new AccessDeniedException("增加商品数量错误！数据归属错误！");
		}
		// 暂不实现：判断商品的状态、库存等，即某商品是否可以存在于购物车中
		// 将查询结果中的商品数量加1
		Integer num = result.getNum()-1;
		// 执行更新：updateNum(cid, num, modifiedUser, modifiedTime)
		updateNum(cid, num, username, new Date());
		
	}

	@Override
	public List<CartVO> getByCids(Integer[] cids) {
		return findByCids(cids);
	}

	@Override
	public void deleteByCid(Integer cid) throws DeleteException {
		delByCid(cid);
	}
	 /**
     * 插入购物车数据
     * @param cart 购物车数据
     */
    private void insert(Cart cart) {
    	Integer rows = mapper.insert(cart);
    	if(rows!=1) {
    		throw new InsertException("添加购物车数据出现未知错误！");
    	}
    }

    /**
     * 修改购物车数据中商品的数量 
     * @param cid 购物车数据的id
     * @param num 新的数量
     * @param modifiedUser 修改执行人
     * @param modifiedTime 修改时间
     */
    private void updateNum(
        @Param("cid") Integer cid, 
        @Param("num") Integer num,
        @Param("modifiedUser") String modifiedUser, 
        @Param("modifiedTime") Date modifiedTime) {
    	Integer rows = mapper.updateNum(cid, num, modifiedUser, modifiedTime);
    	if(rows!=1) {
    		throw new UpdateException("修改购物车中商品数量出现未知错误！");
    	}
    }

    /**
     * 获取某用户在购物车中添加的指定商品的数据
     * @param uid 用户的id
     * @param gid 商品的id
     * @return 匹配的购物车数据，如果没有匹配的数据，则返回null
     */
    private Cart findByUidAndGid(
        @Param("uid") Integer uid, 
        @Param("gid") Long gid) {
    	return mapper.findByUidAndGid(uid, gid);
    }

    /**
     * 获取某用户的购物车数据列表
     * @param uid 用户uid
     * @return 匹配的购物车数据，如果没有匹配的数据，则返回null
     */
    private List<CartVO> findByUid(Integer uid){
    	return mapper.findByUid(uid);
    }
    
    /**
     * 根据购物车数据id获取购物车数据
     * @param cid 购物车id
     * @return 匹配的购物车数据，如果没有匹配的数据，则返回null
     */
    private Cart findByCid(Integer cid) {
    	return mapper.findByCid(cid);
    }
    
    /**
     * 查询用户订单数据
     * @param cids 要查寻的用户订单数据
     * @return 匹配的订单数据数据，如果没有匹配的数据，则返回null
     */
    private List<CartVO> findByCids(Integer[] cids){
    	return mapper.findByCids(cids);
    }

    /**
     * 根据cid删除购物车数据
     * @param cid 购物车cid
     * @return 受影响的行数
     */
    private void delByCid(Integer cid) {
    	Integer rows = mapper.delByCid(cid);
    	if(rows!=1) {
    		throw new DeleteException("删除购物车数据，发生未知错误!");
    	}
    }
	
}
