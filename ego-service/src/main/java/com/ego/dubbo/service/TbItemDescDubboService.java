package com.ego.dubbo.service;

import com.ego.pojo.TbItemDesc;

public interface TbItemDescDubboService {
    /**
     * 查询商品描述
     * @param id 商品id
     * @return
     */
    TbItemDesc selById(long id);
}
