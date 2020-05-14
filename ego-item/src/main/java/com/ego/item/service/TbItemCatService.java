package com.ego.item.service;

import com.ego.item.pojo.PortalMenu;

/**
 * @Auther: cty
 * @Date: 2020/5/14 16:44
 * @Description:
 * @version: 1.0
 */
public interface TbItemCatService {
    /**
     * 查询出所有商品分类类目并转换为特定类型
     * @return
     */
    PortalMenu showCatMenu();
}
