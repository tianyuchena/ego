package com.ego.order.service;

import com.ego.commons.pojo.TbItemChild;
import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Auther: cty
 * @Date: 2020/6/1 11:54
 * @Description:
 * @version: 1.0
 */
public interface OrderService {

    /**
     * 根据选定商品id从缓存中获取商品信息
     * @param ids
     * @return
     */
    List<TbItemChild> getOrderItem(List<Long> ids, HttpServletRequest request);

    /**
     * 创建订单
     * @param order
     * @param orderItems
     * @param orderShipping
     * @param request
     * @return
     */
    int createOrder(TbOrder order, List<TbOrderItem> orderItems, TbOrderShipping orderShipping, HttpServletRequest request, Model model);
}
