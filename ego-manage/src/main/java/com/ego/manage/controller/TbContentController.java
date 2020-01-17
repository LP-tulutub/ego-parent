package com.ego.manage.controller;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbContentService;
import com.ego.pojo.TbContent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class TbContentController {
    @Resource
    TbContentService tbContentServiceImpl;

    /**
     * 内容查询功能
     * @param categoryId
     * @param page 第几页
     * @param rows 每页多少条
     * @return
     */
    @RequestMapping("content/query/list")
    @ResponseBody
    public EasyUIDataGrid list(long categoryId, int page, int rows){
        return tbContentServiceImpl.list(categoryId, page, rows);
    }

    /**
     * 内容新增
     * @param tbContent
     * @return
     */
    @RequestMapping("content/save")
    @ResponseBody
    public EgoResult save(TbContent tbContent){
        return tbContentServiceImpl.save(tbContent);
    }

}
