package cn.jl.myweb.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.jl.myweb.entity.Cart;
import cn.jl.myweb.vo.CartVO;

/**
 * 处理购物车数据的持久层接口
 */
public interface CartMapper {

    /**
     * 插入购物车数据
     * @param cart 购物车数据
     * @return 受影响的行数
     */
    Integer insert(Cart cart);

    /**
     * 修改购物车数据中商品的数量 
     * @param cid 购物车数据的id
     * @param num 新的数量
     * @param modifiedUser 修改执行人
     * @param modifiedTime 修改时间
     * @return 受影响的行数
     */
    Integer updateNum(
        @Param("cid") Integer cid, 
        @Param("num") Integer num,
        @Param("modifiedUser") String modifiedUser, 
        @Param("modifiedTime") Date modifiedTime);

    /**
     * 获取某用户在购物车中添加的指定商品的数据
     * @param uid 用户的id
     * @param gid 商品的id
     * @return 匹配的购物车数据，如果没有匹配的数据，则返回null
     */
    Cart findByUidAndGid(
        @Param("uid") Integer uid, 
        @Param("gid") Long gid);
    
    /**
     * 获取某用户的购物车数据列表
     * @param uid 用户uid
     * @return 匹配的购物车数据，如果没有匹配的数据，则返回null
     */
    List<CartVO> findByUid(Integer uid);
    
    /**
     * 根据购物车数据id获取购物车数据
     * @param cid 购物车id
     * @return 匹配的购物车数据，如果没有匹配的数据，则返回null
     */
    Cart findByCid(Integer cid);
    
    /**
     * 查询用户订单数据
     * @param cids 要查寻的用户订单数据
     * @return 匹配的订单数据数据，如果没有匹配的数据，则返回null
     */
    List<CartVO> findByCids(Integer[] cids);
    
    /**
     * 根据cid删除购物车数据
     * @param cid 购物车cid
     * @return 受影响的行数
     */
    Integer delByCid(Integer cid);

}
