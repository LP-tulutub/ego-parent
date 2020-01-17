package com.ego.dubbo.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbItemParam;

public interface TbItemParamDubboService {
    /**
     *规格参数全部查询
     * @param page 第几页
     * @param rows 每页多少条
     * @return 规格参数表所有信息
     */
    EasyUIDataGrid selTbItemParam(int page, int rows);

    /**
     * 按id删除
     * @param ids 多个id
     * @return
     */
    int delTbItemParam(String ids) throws Exception;

    /**
     * 根据cat_id查询单条数据
     * @param catId param与cat关联的id
     * @return
     */
    TbItemParam selTbItemParamCatId(long catId);

    /**
     * 新增项目类规格参数
     * @param tbItemParam 规格参数
     * @return
     */
    int insTbItemParam(TbItemParam tbItemParam);

}
