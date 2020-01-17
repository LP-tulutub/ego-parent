package com.ego.manage.service;

import com.ego.commons.pojo.EasyUiTree;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbContentCategory;

import java.util.List;

public interface TbContentCategoryService {
    /**
     * 按父id查询
     * @param pid 父id
     * @return 一层内容
     */
    List<EasyUiTree> selTbContentCategoryPid(long pid);

    /**
     * 查重+新增
     * @param name 新增名字
     * @param pid 新增类目的父id
     * @return
     */
    EgoResult create(String name, long pid);

    /**
     * 商品类目名称修改
     * @param id 商品类目id
     * @param name 商品类目新名称
     * @return
     */
    EgoResult update(Long id,String name);

    /**
     * 商品类目删除
     * @param id
     * @return
     */
    EgoResult delete(Long id);
}
