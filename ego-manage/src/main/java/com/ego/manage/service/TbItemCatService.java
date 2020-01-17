package com.ego.manage.service;

import com.ego.commons.pojo.EasyUiTree;

import java.util.List;

public interface TbItemCatService {
    /**
     * 关联选择类项目功能
     * @return
     */
    List<EasyUiTree> selItemCat(long pid);

}
