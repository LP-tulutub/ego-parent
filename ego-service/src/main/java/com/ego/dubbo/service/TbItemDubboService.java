package com.ego.dubbo.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;

import java.util.List;

public interface TbItemDubboService {
    /**
     * 商品分页查询
     * @param page 第几页
     * @param rows 一页多少条
     * @return
     */
    EasyUIDataGrid show(int page,int rows);

    /**
     * 修改商品状态值
     * @param tbItem
     * @return
     */
    int updItemState(TbItem tbItem);

    /**
     * 添加新的商品信息
     * @param tbItem 商品信息表
     * @param tbItemDesc 描述表
     * @param tbItemParamItem 规格参数表
     * @return
     * @throws Exception
     */
    int insItemDate(TbItem tbItem, TbItemDesc tbItemDesc, TbItemParamItem tbItemParamItem) throws Exception;

    /**
     * 按是否上架状态查询全部
     * @param status 是否上架
     * @return
     */
    List<TbItem> selAllByStatus(byte status);

    /**
     * 按id查询
     * @param id 商品id
     * @return
     */
    TbItem selById(long id);
}
