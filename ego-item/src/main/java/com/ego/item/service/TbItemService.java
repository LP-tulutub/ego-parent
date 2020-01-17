package com.ego.item.service;

import com.ego.commons.pojo.TbItemChild;

public interface TbItemService {
    /**
     * 商品详情
     * @param id 商品id
     * @return
     */
    TbItemChild itemDetails(long id);
}
