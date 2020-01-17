package com.ego.item.controller;

import com.ego.item.pojo.PortalMenu;
import com.ego.item.service.TbItemCatService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class TestController {
    @Resource
    TbItemCatService tbItemCatServiceImpl;

    /**
     * test1测试递归转换循环
     * @return
     */
    @RequestMapping("test/test1")
    @ResponseBody
    public PortalMenu test1(){
        PortalMenu test = tbItemCatServiceImpl.test();
        return test;
    }

}
