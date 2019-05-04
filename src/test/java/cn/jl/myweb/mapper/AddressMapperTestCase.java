package cn.jl.myweb.mapper;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.jl.myweb.entity.Address;

/**
 * 用户收货地址持久层测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressMapperTestCase {

	  @Autowired
	    public AddressMapper mapper;

	    @Test
	    public void insert() {
	        Address address = new Address();
	        address.setUid(8);
	        address.setName("小李同学");
	        Integer rows = mapper.insertAddress(address);
	        System.err.println("rows=" + rows);
	    }

	    @Test
	    public void countByUid() {
	        Integer uid = 8;
	        Integer count = mapper.countByUid(uid);
	        System.err.println("count=" + count);
	    }

	    @Test
	    public void updateNonDefault() {
	        Integer uid = 4;
	        Integer rows = mapper.updateNonDefault(uid);
	        System.err.println("rows=" + rows);
	    }

	    @Test
	    public void updateDefault() {
	        Integer aid = 4;
	        Integer rows = mapper.updateDefault(aid);
	        System.err.println("rows=" + rows);
	    }
	    @Test
	    public void updateAddress() {
	    	Address address =  new Address();
	    	address.setAid(10);
	    	address.setTag("家");
	    	address.setTel("13654408006");
	    	address.setName("金龙");
	    	address.setModifiedUser("金龙");
	    	address.setModifiedTime(new Date());
	    	address.setAddress("东城国际");
	    	address.setDistrict("吉林省，长春市，南关区");
	    	Integer rows = mapper.updateAddress(address);
	    	System.err.println("rows=" + rows);
	    }

	    @Test
	    public void findByAid() {
	        Integer aid = 4;
	        Address data = mapper.findByAid(aid);
	        System.err.println(data);
	    }
	    
	    @Test
	    public void deleteByAid() {
	        Integer aid = 3;
	        Integer rows = mapper.deleteByAid(aid);
	        System.err.println("rows=" + rows);
	    }

	    @Test
	    public void findLastModified() {
	        Integer uid = 4;
	        Address data = mapper.findLastModified(uid);
	        System.err.println(data);
	    }

}
