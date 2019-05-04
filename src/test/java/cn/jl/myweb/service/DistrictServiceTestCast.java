package cn.jl.myweb.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.jl.myweb.entity.District;

/**
 * 省/市/区数据业务层测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DistrictServiceTestCast {
	
	   @Autowired
	    public IDistrictService service;

	    @Test
	    public void getByParent() {
	        String parent = "86";
	        List<District> list = service.getByParent(parent);
	        System.err.println("BEGIN:");
	        for (District data : list) {
	            System.err.println(data);
	        }
	        System.err.println("END.");
	    }

}
