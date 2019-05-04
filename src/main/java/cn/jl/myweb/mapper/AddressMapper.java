package cn.jl.myweb.mapper;

import java.util.List;

import cn.jl.myweb.entity.Address;

/**
 *用户信息的持久层接口
 */
public interface AddressMapper {
	
	/**
	 * 增加用户的收货地址
	 * @param address 要增加的收货地址
	 * @return 受影响的行数
	 */
	Integer insertAddress(Address address);
	
	/**
	 * 根据id删除收货地址数据
	 * @param aid 用户收货地址的aid
	 * @return 受影响的行数
	 */
	Integer deleteByAid(Integer aid);

	/**
	 * 取某用户最后修改的收货地址数据
	 * @param uid 用户的id
	 * @return 匹配的用户收货地址，如果没有匹配的数据，则返回null
	 */
	Address findLastModified(Integer uid);

	/**
	 * 统计指定用户的收货地址数据的数量
	 * @param uid 要查询用户的uid
	 * @return 用户收货地址的数量
	 */
	Integer countByUid(Integer uid);
	
	/**
	 * 根据用户的uid查找用户收货地址
	 * @param uid 用户的uid
	 * @return 匹配的用户收货地址，如果没有匹配的数据，则返回null
	 */
	List<Address> findAddress(Integer uid);
	
	/**
	 * 根据收货地址id查询匹配的数据
	 * @param aid 用户的aid
	 * @return 匹配的用户数据，如果没有匹配的数据，则返回null
	 */
	Address findByAid(Integer aid);
	
	/**
	 * 将指定用户的所有收货地址设置为非默认
	 * @param uid 用户的uid
	 * @return 受影响的行数
	 */
	Integer updateNonDefault(Integer uid);
	
	/**
	 * 将指定的收货地址设置为默认
	 * @param aid 用户的aid
	 * @return 受影响的行数
	 */
	Integer updateDefault(Integer aid);
	
	/**
	 * 修改用户收货地址
	 * @param address 要修改收获地址
	 * @return 受影响的行数
	 */
	Integer updateAddress(Address address);
}
