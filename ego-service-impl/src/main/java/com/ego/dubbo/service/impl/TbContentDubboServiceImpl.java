package com.ego.dubbo.service.impl;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.mapper.TbContentMapper;
import com.ego.pojo.TbContent;
import com.ego.pojo.TbContentExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;
import java.util.List;

public class TbContentDubboServiceImpl implements TbContentDubboService {
    @Resource
    private TbContentMapper tbContentMapper;

    /**
     * 内容查询功能
     * @param categoryId
     * @param page 第几页
     * @param rows 每页多少条
     * @return
     */
    @Override
    public EasyUIDataGrid sqlByCidPage(long categoryId, int page, int rows) {
        PageHelper.startPage(page, rows);
        //查询
        TbContentExample example = new TbContentExample();
        if(categoryId!=0){
            example.createCriteria().andCategoryIdEqualTo(categoryId);
        }
        List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);
        //分页代码
        //设置分页条件
        PageInfo<TbContent> pi = new PageInfo<>(list);
        //放入到实体类
        EasyUIDataGrid dategrid = new EasyUIDataGrid();
        dategrid.setRows(pi.getList());
        dategrid.setTotal(pi.getTotal());
        return dategrid;
    }

    /**
     * 新增内容
     * @param tbContent
     * @return
     */
    @Override
    public int insTbContent(TbContent tbContent) {
        return tbContentMapper.insertSelective(tbContent);
    }

    /**
     * 显示大广告
     * @param count 显示几条
     * @param isSort 是否倒序排序
     * @return
     */
    @Override
    public List<TbContent> selAnyWayAd(int count, boolean isSort) {
        TbContentExample example = new TbContentExample();
        //排序
        if(isSort){
            example.setOrderByClause("updated desc");
        }
        if(count!=0){
            PageHelper.startPage(1, count);
            List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);
            PageInfo<TbContent> pi = new PageInfo<>(list);
            return pi.getList();
        }else{
            return tbContentMapper.selectByExampleWithBLOBs(example);
        }
    }
}
