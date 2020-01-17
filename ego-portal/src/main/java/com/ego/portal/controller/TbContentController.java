package com.ego.portal.controller;

import com.ego.portal.service.TbContentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
public class TbContentController {
    @Resource
    TbContentService tbContentServiceImpl;

    /**
     * 大广告显示
     * @param model
     * @return
     */
    @RequestMapping("showBigPic")
    public String showBigPic(Model model) {
        model.addAttribute("ad1", tbContentServiceImpl.showBigAd(6, true));
        return "index";
    }


}
