package com.ego.item.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.item.service.TbItemDescService;
import com.ego.pojo.TbItemDesc;
import org.springframework.stereotype.Service;

@Service
public class TbItemDescServiceImpl implements TbItemDescService {
    @Reference
    private TbItemDescDubboService tbItemDescDubboServiceImpl;

    /**
     * 查询商品描述信息
     * @param id 商品id
     * @return
     */
    @Override
    public String showItemDesc(long id) {

        TbItemDesc tbItemDesc = tbItemDescDubboServiceImpl.selById(id);
        String desc = tbItemDesc.getItemDesc();

        return desc;
    }
}
