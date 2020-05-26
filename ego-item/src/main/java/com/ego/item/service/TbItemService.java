package com.ego.item.service;

import com.ego.commons.pojo.TbItemChild;

/**
 * @Auther: cty
 * @Date: 2020/5/26 9:41
 * @Description:
 * @version: 1.0
 */
public interface TbItemService {

    /**
     * 根据id查询商品
     * @param id
     * @return
     */
    TbItemChild showItem(long id);
}
