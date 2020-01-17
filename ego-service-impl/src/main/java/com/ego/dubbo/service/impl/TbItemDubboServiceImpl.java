package com.ego.dubbo.service.impl;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.mapper.TbItemDescMapper;
import com.ego.mapper.TbItemMapper;
import com.ego.mapper.TbItemParamItemMapper;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemExample;
import com.ego.pojo.TbItemParamItem;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;
import java.util.List;

public class TbItemDubboServiceImpl implements TbItemDubboService {
    @Resource
    private TbItemMapper tbItemMapper;
    @Resource
    private TbItemDescMapper tbItemDescMapper;
    @Resource
    private TbItemParamItemMapper tbItemParamItemMapper;

    /**
     * 商品查询分页
     * @param page 第几页
     * @param rows 一页多少条
     * @return
     */
    @Override
    public EasyUIDataGrid show(int page, int rows) {
        PageHelper.startPage(page, rows);
        //查询全部
        List<TbItem> list = tbItemMapper.selectByExample(new TbItemExample());
        //分页代码
        //设置分页条件
        PageInfo<TbItem> pi = new PageInfo<>(list);
        //放入到实体类
        EasyUIDataGrid dategrid = new EasyUIDataGrid();
        dategrid.setRows(pi.getList());
        dategrid.setTotal(pi.getTotal());

        return dategrid;
    }

    /**
     * 修改商品状态
     * @param tbItem
     * @return
     */
    @Override
    public int updItemState(TbItem tbItem) {
        return tbItemMapper.updateByPrimaryKeySelective(tbItem);
    }

    /**
     * 添加新的商品信息
     * @param tbItem 商品信息表
     * @param tbItemDesc 描述表
     * @param tbItemParamItem 规格参数表
     * @return
     * @throws Exception
     */
    @Override
    public int insItemDate(TbItem tbItem, TbItemDesc tbItemDesc, TbItemParamItem tbItemParamItem) throws Exception {
        int index = 0;
        try {
            index = tbItemMapper.insertSelective(tbItem);
            index+= tbItemDescMapper.insertSelective(tbItemDesc);
            index+= tbItemParamItemMapper.insertSelective(tbItemParamItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(index==3){
            return 1;
        }else{
            throw new Exception("添加失败");
        }

    }

    /**
     * 按是否上架状态查询全部
     * @param status 是否上架
     * @return
     */
    @Override
    public List<TbItem> selAllByStatus(byte status) {
        TbItemExample tbItemExample = new TbItemExample();
        tbItemExample.createCriteria().andStatusEqualTo(status);
        return tbItemMapper.selectByExample(tbItemExample);
    }

    /**
     * 商品详情
     * @param id 商品id
     * @return
     */
    @Override
    public TbItem selById(long id) {
        return tbItemMapper.selectByPrimaryKey(id);
    }


}
