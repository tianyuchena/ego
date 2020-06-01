package com.ego.dubbo.service.impl;

import com.ego.dubbo.service.TbOrderDubboService;
import com.ego.mapper.TbOrderItemMapper;
import com.ego.mapper.TbOrderMapper;
import com.ego.mapper.TbOrderShippingMapper;
import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: cty
 * @Date: 2020/6/1 20:01
 * @Description:
 * @version: 1.0
 */
public class TbOrderDubboServiceImpl implements TbOrderDubboService {
    @Resource
    private TbOrderMapper tbOrderMapper;
    @Resource
    private TbOrderItemMapper tbOrderItemMapper;
    @Resource
    private TbOrderShippingMapper tbOrderShippingMapper;

    @Override
    public int insOrder(TbOrder order, List<TbOrderItem> orderItems, TbOrderShipping orderShipping) throws Exception {
        int index = tbOrderMapper.insertSelective(order);
        for(TbOrderItem orderItem: orderItems)
        {
            index += tbOrderItemMapper.insertSelective(orderItem);
        }
        index += tbOrderShippingMapper.insertSelective(orderShipping);

        if(index == (2 + orderItems.size()))
            return 1;
        else
            throw new Exception("订单创建失败");
    }
}
