package com.ego.order.controller;

import com.ego.order.pojo.MyOrderParam;
import com.ego.order.service.OrderService;
import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Auther: cty
 * @Date: 2020/6/1 10:07
 * @Description:
 * @version: 1.0
 */
@Controller
public class OrderController {
    @Resource
    private OrderService orderServiceImpl;

    /**
     * 订单确认
     * @param ids
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("order/order-cart.html")
    public String showOrder(@RequestParam("id") List<Long> ids, HttpServletRequest request, Model model)
    {
        model.addAttribute("cartList", orderServiceImpl.getOrderItem(ids, request));
        return "order-cart";
    }


    @RequestMapping("order/create.html")
    public String createOrder(MyOrderParam param, HttpServletRequest request, Model model)
    {
        TbOrder order = new TbOrder();
        order.setPayment(param.getPayment());
        order.setPaymentType(param.getPaymentType());

        List<TbOrderItem> orderItems = param.getOrderItems();

        TbOrderShipping orderShipping = param.getOrderShipping();

        int index = orderServiceImpl.createOrder(order, orderItems, orderShipping, request, model);
        if(index == 1)
            return "success";
        else
            return "error/exception";
    }


}
