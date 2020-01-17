package com.ego.manage.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemParamItem;

public interface TbItemService {
    /**
     * 商品查询
     * @param page
     * @param rows
     * @return
     */
    EasyUIDataGrid show(int page,int rows);

    /**
     * 商品状态修改
     * @param ids
     * @param state
     * @return
     */
    int updItemState(String ids, byte state);

    /**
     * 添加新的商品信息
     * @param tbItem 商品信息
     * @param desc 商品描述
     * @param itemParams 商品规格参数
     * @return
     */
    int insTtemADesc(TbItem tbItem, String desc, String itemParams);
}
