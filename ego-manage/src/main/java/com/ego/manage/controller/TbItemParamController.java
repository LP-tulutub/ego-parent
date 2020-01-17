package com.ego.manage.controller;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbItemParamService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class TbItemParamController {
    @Resource
    private TbItemParamService tbItemParamServiceImpl;

    /**
     * 联合查询param与cat
     * @param page 第几页
     * @param rows 每页多少条
     * @return
     */
    @RequestMapping("item/param/list")
    @ResponseBody
    public EasyUIDataGrid paramList(int page, int rows){
        return tbItemParamServiceImpl.selTbItemPC(page, rows);
    }

    /**
     * 删除id商品的规格参数
     * @param ids 多个id
     * @return
     */
    @RequestMapping("item/param/delete")
    @ResponseBody
    public EgoResult paramDelete(String ids){
        EgoResult er = tbItemParamServiceImpl.delTbItemP(ids);
        return er;
    }

    /**
     * 根据cat_id查询单条数据
     * @param catId param与cat关联的id
     * @return
     */
    @RequestMapping("item/param/query/itemcatid/{catId}")
    @ResponseBody
    public EgoResult paramQICatId(@PathVariable long catId){
        return  tbItemParamServiceImpl.selTbItemParamCatId(catId);
    }

    /**
     * 新增项目类规格参数
     * @param tbItemParam 规格参数
     * @param catId param与cat表对应的id
     * @return
     */
    @RequestMapping("item/param/save/{catId}")
    @ResponseBody
    public EgoResult paramSave(TbItemParam tbItemParam, @PathVariable long catId){
        return tbItemParamServiceImpl.insTbItemParam(tbItemParam, catId);
    }
}
