package com.ego.dubbo.service;

import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;

import java.util.List;

/**
 * @Auther: cty
 * @Date: 2020/6/1 18:11
 * @Description:
 * @version: 1.0
 */
public interface TbOrderDubboService {

    int insOrder(TbOrder order, List<TbOrderItem> orderItems, TbOrderShipping orderShipping) throws Exception;
}
