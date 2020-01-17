package com.ego.manage.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbContent;

public interface TbContentService {
    /**
     * 内容查询功能
     * @param categoryId
     * @param page 第几页
     * @param rows 每页多少条
     * @return
     */
    EasyUIDataGrid list(long categoryId, int page, int rows);

    /**
     * 新增
     * @param tbContent
     * @return
     */
    EgoResult save(TbContent tbContent);
}
