package cn.jl.myweb.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.jl.myweb.entity.Address;
import cn.jl.myweb.service.ex.ServiceException;

/**
 * 用户收货地址业务层测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressServiceTestCase {
	
	@Autowired
	private IAddressService service;
	
	
	 @Test
	    public void addnew() {
	        try {
	            Address address = new Address();
	            address.setUid(10);
	            address.setName("小刘同学");
	            String username = "小森同学";
	            service.addAddress(address, username);
	            System.err.println("OK.");
	        } catch (ServiceException e) {
	            System.err.println(e.getClass().getName());
	            System.err.println(e.getMessage());
	        }
	    }

	 @Test
	 public void delete() {
	     try {
	         Integer uid = 4;
	         Integer aid = 5; 
	         service.delete(uid, aid);
	         System.err.println("OK.");
	     } catch (ServiceException e) {
	         System.err.println(e.getClass().getName());
	         System.err.println(e.getMessage());
	     }
	 }
}
