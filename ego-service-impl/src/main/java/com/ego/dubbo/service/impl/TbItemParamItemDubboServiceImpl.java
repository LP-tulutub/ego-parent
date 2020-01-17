package com.ego.dubbo.service.impl;

import com.ego.dubbo.service.TbItemParamItemDubboService;
import com.ego.mapper.TbItemParamItemMapper;
import com.ego.pojo.TbItemParamItem;
import com.ego.pojo.TbItemParamItemExample;

import javax.annotation.Resource;
import java.util.List;

public class TbItemParamItemDubboServiceImpl implements TbItemParamItemDubboService {
    @Resource
    private TbItemParamItemMapper tbItemParamItemMapper;

    /**
     * TbItemParamItem表按itemId查询规格参数
     * @param itemId 商品id
     * @return
     */
    @Override
    public TbItemParamItem selByItemId(long itemId) {
        TbItemParamItemExample tbItemParamItemExample = new TbItemParamItemExample();
        tbItemParamItemExample.createCriteria().andItemIdEqualTo(itemId);
        List<TbItemParamItem> tbItemParamItems = tbItemParamItemMapper.selectByExampleWithBLOBs(tbItemParamItemExample);

        if (tbItemParamItems!=null&&!tbItemParamItems.equals("")){
            return tbItemParamItems.get(0);
        }

        return null;
    }
}
