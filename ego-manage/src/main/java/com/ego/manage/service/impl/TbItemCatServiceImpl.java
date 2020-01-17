package com.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUiTree;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.manage.service.TbItemCatService;
import com.ego.pojo.TbItemCat;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TbItemCatServiceImpl implements TbItemCatService {
    @Reference
    private TbItemCatDubboService tbItemCatDubboServiceImpl;

    /**
     * 关联选择类项目功能
     * @param pid
     * @return
     */
    @Override
    public List<EasyUiTree> selItemCat(long pid) {
        List<TbItemCat> list = tbItemCatDubboServiceImpl.selTbItemCat(pid);
        List<EasyUiTree> listTree = new ArrayList<>();
        for (TbItemCat cat : list) {
            EasyUiTree tree = new EasyUiTree();
            tree.setId(cat.getId());
            tree.setText(cat.getName());
            tree.setState(cat.getIsParent()?"closed":"open");
            listTree.add(tree);
        }
        return listTree;
    }

}
