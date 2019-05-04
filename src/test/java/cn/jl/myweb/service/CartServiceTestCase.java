package cn.jl.myweb.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.jl.myweb.entity.Cart;
import cn.jl.myweb.service.ex.ServiceException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartServiceTestCase {

    @Autowired
    public ICartService service;

    @Test
    public void addToCart() {
        try {
            String username = "ROOT";
            Cart cart = new Cart();
            cart.setUid(20);
            cart.setGid(21L);
            cart.setNum(3);
            cart.setCid(2);
            service.addToCart(username, cart);
            System.err.println("OK.");
        } catch (ServiceException e) {
            System.err.println(e.getClass().getName());
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void addNum() {
        try {
            Integer uid = 4;
            String username = "系统管理员";
            Integer cid = 3;
            service.addNum(uid, username, cid);
            System.err.println("OK.");
        } catch (ServiceException e) {
            System.err.println(e.getClass().getName());
            System.err.println(e.getMessage());
        }
    }
}
