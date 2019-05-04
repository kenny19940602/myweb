package cn.jl.myweb.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.jl.myweb.entity.Address;
import cn.jl.myweb.entity.District;
import cn.jl.myweb.mapper.AddressMapper;
import cn.jl.myweb.service.IAddressService;
import cn.jl.myweb.service.IDistrictService;
import cn.jl.myweb.service.ex.AccessDeniedException;
import cn.jl.myweb.service.ex.AddressNotFoundException;
import cn.jl.myweb.service.ex.DeleteException;
import cn.jl.myweb.service.ex.InsertException;
import cn.jl.myweb.service.ex.UpdateException;

/**
 * 用户收货地址的业务层实现类
 */
@Service
public class AddressServiceImpl implements IAddressService {

	@Autowired
	private AddressMapper mapper;
	@Autowired
	private IDistrictService service;
	
	@Override
	public void addAddress(Address address,String username) throws InsertException {
		// 查询用户的收货地址的数量：countByUid(Integer uid)，参数值来自address.getUid();
		Integer num = countByUid(address.getUid());
	    // 判断数量是否为0
		// 否：当前增加的不是第1条收货地址，则：address.setIsDefault(0)
		// 是：当前将增加第1条收货地址，则：address.setIsDefault(1)
		address.setIsDefault(num==0?1:0);
		// 处理district
		String district = getDistrict(address.getProvince(), address.getCity(), address.getArea());
		address.setDistrict(district);
		// 4项日志：时间是直接创建对象得到，用户名使用参数username
		address.setCreatedUser(username);
		address.setCreatedTime(new Date());
		address.setModifiedUser(username);
		address.setModifiedTime(new Date());
	    // 执行增加：insert(Address address);
		insertAddress(address);
	}
	
	@Override
	@Transactional
	public void delete(Integer uid, Integer aid)
			throws AddressNotFoundException, AccessDeniedException, DeleteException, UpdateException {
		// 根据aid查询即将删除的数据
		Address result = findByAid(aid);
	    // 判断查询结果是否为null
		if(result==null) {
			// 是：AddressNotFoundException
			throw new AddressNotFoundException("设置默认收货地址失败！尝试访问的数据不存在！");
		}
		// 检查数据归属是否不正确
		if(!uid.equals(result.getUid())) {
			// 是：AccessDeniedException
			throw new AccessDeniedException("设置默认收货地址失败！数据归属错误！");
		}
		// 执行删除
		deleteByAid(aid);
		// 检查此前的查询结果中的isDefault是否为0
		if(result.getIsDefault().equals(0)) {
			// return;
			return;
		}
		Integer num = countByUid(uid);
		// 获取当前用户的收货地址数据的数量
		// 判断数量是否为0
		if(num.equals(0)) {
			// return;
			return;
		}
		// 获取当前用户最后修改的收货地址数据
		Address last = findLastModified(uid);
		// 将全部数据设置为非默认
		updateNonDefault(uid);
	    // 将最后修改的数据设置为默认。
		updateDefault(last.getAid());
		
	}


	
	@Override
	public List<Address> getByUid(Integer uid) {
		return findAddress(uid);
	}
	
	@Override
	@Transactional
	public void setDefaultAddress(Integer uid,Integer aid) throws AddressNotFoundException, AccessDeniedException, UpdateException {
		// 根据aid查询数据
		Address result = findByAid(aid);
	    // 判断数据是否为null
		if(result==null) {
			// 是：AddressNotFoundException
			throw new AddressNotFoundException("设置默认收货地址失败！尝试访问的数据不存在！");
		}
		// 判断参数uid与查询结果中的uid是否不一致
		if(!uid.equals(result.getUid())) {
			// 是：AccessDeniedException
			throw new AccessDeniedException("设置默认收货地址失败！数据归属错误！");
		}
		// 全部设置为非默认
		updateNonDefault(uid);
		// 把指定的设置为默认
		updateDefault(aid);
	}
	
	@Override
	public void setAddress(Address address, String username, Integer uid)
			throws AddressNotFoundException, AddressNotFoundException, UpdateException {
		// 根据aid查询数据
		Address result = findByAid(address.getAid());
		// 判断数据是否为null
		if(result==null) {
			// 是：AddressNotFoundException
			throw new AddressNotFoundException("设置收货地址失败！尝试访问的数据不存在！");
		}
		// 判断参数uid与查询结果中的uid是否不一致
		if(!uid.equals(result.getUid())) {
			// 是：AccessDeniedException
			throw new AccessDeniedException("设置默收货地址失败！数据归属错误！");
		}
		address.setModifiedUser(username);
		address.setModifiedTime(new Date());
		updateAddress(address);
	}
	
	/**
	 * 增加用户的收货地址
	 * @param address 要增加的收货地址
	 */
	private void insertAddress(Address address) {
		Integer rows = mapper.insertAddress(address);
		if(rows!=1) {
			throw new InsertException("");
		}
		
	}
	
	/**
	 * 根据id删除收货地址数据
	 * @param aid 用户收货地址的aid
	 */
	private void deleteByAid(Integer aid) {
		Integer rows = mapper.deleteByAid(aid);
		if(rows!=1) {
			throw new DeleteException("删除收货地址时出现未知错误！");
		}
	}

	/**
	 * 取某用户最后修改的收货地址数据
	 * @param uid 用户的id
	 * @return 匹配的用户收货地址，如果没有匹配的数据，则返回null
	 */
	private Address findLastModified(Integer uid) {
		return mapper.findLastModified(uid);
	}

	/**
	 * 统计指定用户的收货地址数据的数量
	 * @param uid 要查询用户的uid
	 * @return 用户收货地址的数量
	 */
	private Integer countByUid(Integer uid) {
		return mapper.countByUid(uid);
	}
	
	/**
	 * 根据省、市、区的代号，获取名称
	 * @param province 省的代号
	 * @param city 市的代号
	 * @param area 区的代号
	 * @return 省、市、区的代号对应的名称
	 */
	private String getDistrict(
			String province, String city, String area) {
		District p = service.getByCode(province);
		District c = service.getByCode(city);
		District a = service.getByCode(area);
		String provinceName = p == null? "NULL":p.getName();
		String cityName = c == null? "NULL":c.getName();
		String areaName = a == null? "NULL":a.getName();
		StringBuffer result = new StringBuffer();
		result.append(provinceName);
		result.append(",");
		result.append(cityName);
		result.append(",");
		result.append(areaName);
		return result.toString();
	}
	
	/**
	 * 根据用户的uid查找用户收货地址
	 * @param uid 用户的uid
	 * @return 匹配的用户收货地址，如果没有匹配的数据，则返回null
	 */
	private List<Address> findAddress(Integer uid){
		return mapper.findAddress(uid);
	}

	/**
	 * 根据收货地址id查询匹配的数据
	 * @param aid 用户的aid
	 * @return 匹配的用户数据，如果没有匹配的数据，则返回null
	 */
	private Address findByAid(Integer aid) {
		return mapper.findByAid(aid);
	}
	
	/**
	 * 将指定用户的所有收货地址设置为非默认
	 * @param uid 用户的uid
	 */
	private void updateNonDefault(Integer uid) {
		mapper.updateNonDefault(uid);
	}
	
	/**
	 * 将指定的收货地址设置为默认
	 * @param aid 用户的aid
	 */
	private void updateDefault(Integer aid) {
		mapper.updateDefault(aid);
	}

	/**
	 * 修改用户收货地址
	 * @param address 要修改收获地址
	 */
	private void updateAddress(Address address) {
		Integer rows = mapper.updateAddress(address);
		if(rows!=1) {
			throw new UpdateException("修改收货地址时出现未知错误！");
		}
	}
	

	

}
