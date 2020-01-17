package com.ego.dubbo.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbContent;

import java.util.List;

public interface TbContentDubboService {
    /**
     * 内容查询功能
     * @param categoryId
     * @param page 第几页
     * @param rows 每页多少条
     * @return
     */
    EasyUIDataGrid sqlByCidPage(long categoryId, int page, int rows);

    /**
     * 新增内容
     * @param tbContent
     * @return
     */
    int insTbContent(TbContent tbContent);

    /**
     * 显示大广告
     * @param count 显示几条
     * @param isSort 是否倒序排序
     * @return
     */
    List<TbContent> selAnyWayAd(int count, boolean isSort);
}
