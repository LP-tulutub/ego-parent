package com.ego.dubbo.service.impl;

import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.mapper.TbItemDescMapper;
import com.ego.pojo.TbItemDesc;

import javax.annotation.Resource;

public class TbItemDescDubboServiceImpl implements TbItemDescDubboService {
    @Resource
    private TbItemDescMapper tbItemDescMapper;

    /**
     *
     * @param id 商品id
     * @return
     */
    @Override
    public TbItemDesc selById(long id) {
        return tbItemDescMapper.selectByPrimaryKey(id);
    }
}
