package cn.jl.myweb.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.jl.myweb.entity.District;

/**
 * 省/市/区数据持久层测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DistrictMapperTestCase {
	
	@Autowired
    public DistrictMapper mapper;

    @Test
    public void findByParent() {
        String parent = "130600";
        List<District> list = mapper.findByParent(parent);
        System.err.println("BEGIN:");
        for (District data : list) {
            System.err.println(data);
        }
        System.err.println("END.");
    }
    
    @Test
    public void findByCode() {
        String code = "320102";
        District data = mapper.findByCode(code);
        System.err.println(data);
    }

}
