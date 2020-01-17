package com.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.IDUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.manage.service.TbItemService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;
import com.ego.redis.dao.JedisDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class TbItemServiceImpl implements TbItemService {
    @Reference
    private TbItemDubboService tbItemDubboServiceImpl;
    @Value("${solr.url.add}")
    private String url;
    @Resource
    private JedisDao jedisDaoimpl;
    @Value("${redis.item.key}")
    private String itemKey;

    /**
     * 商品查询
     * @param page
     * @param rows
     * @return
     */
    @Override
    public EasyUIDataGrid show(int page, int rows) {
        return tbItemDubboServiceImpl.show(page,rows);
    }

    /**
     * 商品状态修改
     * @param ids
     * @param state
     * @return
     */
    @Override
    public int updItemState(String ids, byte state) {
        int index = 0;
        TbItem tbItem = new TbItem();
        String[] idStr = ids.split(",");
        for(String id : idStr){
            tbItem.setId(Long.parseLong(id));
            tbItem.setStatus(state);
            index += tbItemDubboServiceImpl.updItemState(tbItem);
        }
        if(index==idStr.length){
            //redis删除、下架同步
            //System.out.println("ego-manage(TbItemServiceImpl)# status："+state);
            if ((int)state==2||(int)state==3){
                for(String id : idStr){
                    //System.out.println("ego-manage(TbItemServiceImpl)# redis删除："+itemKey+id);
                    jedisDaoimpl.del(itemKey+id);
                }
            }

            return 1;
        }
        return 0;
    }

    /**
     * 添加新的商品信息
     * @param tbItem 商品信息
     * @param desc 商品描述
     * @param itemParams 商品规格参数
     * @return
     */
    @Override
    public int insTtemADesc(TbItem tbItem, String desc, String itemParams) {
        long id = IDUtils.genItemId();
        tbItem.setId(id);
        Date date = new Date();
        tbItem.setCreated(date);
        tbItem.setUpdated(date);
        tbItem.setStatus((byte) 1);
        //商品描述表
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemDesc(desc);
        itemDesc.setItemId(id);
        itemDesc.setCreated(date);
        itemDesc.setUpdated(date);
        //规格参数表
        TbItemParamItem tbItemParamItem = new TbItemParamItem();
        tbItemParamItem.setCreated(date);
        tbItemParamItem.setUpdated(date);
        tbItemParamItem.setParamData(itemParams);

        int index = 0;

        try {
            index = tbItemDubboServiceImpl.insItemDate(tbItem, itemDesc, tbItemParamItem);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (index==1){
            final TbItem itemFinal = tbItem;
            final String descFinal = desc;
            new Thread(){
                public void run() {
                    Map<String,Object> map = new HashMap<>();
                    map.put("item", itemFinal);
                    map.put("desc", descFinal);

                    HttpClientUtil.doPostJson(url, JsonUtils.objectToJson(map));
                    //使用java代码调用其他项目的控制器
                };
            }.start();
        }

        return index;
    }
}
