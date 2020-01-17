package com.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.DubboResult;
import com.ego.commons.pojo.EasyUiTree;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.IDUtils;
import com.ego.dubbo.service.TbContentCategoryDubboService;
import com.ego.manage.service.TbContentCategoryService;
import com.ego.pojo.TbContentCategory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TbContentCategoryServiceImpl implements TbContentCategoryService {
    @Reference
    TbContentCategoryDubboService tbContentCategoryDubboServiceImpl;

    /**
     * 按父id查询
     * @param pid 父id
     * @return 一层内容
     */
    @Override
    public List<EasyUiTree> selTbContentCategoryPid(long pid) {
        List<EasyUiTree> listTree = new ArrayList<>();
        List<TbContentCategory> list = tbContentCategoryDubboServiceImpl.selTbContentCategoryPid(pid);
        for (TbContentCategory cate : list){
            EasyUiTree easyUiTree = new EasyUiTree();
            easyUiTree.setId(cate.getId());
            easyUiTree.setText(cate.getName());
            easyUiTree.setState(cate.getIsParent()?"closed":"open");
            listTree.add(easyUiTree);
        }

        return listTree;
    }

    /**
     * 查重+新增
     * @param name 新增名字
     * @param pid 新增类目的父id
     * @return
     */
    @Override
    public EgoResult create(String name, long pid) {
        EgoResult egoResult = new EgoResult();
        List<TbContentCategory> list = tbContentCategoryDubboServiceImpl.selTbContentCategoryPid(pid);
        for (TbContentCategory cate : list){
            if (cate.getName().equals(name)){
                egoResult.setStatus(0);
                egoResult.setData("重复名称");
                return egoResult;
            }
        }
        Date date = new Date();
        TbContentCategory tbContentCategory = new TbContentCategory();
        tbContentCategory.setIsParent(false);
        tbContentCategory.setParentId(pid);
        tbContentCategory.setName(name);
        tbContentCategory.setCreated(date);
        tbContentCategory.setUpdated(date);
        tbContentCategory.setSortOrder(1);
        tbContentCategory.setStatus(1);
        long id = IDUtils.genItemId();
        tbContentCategory.setId(id);
        try {
            DubboResult dubboResult = tbContentCategoryDubboServiceImpl.insUpdTbContentCategory(tbContentCategory, pid);
            egoResult.setStatus(dubboResult.getStatus());
            egoResult.setData(dubboResult.getData());
        } catch (Exception e) {
            egoResult.setStatus(0);
            egoResult.setData(e.getMessage());
        }


        return egoResult;
    }

    /**
     * 商品类目名称修改
     * @param id 商品类目id
     * @param name 商品类目新名称
     * @return
     */
    @Override
    public EgoResult update(Long id, String name) {
        EgoResult egoResult = new EgoResult();
        TbContentCategory tbContentCategory = tbContentCategoryDubboServiceImpl.selTbContentCategoryid(id);
        List<TbContentCategory> list = tbContentCategoryDubboServiceImpl.selTbContentCategoryPid(tbContentCategory.getParentId());
        for (TbContentCategory ll : list){
            if (ll.getName().equals(name)){
                egoResult.setStatus(0);
                egoResult.setData("重复名称");
                return egoResult;
            }
        }

        int index = tbContentCategoryDubboServiceImpl.updTbContentCategoryName(id, name);
        if (index==1){
            egoResult.setStatus(200);
        }else{
            egoResult.setStatus(0);
            egoResult.setData("修改名称失败");
        }
        return egoResult;
    }

    /**
     * 商品类目删除
     * @param id
     * @return
     */
    @Override
    public EgoResult delete(Long id) {
        EgoResult er = new EgoResult();
        int index = 0;
        try {
            index = tbContentCategoryDubboServiceImpl.updTbContentCategoryStatus(id);
            if (index==1){
                er.setStatus(200);
            }
        } catch (Exception e) {
            er.setStatus(200);
            er.setData(e.getMessage());
        }

        return er;
    }
}
