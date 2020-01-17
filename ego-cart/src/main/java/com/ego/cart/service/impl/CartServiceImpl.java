package com.ego.cart.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.cart.service.CartService;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.TbItemChild;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbUser;
import com.ego.redis.dao.JedisDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Reference
    private TbItemDubboService tbItemDubboServiceImpl;
    @Resource
    private JedisDao jedisDaoImpl;
    @Value("${redis.cart.key}")
    private String cartKey;

    /**
     * 购物车增加商品
     * @param id 商品id
     * @param num 加入数量
     * @param token cookie登陆key
     * @return
     */
    @Override
    public EgoResult addCart(long id, int num, String token) {
        TbItem tbItem = tbItemDubboServiceImpl.selById(id);

        List<TbItemChild> list = new ArrayList<>();
        String userJson = jedisDaoImpl.get(token);
        TbUser tbUser = JsonUtils.jsonToPojo(userJson, TbUser.class);
        String key = cartKey + tbUser.getUsername();

        if (jedisDaoImpl.exists(key)){
            String listItemJson = jedisDaoImpl.get(key);
            if (listItemJson!=null&&!listItemJson.equals("")){
                list = JsonUtils.jsonToList(listItemJson, TbItemChild.class);
                for (TbItemChild child : list){
                    if (child.getId()==id){
                        child.setNumCart(child.getNumCart()+num);
                        jedisDaoImpl.set(key, JsonUtils.objectToJson(list));
                        return null;
                    }
                }
            }
        }

        TbItemChild tbItemChild = new TbItemChild();
        tbItemChild.setId(id);
        tbItemChild.setNumCart(num);
        tbItemChild.setImages(tbItem.getImage()==null||tbItem.getImage().equals("")?new String[1]:tbItem.getImage().split(","));
        tbItemChild.setPrice(tbItem.getPrice());

        list.add(tbItemChild);
        jedisDaoImpl.set(key, JsonUtils.objectToJson(list));

        return null;
    }

    /**
     * 购物车页面
     * @param token cookie登陆key
     * @return
     */
    @Override
    public List<TbItemChild> selCart(String token) {
        String userJson = jedisDaoImpl.get(token);
        TbUser tbUser = JsonUtils.jsonToPojo(userJson, TbUser.class);
        String listJson = jedisDaoImpl.get(cartKey+tbUser.getUsername());
        List<TbItemChild> list = JsonUtils.jsonToList(listJson, TbItemChild.class);

        return list;
    }

    /**
     * 修改购物车商品数量
     * @param id 商品id
     * @param numCart 商品数量
     * @param token
     * @return
     */
    @Override
    public EgoResult updCart(long id, int numCart, String token) {
        String userJson = jedisDaoImpl.get(token);
        TbUser tbUser = JsonUtils.jsonToPojo(userJson, TbUser.class);
        String key = cartKey + tbUser.getUsername();
        String listJson = jedisDaoImpl.get(key);
        List<TbItemChild> list = JsonUtils.jsonToList(listJson, TbItemChild.class);

        for (TbItemChild child : list){
            if (child.getId()==id){
                child.setNumCart(numCart);
                jedisDaoImpl.set(key, JsonUtils.objectToJson(list));
                return null;
            }
        }
        return null;
    }

    /**
     * 删除购物车商品
     * @param id 商品id
     * @param token
     * @return
     */
    @Override
    public EgoResult delCart(long id, String token) {
        String userJson = jedisDaoImpl.get(token);
        TbUser tbUser = JsonUtils.jsonToPojo(userJson, TbUser.class);
        String key = cartKey + tbUser.getUsername();
        String listJson = jedisDaoImpl.get(key);
        List<TbItemChild> list = JsonUtils.jsonToList(listJson, TbItemChild.class);

        for (TbItemChild child : list){
            if (child.getId()==id){
                list.remove(child);
                jedisDaoImpl.set(key, JsonUtils.objectToJson(list));
                return null;
            }
        }
        return null;
    }


}
