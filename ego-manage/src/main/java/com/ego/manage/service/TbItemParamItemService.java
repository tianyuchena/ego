package com.ego.manage.service;

import com.ego.pojo.TbItemParamItem;

/**
 * @Auther: cty
 * @Date: 2020/5/26 21:08
 * @Description:
 * @version: 1.0
 */
public interface TbItemParamItemService {

    /**
     * 通过商品id查询规格参数内容
     * @param itemId
     * @return
     */
    TbItemParamItem showParamItem(long itemId);
}
