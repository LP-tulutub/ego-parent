package com.ego.manage.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbItemParam;

public interface TbItemParamService {
    /**
     * 联合查询param与cat
     * @param page 第几页
     * @param rows 每页多少条
     * @return
     */
    EasyUIDataGrid selTbItemPC(int page, int rows);

    /**
     * 删除id商品的格参数
     * @param ids 多个id
     * @return
     */
    EgoResult delTbItemP(String ids);

    /**
     * 根据cat_id查询单条数据
     * @param catId param与cat关联的id
     * @return
     */
    EgoResult selTbItemParamCatId(long catId);

    /**
     * 新增项目类规格参数
     * @param tbItemParam 规格参数
     * @param catId param与cat表对应的id
     * @return
     */
    EgoResult insTbItemParam(TbItemParam tbItemParam, long catId);

}
