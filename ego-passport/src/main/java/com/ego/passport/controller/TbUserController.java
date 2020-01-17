package com.ego.passport.controller;

import com.ego.commons.pojo.EgoResult;
import com.ego.passport.service.TbUserService;
import com.ego.pojo.TbUser;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class TbUserController {
    @Resource
    private TbUserService tbUserServiceImpl;

    /**
     * 访问登陆页面
     * @return
     */
    @RequestMapping("user/showLogin")
    public String showLogin(@RequestHeader(value="Referer",defaultValue="") String url, Model model, String interurl){
        if(interurl!=null&&!interurl.equals("")){
            model.addAttribute("redirect", interurl);
        }else if(!url.equals("")){
            model.addAttribute("redirect", url);
        }
        return "login";
    }

    /**
     * 用户登陆
     * @param tbUser
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("user/login")
    @ResponseBody
    public EgoResult loginUser(TbUser tbUser, HttpServletRequest request, HttpServletResponse response){
        return tbUserServiceImpl.loginUser(tbUser, request, response);
    }

    /**
     * cookie登陆
     * @param token redis的id
     * @param callback
     * @return
     */
    @RequestMapping("user/token/{token}")
    @ResponseBody
    public Object cookieLogin(@PathVariable String token, String callback){
        EgoResult egoResult = tbUserServiceImpl.cookieLogin(token);
        if (callback!=null&&!callback.equals("")){
            MappingJacksonValue mjv = new MappingJacksonValue(egoResult);
            mjv.setJsonpFunction(callback);
            return mjv;
        }

        return egoResult;
    }

    /**
     * 退出登陆
     * @param token redis的id
     * @param callback
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("user/logout/{token}")
    @ResponseBody
    public Object logout(@PathVariable String token, String callback, HttpServletRequest request, HttpServletResponse response){
        EgoResult egoResult = tbUserServiceImpl.logout(token, request, response);
        if (callback!=null&&!callback.equals("")){
            MappingJacksonValue mjv = new MappingJacksonValue(egoResult);
            mjv.setJsonpFunction(callback);
            return mjv;
        }
        return egoResult;

    }
}
