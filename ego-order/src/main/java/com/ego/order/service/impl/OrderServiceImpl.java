package com.ego.order.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.TbItemChild;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.IDUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.dubbo.service.TbOrderDubboService;
import com.ego.order.service.OrderService;
import com.ego.pojo.*;
import com.ego.redis.dao.JedisDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Auther: cty
 * @Date: 2020/6/1 11:57
 * @Description:
 * @version: 1.0
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Reference
    private TbItemDubboService tbItemDubboServiceImpl;
    @Resource
    private JedisDao jedisDaoImpl;
    @Value("${cart.key}")
    private String cartKey;
    @Reference
    private TbOrderDubboService tbOrderDubboServiceImpl;

    @Override
    public List<TbItemChild> getOrderItem(List<Long> ids, HttpServletRequest request) {
        List<TbItemChild> cartList = new ArrayList<>();

        String userInfoKey = CookieUtils.getCookieValue(request, "TT_TOKEN");
        String userInfoJson = jedisDaoImpl.get(userInfoKey);
        TbUser userInfoPojo = JsonUtils.jsonToPojo(userInfoJson, TbUser.class);
        String cartInfoKey = cartKey + userInfoPojo.getUsername();

        String cartInfoJson = jedisDaoImpl.get(cartInfoKey);
        if(cartInfoJson!=null && !cartInfoJson.equals(""))
        {
            List<TbItemChild> cartInfoList = JsonUtils.jsonToList(cartInfoJson, TbItemChild.class);
            for(TbItemChild child: cartInfoList) {
                for (Long id : ids) {
                    if ((long)id == (long)child.getId())  // Long是对象会比较地址，long是基本数据类型会比较值！
                    {
                        // 判断购买量是否大于库存量
                        TbItem item = tbItemDubboServiceImpl.selById(id);
                        if(item.getNum() < child.getNum())
                            child.setEnough(false);
                        else
                            child.setEnough(true);
                        cartList.add(child);
                    }
                }
            }
        }

        if(cartList.get(0)==null)
            return null;
        return cartList;
    }

    @Override
    public int createOrder(TbOrder order, List<TbOrderItem> orderItems, TbOrderShipping orderShipping, HttpServletRequest request, Model model) {
        int index = 0;
        long orderId = IDUtils.genItemId();
        Date date = new Date();

        // 准备order数据
        order.setOrderId(orderId+"");
        order.setCreateTime(date);
        order.setUpdateTime(date);

        String userInfoKey = CookieUtils.getCookieValue(request, "TT_TOKEN");
        String userInfoJson = jedisDaoImpl.get(userInfoKey);
        TbUser userInfoPojo = JsonUtils.jsonToPojo(userInfoJson, TbUser.class);

        order.setUserId(userInfoPojo.getId());
        order.setBuyerNick(userInfoPojo.getUsername());

        // 准备orderItems数据
        for(TbOrderItem orderItem: orderItems)
        {
            orderItem.setId(IDUtils.genItemId()+"");
            orderItem.setOrderId(orderId+"");
        }

        // 准备orderShipping数据
        orderShipping.setOrderId(orderId+"");
        orderShipping.setCreated(date);
        orderShipping.setUpdated(date);

        // 数据库新增数据
        try {
            index = tbOrderDubboServiceImpl.insOrder(order, orderItems, orderShipping);
            if(index == 1)
            {  // 准备model数据
                model.addAttribute("orderId", orderId+"");
                model.addAttribute("payment", order.getPayment());
                model.addAttribute("date", date);
            }

            // 删除购物车中商品缓存
            String cartInfoKey = cartKey + userInfoPojo.getUsername();
            String cartInfoJson = jedisDaoImpl.get(cartInfoKey);
            List<TbItemChild> cartInfoList = JsonUtils.jsonToList(cartInfoJson, TbItemChild.class);
            List<TbItemChild> cartOrderList = new ArrayList<>();
            for(TbItemChild child: cartInfoList)
                for(TbOrderItem orderItem: orderItems)
                    if(child.getId() == Long.parseLong(orderItem.getItemId()))
                        cartOrderList.add(child);

            for(TbItemChild child: cartOrderList)
                cartInfoList.remove(child);

            jedisDaoImpl.set(cartInfoKey, JsonUtils.objectToJson(cartInfoList));

            // 更新数据库中商品库存数量
            int indexOfUpdItemNum = 0;
            for(TbOrderItem orderItem: orderItems)
            {
                long itemId = Long.parseLong(orderItem.getItemId());
                TbItem item = tbItemDubboServiceImpl.selById(itemId);
                item.setNum(item.getNum() - orderItem.getNum());
                indexOfUpdItemNum +=tbItemDubboServiceImpl.updItem(item);
            }
            if(indexOfUpdItemNum == orderItems.size())
                System.out.println("更新数据库中商品库存数量成功");
            else
                System.out.println("更新数据库中商品库存数量失败");

            return index;
        } catch (Exception e) {
            model.addAttribute("message", "订单创建失败");
            return 0;
        }
    }


}
