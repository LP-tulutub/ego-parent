package com.ego.dubbo.service;

import com.ego.pojo.TbItemCat;

import java.util.List;

public interface TbItemCatDubboService {
    /**
     * 关联选择类项目功能
     * @return
     */
    List<TbItemCat> selTbItemCat(long pid);

    /**
     * 按id查询名称
     * @param id 商品id
     * @return
     */
    TbItemCat selTbItemCatId(long id);

    /**
     * 根据是否是父节点查询
     * @param isParent
     * @return
     */
    List<TbItemCat> selByIsParent(boolean isParent);
}
