package com.ego.manage.controller;

import com.ego.commons.pojo.EasyUiTree;
import com.ego.manage.service.TbItemCatService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class TbItemCatController {
    @Resource
    private TbItemCatService tbItemCatServiceImpl;

    /**
     * 新增商品的选择项目类型
     * @param id
     * @return
     */
    @RequestMapping("item/cat/list")
    @ResponseBody
    public List<EasyUiTree> catList(@RequestParam(defaultValue="0") long id){
        return tbItemCatServiceImpl.selItemCat(id);
    }
}
