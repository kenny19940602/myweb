package cn.jl.myweb.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.jl.myweb.entity.Goods;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsServiceTestCase {

    @Autowired
    public IGoodsService service;

    @Test
    public void getHotGoods() {
        List<Goods> list = service.getHotGoods();
        System.err.println("BEGIN:");
        for (Goods data : list) {
            System.err.println(data);
        }
        System.err.println("END.");
    }

    @Test
    public void getById() {
        Long id = 10000017L;
        Goods data = service.getById(id);
        System.err.println(data);
    }
}
