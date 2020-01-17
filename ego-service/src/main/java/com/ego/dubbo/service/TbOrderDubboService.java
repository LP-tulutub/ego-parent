package com.ego.dubbo.service;

import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;

import java.util.List;

public interface TbOrderDubboService {
    /**
     * 事务回滚+订单操作
     * @param tbOrder 订单信息
     * @param list 商品信息
     * @param tbOrderShipping 购买者信息
     * @return
     */
    int insOrder(TbOrder tbOrder, List<TbOrderItem> list, TbOrderShipping tbOrderShipping) throws Exception;
}
