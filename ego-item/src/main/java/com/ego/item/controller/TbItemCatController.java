package com.ego.item.controller;

import com.ego.item.service.TbItemCatService;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class TbItemCatController {
    @Resource
    private TbItemCatService tbItemCatServiceImpl;

    /**
     * 京东类似的导航显示
     * @return 要求的json格式
     */
    @RequestMapping("rest/itemcat/all")
    @ResponseBody
    public MappingJacksonValue restIA(String callback){
        MappingJacksonValue mjv = new MappingJacksonValue(tbItemCatServiceImpl.selTbItemCatAll());
        mjv.setJsonpFunction(callback);
        return mjv;
    }



}
