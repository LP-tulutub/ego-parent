package com.ego.item.controller;

import com.ego.commons.pojo.TbItemChild;
import com.ego.item.service.TbItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
public class TbItemController {
    @Resource
    private TbItemService tbItemService;

    /**
     * 商品详情
     * @param id 商品id
     * @param model
     * @return
     */
    @RequestMapping("item/{id}.html")
    public String itemDetails(@PathVariable long id, Model model){
        TbItemChild tbItemChild = tbItemService.itemDetails(id);

        model.addAttribute("item", tbItemChild);
        return "item";
    }
}
