package cn.jl.myweb.service;

import java.util.List;

import cn.jl.myweb.entity.Address;
import cn.jl.myweb.service.ex.AccessDeniedException;
import cn.jl.myweb.service.ex.AddressNotFoundException;
import cn.jl.myweb.service.ex.DeleteException;
import cn.jl.myweb.service.ex.InsertException;
import cn.jl.myweb.service.ex.UpdateException;

/**
 * 处理用户数据的业务层接口
 */
public interface IAddressService {
	
	/**
	 * 增加用户的收货地址
	 * @param address 要增加的收货地址
	 */
	void addAddress(Address address,String username) throws InsertException;
	
	/**
	 * 删除用户收货地址
	 * @param uid 要删除收货地址的用户id
	 * @param aid 要删除收货地址的id
	 * @throws AddressNotFoundException 收货地址不存在的异常
	 * @throws AccessDeniedException 收货地址归属异常
	 * @throws DeleteException
	 * @throws UpdateException 收货地址归属异常
	 */
	void delete(Integer uid, Integer aid) throws AddressNotFoundException, AccessDeniedException, DeleteException, UpdateException;
	
	/**
	 *根据用户的uid查找用户收货地址
	 * @param uid 用户的uid
	 * @return 匹配的用户收货地址，如果没有匹配的数据，则返回null
	 */
	List<Address> getByUid(Integer uid);
	
	/**
	 * 将给定的收货地址aid 设置默认收货地址
	 * @param aid 要设置为默认收货地址的aid
	 * @throws AddressNotFoundException 收货地址不存在的异常
	 * @throws AccessDeniedException 收货地址不存在的异常
	 * @throws UpdateException 修改失败的异常类
	 */
	void setDefaultAddress(Integer uid,Integer aid) throws AddressNotFoundException,AccessDeniedException,UpdateException;
	
	/**
	 * 修改收货地址
	 * @param address 要修改的收货地址
	 * @throws AddressNotFoundException 收货地址不存在的异常
	 * @throws AddressNotFoundException 收货地址不存在的异常
	 * @throws UpdateException 修改失败的异常类
	 */
	void setAddress(Address address,String username,Integer uid) throws AddressNotFoundException,AddressNotFoundException,UpdateException;


}
