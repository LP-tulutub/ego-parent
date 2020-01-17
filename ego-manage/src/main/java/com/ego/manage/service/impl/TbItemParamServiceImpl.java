package com.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.LPUtils;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.dubbo.service.TbItemParamDubboService;
import com.ego.manage.pojo.TbItemParamChild;
import com.ego.manage.service.TbItemParamService;
import com.ego.pojo.TbItemParam;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TbItemParamServiceImpl implements TbItemParamService {
    @Reference
    private TbItemParamDubboService tbItemParamDubboServiceImpl;
    @Reference
    private TbItemCatDubboService tbItemCatDubboServiceImpl;

    /**
     * 联合查询param与cat
     * @param page 第几页
     * @param rows 每页多少条
     * @return
     */
    @Override
    public EasyUIDataGrid selTbItemPC(int page, int rows) {

        EasyUIDataGrid datagrid = tbItemParamDubboServiceImpl.selTbItemParam(page, rows);
        //下面2中方法都可以
        //List<TbItemParam> list = (List<TbItemParam>) datagrid.getRows();
        List<TbItemParam> list = LPUtils.castList(datagrid.getRows(), TbItemParam.class);
        List<TbItemParamChild> listChild = new ArrayList<>();

        for(TbItemParam param : list){
            TbItemParamChild tbItemParamChild = new TbItemParamChild();

            tbItemParamChild.setId(param.getId());
            tbItemParamChild.setItemCatId(param.getItemCatId());
            tbItemParamChild.setParamData(param.getParamData());
            tbItemParamChild.setCreated(param.getCreated());
            tbItemParamChild.setUpdated(param.getUpdated());
            tbItemParamChild.setItemCatName(tbItemCatDubboServiceImpl.selTbItemCatId(param.getItemCatId()).getName());

            listChild.add(tbItemParamChild);
        }
        datagrid.setRows(listChild);

        return datagrid;
    }

    /**
     * 删除id商品的规格参数
     * @param ids 多个id
     * @return
     */
    @Override
    public EgoResult delTbItemP(String ids) {
        int index = 0;
        try {
            index = tbItemParamDubboServiceImpl.delTbItemParam(ids);
        } catch (Exception e) {
            e.printStackTrace();
        }
        EgoResult er = new EgoResult();
        if(index==1){
            er.setStatus(200);
        }else{
            er.setStatus(500);
        }
        return er;
    }

    /**
     * 根据cat_id查询单条数据
     * @param catId param与cat关联的id
     * @return
     */
    @Override
    public EgoResult selTbItemParamCatId(long catId) {
        EgoResult er = new EgoResult();
        TbItemParam tbItemParam = tbItemParamDubboServiceImpl.selTbItemParamCatId(catId);
        if(tbItemParam!=null){
            er.setData(tbItemParam);
            er.setStatus(200);
        }else{
            er.setStatus(400);
        }
        return er;
    }

    /**
     * 新增项目类规格参数
     * @param tbItemParam 规格参数
     * @param catId param与cat表对应的id
     * @return
     */
    @Override
    public EgoResult insTbItemParam(TbItemParam tbItemParam, long catId) {
        tbItemParam.setItemCatId(catId);
        int index = tbItemParamDubboServiceImpl.insTbItemParam(tbItemParam);
        EgoResult er = new EgoResult();
        if(index==1){
            er.setStatus(200);
        }
        return er;
    }
}
