package com.ego.dubbo.service.impl;

import com.ego.dubbo.service.TbOrderDubboService;
import com.ego.mapper.TbOrderItemMapper;
import com.ego.mapper.TbOrderMapper;
import com.ego.mapper.TbOrderShippingMapper;
import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;

import javax.annotation.Resource;
import java.util.List;

public class TbOrderDubboServiceImpl implements TbOrderDubboService {
    @Resource
    private TbOrderMapper tbOrderMapper;
    @Resource
    private TbOrderItemMapper tbOrderItemMapper;
    @Resource
    private TbOrderShippingMapper tbOrderShippingMapper;

    /**
     * 事务回滚+订单操作
     * @param tbOrder 订单信息
     * @param list 商品信息
     * @param tbOrderShipping 购买者信息
     * @return
     */
    @Override
    public int insOrder(TbOrder tbOrder, List<TbOrderItem> list, TbOrderShipping tbOrderShipping) throws Exception {
        int insert = tbOrderMapper.insert(tbOrder);
        insert+= tbOrderShippingMapper.insert(tbOrderShipping);
        for (TbOrderItem orderItem : list){
            insert+= tbOrderItemMapper.insert(orderItem);
        }
        if (insert==(list.size()+2)){
            return 1;
        }else {
            throw new Exception("订单提交失败");
        }
    }
}
