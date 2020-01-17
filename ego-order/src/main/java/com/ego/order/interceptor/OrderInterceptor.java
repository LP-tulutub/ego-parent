package com.ego.order.interceptor;

import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.JsonUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OrderInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse rep, Object o) throws Exception {
        String tt_token = CookieUtils.getCookieValue(req, "TT_TOKEN");
        if (tt_token!=null&&!tt_token.equals("")){
            String postJson = HttpClientUtil.doPost("http://localhost:8084/user/token/" + tt_token);
            EgoResult egoResult = JsonUtils.jsonToPojo(postJson, EgoResult.class);
            if (egoResult.getStatus()==200){
                return true;
            }
        }
        String num = req.getParameter("num");
        rep.sendRedirect("http://localhost:8084/user/showLogin?interurl="+req.getRequestURL()+"%3Fnum="+num);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
