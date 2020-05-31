package com.ego.cart.service;

import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.TbItemChild;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Auther: cty
 * @Date: 2020/5/31 11:18
 * @Description:
 * @version: 1.0
 */
public interface CartService {

    /**
     * 将商品添加到购物车（存储到缓存中）
     * @param itemId
     * @param num
     * @param request
     */
    void addCart(long itemId, int num, HttpServletRequest request);

    /**
     * 获取购物车信息
     * @param request
     * @return
     */
    List<TbItemChild> getCart(HttpServletRequest request);

    /**
     * 更新购物车信息
     * @param itemId
     * @param updateNum
     * @param request
     * @return
     */
    EgoResult updateCart(long itemId, int updateNum, HttpServletRequest request);

    /**
     * 删除购物车中的商品
     * @param itemId
     * @param request
     * @return
     */
    EgoResult deleteItem(long itemId, HttpServletRequest request);


}
