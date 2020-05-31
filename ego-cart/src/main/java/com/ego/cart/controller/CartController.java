package com.ego.cart.controller;

import com.ego.cart.service.CartService;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.TbItemChild;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Auther: cty
 * @Date: 2020/5/31 9:47
 * @Description:
 * @version: 1.0
 */
@Controller
public class CartController {
    @Resource
    private CartService cartServiceImpl;

    /**
     * 将商品添加到购物车（redis缓存）
     * @param itemId
     * @param num
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("cart/add/{itemId}.html")
    public String cartSuccess(@PathVariable long itemId, int num, Model model, HttpServletRequest request)
    {
        cartServiceImpl.addCart(itemId, num, request);
        model.addAttribute("num", num);
        return "cartSuccess";
    }

    /**
     * 获取购物车信息
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("cart/cart.html")
    public String showCart(Model model, HttpServletRequest request)
    {
        List<TbItemChild> userCartList = cartServiceImpl.getCart(request);
        model.addAttribute("cartList", userCartList);
        return "cart";
    }

    /**
     * 更新购物车信息
     * @param itemId
     * @param updateNum
     * @param request
     * @return
     */
    @RequestMapping("cart/update/num/{itemId}/{updateNum}.action")
    @ResponseBody
    public EgoResult updateNum(@PathVariable long itemId, @PathVariable int updateNum, HttpServletRequest request)
    {
        return cartServiceImpl.updateCart(itemId, updateNum, request);
    }

    /**
     * 删除购物车中的商品
     * @param itemId
     * @param request
     * @return
     */
    @RequestMapping("cart/delete/{itemId}.action")
    @ResponseBody
    public EgoResult deleteItem(@PathVariable long itemId, HttpServletRequest request)
    {
        return cartServiceImpl.deleteItem(itemId, request);
    }

}
