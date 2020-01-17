package com.ego.dubbo.service;

import com.ego.commons.pojo.DubboResult;
import com.ego.pojo.TbContentCategory;

import java.util.List;

public interface TbContentCategoryDubboService {
    /**
     * 按父id查询
     * @param pid 父id
     * @return 按父id查询结果
     */
    List<TbContentCategory> selTbContentCategoryPid(long pid);

    /**
     * 事务回滚+插入升级新的内容
     * @param tbContentCategory 要插入的内容
     * @param pid 插入的父id
     * @return
     */
    DubboResult insUpdTbContentCategory(TbContentCategory tbContentCategory, long pid) throws Exception;

    /**
     * 商品类目重命名
     * @param id 商品类目id
     * @param name 新的商品类目名称
     * @return
     */
    int updTbContentCategoryName(long id, String name);

    /**
     * 按id查询
     * @param id 商品类目id
     * @return
     */
    TbContentCategory selTbContentCategoryid(long id);

    /**
     * 事务回滚+删除
     * @param id 删除商品类目的id
     * @return
     */
    int updTbContentCategoryStatus(Long id) throws Exception;
}
