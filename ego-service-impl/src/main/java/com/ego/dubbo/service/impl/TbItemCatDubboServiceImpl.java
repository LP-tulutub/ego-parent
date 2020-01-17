package com.ego.dubbo.service.impl;

import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.mapper.TbItemCatMapper;
import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemCatExample;

import javax.annotation.Resource;
import java.util.List;

public class TbItemCatDubboServiceImpl implements TbItemCatDubboService {
    @Resource
    private TbItemCatMapper tbItemCatMapper;

    /**
     * 关联选择类项目功能
     * @return
     */
    @Override
    public List<TbItemCat> selTbItemCat(long pid) {
        TbItemCatExample cat = new TbItemCatExample();
        cat.createCriteria().andParentIdEqualTo(pid);
        return tbItemCatMapper.selectByExample(cat);
    }

    /**
     * 按id查询名称
     * @param id 商品id
     * @return
     */
    @Override
    public TbItemCat selTbItemCatId(long id) {
        return tbItemCatMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据是否是父节点查询
     * @param isParent
     * @return
     */
    @Override
    public List<TbItemCat> selByIsParent(boolean isParent) {
        TbItemCatExample cat = new TbItemCatExample();
        cat.createCriteria().andIsParentEqualTo(isParent);
        return tbItemCatMapper.selectByExample(cat);
    }


}
