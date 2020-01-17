package com.ego.order.service;

import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.TbItemChild;
import com.ego.order.pojo.MyOrderParam;

import java.util.List;

public interface TbOrderService {
    /**
     * 查询购物车商品
     * @param ids 购物车商品id集合
     * @param token cookie登陆
     * @return
     */
    List<TbItemChild> selByCart(List<Long> ids, String token);

    /**
     * 提交订单
     * @param param 订单数据
     * @param token cookie登陆
     * @return
     */
    EgoResult orderIns(MyOrderParam param, String token);
}
