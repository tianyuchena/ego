package com.ego.dubbo.service;

import com.ego.pojo.TbItemParamItem;

import java.util.List;

/**
 * @Auther: cty
 * @Date: 2020/5/26 21:01
 * @Description:
 * @version: 1.0
 */
public interface TbItemParamItemDubboService {

    /**
     * 通过商品id查询规格参数
     * @param itemId
     * @return
     */
    List<TbItemParamItem> selByItemId(long itemId);
}
