package com.ego.item.controller;

import com.ego.item.service.TbItemDescService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class TbItemDescController {
    @Resource
    private TbItemDescService tbItemDescServiceImpl;

    /**
     * 商品描述查询
     * @param id 商品id
     * @return
     */
    @RequestMapping(value = "item/desc/{id}.html", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String showItemDesc(@PathVariable long id){
        return tbItemDescServiceImpl.showItemDesc(id);
    }


}
