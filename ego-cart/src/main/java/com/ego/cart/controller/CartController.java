package com.ego.cart.controller;

import com.ego.cart.service.CartService;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.CookieUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CartController {
    @Resource
    private CartService CartServiceImpl;

    /**
     * 加入购物车
     * @param id 商品id
     * @param num 加入数量
     * @param request
     * @return
     */
    @RequestMapping("cart/add/{id}.html")
    public String addCart(@PathVariable long id, int num, HttpServletRequest request){
        String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
        EgoResult egoResult = CartServiceImpl.addCart(id, num, token);
        return "cartSuccess";
    }

    /**
     * 购物车页面
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("cart/cart.html")
    public String showCart(Model model,HttpServletRequest request){
        String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
        model.addAttribute("cartList", CartServiceImpl.selCart(token));
        return "cart";
    }

    /**
     * 修改购物车商品数量
     * @param id 商品id
     * @param numCart 商品数量
     * @param request
     * @return
     */
    @RequestMapping({"cart/update/num/{id}/{numCart}.action", "service/cart/update/num/{id}/{numCart}"})
    @ResponseBody
    public EgoResult updCart(@PathVariable long id, @PathVariable int numCart, HttpServletRequest request){
        String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
        EgoResult egoResult = CartServiceImpl.updCart(id, numCart, token);
        return egoResult;
    }

    /**
     * 删除购物车商品
     * @param id 商品id
     * @param request
     * @return
     */
    @RequestMapping("cart/delete/{id}.action")
    @ResponseBody
    public EgoResult delCart(@PathVariable long id, HttpServletRequest request){
        String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
        EgoResult egoResult = CartServiceImpl.delCart(id, token);
        return egoResult;
    }
}
