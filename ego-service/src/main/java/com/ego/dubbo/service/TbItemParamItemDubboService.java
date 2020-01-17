package com.ego.dubbo.service;

import com.ego.pojo.TbItemParamItem;

public interface TbItemParamItemDubboService {
    /**
     * TbItemParamItem表按itemId查询规格参数
     * @param itemId 商品id
     * @return
     */
    TbItemParamItem selByItemId(long itemId);
}
