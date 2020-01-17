package com.ego.item.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.TbItemChild;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.item.service.TbItemService;
import com.ego.pojo.TbItem;
import com.ego.redis.dao.JedisDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TbItemServiceImpl implements TbItemService {
    @Reference
    private TbItemDubboService tbItemDubboService;
    @Resource
    private JedisDao jedisDaoImpl;
    @Value("${redis.item.key}")
    private String itemKey;

    /**
     * 商品详情
     * @param id 商品id
     * @return
     */
    @Override
    public TbItemChild itemDetails(long id) {
        String key = itemKey+id;
        if (jedisDaoImpl.exists(key)){
            String childJson = jedisDaoImpl.get(key);
            if (childJson!=null&&!childJson.equals("")){
                System.out.println("ego-item(TbItemServiceImpl)# redis中取出："+childJson);
                return JsonUtils.jsonToPojo(childJson, TbItemChild.class);
            }
        }

        TbItem tbItem = tbItemDubboService.selById(id);
        TbItemChild tbItemChild = new TbItemChild();

        tbItemChild.setId(tbItem.getId());
        tbItemChild.setTitle(tbItem.getTitle());
        tbItemChild.setPrice(tbItem.getPrice());
        tbItemChild.setSellPoint(tbItem.getSellPoint());
        tbItemChild.setImages(tbItem.getImage()!=null&&!tbItem.getImage().equals("")?tbItem.getImage().split(","):new String[1]);
        //保存到redis
        jedisDaoImpl.set(key, JsonUtils.objectToJson(tbItemChild));
        System.out.println("ego-item(TbItemServiceImpl)# mysql中取出："+tbItemChild);
        return tbItemChild;
    }
}
