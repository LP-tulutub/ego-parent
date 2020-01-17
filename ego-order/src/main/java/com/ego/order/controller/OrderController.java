package com.ego.order.controller;

import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.TbItemChild;
import com.ego.commons.utils.CookieUtils;
import com.ego.order.pojo.MyOrderParam;
import com.ego.order.service.TbOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class OrderController {
    @Resource
    private TbOrderService tbOrderServiceImpl;

    /**
     * 订单系统显示商品
     * @param ids 购物车商品
     * @param request
     * @return
     */
    @RequestMapping("order/order-cart.html")
    public String orderCart(@RequestParam("id") List<Long> ids, Model model, HttpServletRequest request){
        String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
        List<TbItemChild> list = tbOrderServiceImpl.selByCart(ids, token);
        model.addAttribute("cartList", list);
        return "order-cart";
    }

    /**
     * 提交订单
     * @param model
     * @param myOrderParam 所有订单相关信息
     * @param request
     * @return
     */
    @RequestMapping("order/create.html")
    public String orderCreate(Model model, MyOrderParam myOrderParam, HttpServletRequest request){
        String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
        EgoResult egoResult = tbOrderServiceImpl.orderIns(myOrderParam, token);
        if (egoResult.getStatus()==200){
            return "my-orders";
        }else {
            model.addAttribute("message", "订单创建失败");
            return "error/exception";
        }
    }

}
