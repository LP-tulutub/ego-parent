package com.ego.cart.service;

import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.TbItemChild;

import java.util.List;

public interface CartService {
    /**
     * 购物车增加商品
     * @param id 商品id
     * @param num 加入数量
     * @param token cookie登陆key
     * @return
     */
    EgoResult addCart(long id, int num, String token);

    /**
     * 购物车页面
     * @param token cookie登陆key
     * @return
     */
    List<TbItemChild> selCart(String token);

    /**
     * 修改购物车商品数量
     * @param id 商品id
     * @param numCart 商品数量
     * @param token
     * @return
     */
    EgoResult updCart(long id,int numCart, String token);

    /**
     * 删除购物车商品
     * @param id 商品id
     * @param token
     * @return
     */
    EgoResult delCart(long id, String token);
}
