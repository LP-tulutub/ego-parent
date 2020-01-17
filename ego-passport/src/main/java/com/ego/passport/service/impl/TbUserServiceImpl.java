package com.ego.passport.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbUserDubboService;
import com.ego.passport.service.TbUserService;
import com.ego.pojo.TbUser;
import com.ego.redis.dao.JedisDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Service
public class TbUserServiceImpl implements TbUserService {
    @Reference
    private TbUserDubboService tbUserDubboServiceImpl;
    @Resource
    private JedisDao jedisDaoImpl;

    /**
     * 用户登陆
     * @param tbUser
     * @return
     */
    @Override
    public EgoResult loginUser(TbUser tbUser, HttpServletRequest request, HttpServletResponse response) {
        EgoResult result = new EgoResult();

        TbUser user = tbUserDubboServiceImpl.selByUser(tbUser);
        if (user!=null){
            //当用户登录成功后把用户信息放入到redis中
            String key = UUID.randomUUID().toString();
            jedisDaoImpl.set(key, JsonUtils.objectToJson(user));
            jedisDaoImpl.expire(key, 60*60*24*7);
            //产生Cookie
            CookieUtils.setCookie(request, response, "TT_TOKEN", key, 60*60*24*7);
            result.setStatus(200);
        }else {
            result.setMsg("登陆失败");
        }

        return result;
    }

    /**
     * cookie登陆
     * @param token
     * @return
     */
    @Override
    public EgoResult cookieLogin(String token) {
        EgoResult egoResult = new EgoResult();
        String userJson = jedisDaoImpl.get(token);
        if (userJson!=null&&!userJson.equals("")){
            TbUser tbUser = JsonUtils.jsonToPojo(userJson, TbUser.class);
            tbUser.setPassword(null);

            egoResult.setStatus(200);
            egoResult.setMsg("OK");
            egoResult.setData(tbUser);
        }else {
            egoResult.setMsg("登陆失败");
        }


        return egoResult;
    }

    /**
     * 退出登陆
     * @param token redis的id
     * @param request 操作cookie
     * @param response 操作cookie
     * @return
     */
    @Override
    public EgoResult logout(String token, HttpServletRequest request, HttpServletResponse response) {
        EgoResult egoResult = new EgoResult();

        jedisDaoImpl.del(token);
        CookieUtils.deleteCookie(request, response, token);

        egoResult.setStatus(200);
        egoResult.setMsg("OK");
        return egoResult;
    }
}
