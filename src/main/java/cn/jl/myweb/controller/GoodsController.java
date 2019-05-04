package cn.jl.myweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.jl.myweb.entity.Goods;
import cn.jl.myweb.service.IGoodsService;
import cn.jl.myweb.util.ResponseResult;

@RestController
@RequestMapping("/goods")
public class GoodsController extends BaseController {

    @Autowired
    private IGoodsService goodsService;

    @GetMapping("/hot")
    public ResponseResult<List<Goods>> getHotGoods() {
        // 获取数据
        List<Goods> data = goodsService.getHotGoods();
        // 返回
        return new ResponseResult<>(SUCCESS, data);
    }

    @GetMapping("/{id}/details")
    public ResponseResult<Goods> getById(
        @PathVariable("id") Long id) {
        // 执行
        Goods data = goodsService.getById(id);
        // 返回
        return new ResponseResult<>(SUCCESS, data);
    }
}
