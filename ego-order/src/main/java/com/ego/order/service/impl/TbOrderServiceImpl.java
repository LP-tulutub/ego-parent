package com.ego.order.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.TbItemChild;
import com.ego.commons.utils.IDUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.dubbo.service.TbOrderDubboService;
import com.ego.order.pojo.MyOrderParam;
import com.ego.order.service.TbOrderService;
import com.ego.pojo.*;
import com.ego.redis.dao.JedisDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TbOrderServiceImpl implements TbOrderService {
    @Reference
    private TbItemDubboService tbItemDubboServiceImpl;
    @Reference
    private TbOrderDubboService tbOrderDubboServiceImpl;
    @Resource
    private JedisDao jedisDaoImpl;
    @Value("${redis.cart.key}")
    private String cartKey;

    /**
     * 查询购物车商品
     * @param ids 购物车商品id集合
     * @param token cookie登陆
     * @return
     */
    @Override
    public List<TbItemChild> selByCart(List<Long> ids, String token) {
        List<TbItemChild> list = new ArrayList<>();

        String userJson = jedisDaoImpl.get(token);
        TbUser tbUser = JsonUtils.jsonToPojo(userJson, TbUser.class);
        String key = cartKey + tbUser.getUsername();
        String cartJson = jedisDaoImpl.get(key);
        List<TbItemChild> childList = JsonUtils.jsonToList(cartJson, TbItemChild.class);

        for (long id : ids){
            for (TbItemChild child : childList){
                if (id==child.getId()){
                    //判断购买量是否大于等于库存
                    TbItem tbItem = tbItemDubboServiceImpl.selById(id);
                    if(tbItem.getNum()>=child.getNumCart()){
                        child.setEnough(true);
                    }else{
                        child.setEnough(false);
                    }
                    list.add(child);
                }
            }
        }

        return list;
    }

    /**
     * 提交订单
     * @param param 订单数据
     * @param token cookie登陆
     * @return
     */
    @Override
    public EgoResult orderIns(MyOrderParam param, String token) {
        String userJson = jedisDaoImpl.get(token);
        TbUser tbUser = JsonUtils.jsonToPojo(userJson, TbUser.class);
        Date date =new Date();
        long id = IDUtils.genItemId();
        //订单表数据
        TbOrder order = new TbOrder();
        order.setPayment(param.getPayment());
        order.setPaymentType(param.getPaymentType());
        order.setOrderId(id+"");
        order.setCreateTime(date);
        order.setUpdateTime(date);
        order.setUserId(tbUser.getId());
        order.setBuyerNick(tbUser.getUsername());
        order.setBuyerRate(0);
        //订单-商品表
        for (TbOrderItem item : param.getOrderItems()) {
            item.setId(IDUtils.genItemId()+"");
            item.setOrderId(id+"");
        }
        //收货人信息
        TbOrderShipping shipping = param.getOrderShipping();
        shipping.setOrderId(id+"");
        shipping.setCreated(date);
        shipping.setUpdated(date);

        EgoResult erResult = new EgoResult();
        try {
            int index = tbOrderDubboServiceImpl.insOrder(order, param.getOrderItems(), shipping);
            if(index==1){
                erResult.setStatus(200);
                //删除购买的商品
                String key = cartKey + tbUser.getUsername();
                String itemJson = jedisDaoImpl.get(key);
                List<TbItemChild> listCart = JsonUtils.jsonToList(itemJson, TbItemChild.class);
                List<TbItemChild> listNew = new ArrayList<>();
                for (TbItemChild child : listCart) {
                    for (TbOrderItem item : param.getOrderItems()) {
                        if(child.getId()==Long.parseLong(item.getItemId())){
                            listNew.add(child);
                        }
                    }
                }
                for (TbItemChild myNew : listNew) {
                    listCart.remove(myNew);
                }
                //删除
                jedisDaoImpl.set(key, JsonUtils.objectToJson(listCart));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return erResult;
    }
}
