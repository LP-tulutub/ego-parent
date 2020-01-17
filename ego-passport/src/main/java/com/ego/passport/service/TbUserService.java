package com.ego.passport.service;

import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface TbUserService {
    /**
     * 用户登陆
     * @param tbUser
     * @return
     */
    EgoResult loginUser(TbUser tbUser, HttpServletRequest request, HttpServletResponse response);

    /**
     * cookie登陆
     * @param token
     * @return
     */
    EgoResult cookieLogin(String token);

    /**
     * 退出登陆
     * @param token redis的id
     * @param request 操作cookie
     * @param response 操作cookie
     * @return
     */
    EgoResult logout(String token, HttpServletRequest request, HttpServletResponse response);
}
