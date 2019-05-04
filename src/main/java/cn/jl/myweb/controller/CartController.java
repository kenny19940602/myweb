package cn.jl.myweb.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.jl.myweb.entity.Cart;
import cn.jl.myweb.service.ICartService;
import cn.jl.myweb.util.ResponseResult;
import cn.jl.myweb.vo.CartVO;

/**
 * 处理用户地址的控制器层
 */
@RestController
@RequestMapping("cart")
public class CartController extends BaseController {
	
	@Autowired
	private ICartService service;

	@RequestMapping("add")
	public ResponseResult<Void> addCart(Cart cart,HttpSession session){
		String username = session.getAttribute("username").toString();
		cart.setUid(getUidFromSession(session));
		service.addToCart(username, cart);
		return new ResponseResult<Void>(SUCCESS);
	}
	
	@RequestMapping("/")
	public ResponseResult<List<CartVO>> getCart(HttpSession session){
		List<CartVO> data = service.getCartList(getUidFromSession(session));
		return new ResponseResult<List<CartVO>>(SUCCESS,data);
	}
	
	@RequestMapping("{cid}/add_num")
	public ResponseResult<Void> addNum(@PathVariable("cid") Integer cid , HttpSession session){
		Integer uid = getUidFromSession(session);
		String username = session.getAttribute("username").toString();
		service.addNum(uid, username, cid);
		return new ResponseResult<Void>(SUCCESS);
	}
	
	@RequestMapping("{cid}/red_num")
	public ResponseResult<Void> redNum(@PathVariable("cid") Integer cid , HttpSession session){
		Integer uid = getUidFromSession(session);
		String username = session.getAttribute("username").toString();
		service.redNum(uid, username, cid);
		return new ResponseResult<Void>(SUCCESS);
	}
	
	@RequestMapping("checked_list")
	public ResponseResult<List<CartVO>> getByCids (Integer[] cids){
		List<CartVO> data = service.getByCids(cids);
		return new ResponseResult<List<CartVO>>(SUCCESS,data);
	}
	
	@RequestMapping("{cid}/del")
	public ResponseResult<Void> deleteByCid(@PathVariable("cid")Integer cid){
		service.deleteByCid(cid);
		return new ResponseResult<Void>(SUCCESS);
	}
}

