package com.ego.manage.controller;

import com.ego.commons.pojo.EasyUiTree;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbContentCategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class TbContentCategoryController {
    @Resource
    TbContentCategoryService tbContentCategoryServiceImpl;

    /**
     * 按父id查询
     * @param id 父id
     * @return 一层内容
     */
    @RequestMapping("content/category/list")
    @ResponseBody
    public List<EasyUiTree> contentCL(@RequestParam(defaultValue = "0") long id){
        return tbContentCategoryServiceImpl.selTbContentCategoryPid(id);
    }

    /**
     * 新增商品类目
     * @param parentId 新增类目的父id
     * @param name 新增类目名称
     * @return
     */
    @RequestMapping("content/category/create")
    @ResponseBody
    public EgoResult create(Long parentId, String name){
        return tbContentCategoryServiceImpl.create(name, parentId);
    }

    /**
     * 商品类目名称修改
     * @param id 商品类目id
     * @param name 商品类目新名称
     * @return
     */
    @RequestMapping("content/category/update")
    @ResponseBody
    public EgoResult update(Long id, String name){
        return tbContentCategoryServiceImpl.update(id, name);
    }

    /**
     * 删除商品类目
     * @param id 删除商品类目名称
     * @return
     */
    @RequestMapping("content/category/delete")
    @ResponseBody
    public EgoResult delete(Long id){
        return tbContentCategoryServiceImpl.delete(id);
    }
}
