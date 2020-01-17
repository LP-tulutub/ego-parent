package com.ego.dubbo.service.impl;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.dubbo.service.TbItemParamDubboService;
import com.ego.mapper.TbItemParamItemMapper;
import com.ego.mapper.TbItemParamMapper;
import com.ego.pojo.TbItemParam;
import com.ego.pojo.TbItemParamExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

public class TbItemParamDubboServiceImpl implements TbItemParamDubboService {
    @Resource
    private TbItemParamMapper tbItemParamMapper;

    /**
     * 规格参数全部查询
     * @param page 第几页
     * @param rows 每页多少条
     * @return
     */
    @Override
    public EasyUIDataGrid selTbItemParam(int page, int rows) {
        PageHelper.startPage(page, rows);
        List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(new TbItemParamExample());
        PageInfo<TbItemParam> pi = new PageInfo<>(list);

        EasyUIDataGrid dategrid = new EasyUIDataGrid();
        dategrid.setRows(pi.getList());
        dategrid.setTotal(pi.getTotal());

        return dategrid;
    }

    /**
     *按id删除
     * @param ids 多个id
     * @return
     */
    @Override
    public int delTbItemParam(String ids) throws Exception {
        System.out.println("ego-service-impl# 删除操作成功");
        String[] list = ids.split(",");
        int index = 0;
        for(String ll : list){
            index+= tbItemParamMapper.deleteByPrimaryKey(Long.parseLong(ll));
        }
        if(index==list.length){
            return 1;
        }else {
            System.out.println("ego-service-impl# 删除失败");
            throw new Exception("删除失败");
        }
    }

    /**
     * 根据cat_id查询单条数据
     * @param catId param与cat关联的id
     * @return
     */
    @Override
    public TbItemParam selTbItemParamCatId(long catId) {
        TbItemParamExample tbItemParamExample = new TbItemParamExample();
        tbItemParamExample.createCriteria().andItemCatIdEqualTo(catId);
        List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(tbItemParamExample);
        if(list!=null&&list.size()>0){
            //要求每个类目只能有一个模板
            return list.get(0);
        }
        return null;
    }

    /**
     * 新增项目类规格参数
     * @param tbItemParam 规格参数
     * @return
     */
    @Override
    public int insTbItemParam(TbItemParam tbItemParam) {
        Date date = new Date();
        tbItemParam.setCreated(date);
        tbItemParam.setUpdated(date);
        int index = tbItemParamMapper.insertSelective(tbItemParam);
        return index;
    }


}
