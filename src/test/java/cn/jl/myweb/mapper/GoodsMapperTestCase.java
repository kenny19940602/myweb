package cn.jl.myweb.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.jl.myweb.entity.Goods;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsMapperTestCase {

    @Autowired
    public GoodsMapper mapper;

    @Test
    public void findHotGoods() {
        List<Goods> list = mapper.findHotGoods();
        System.err.println("BEGIN:");
        for (Goods data : list) {
            System.err.println(data);
        }
        System.err.println("END.");
    }

    @Test
    public void findById() {
        Long id = 10000017L;
        Goods data = mapper.findById(id);
        System.err.println(data);
    }
}
