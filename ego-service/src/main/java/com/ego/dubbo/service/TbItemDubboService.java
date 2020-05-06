package com.ego.dubbo.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbItem;

/**
 * @Auther: cty
 * @Date: 2020/5/5 21:30
 * @Description: 服务接口
 * @version: 1.0
 */
public interface TbItemDubboService {
    /**
     * 商品分页查询
     * @param page
     * @param rows
     * @return
     */
    EasyUIDataGrid show(int page, int rows);

    /**
     * 根据id修改状态
     * @param tbItem
     * @return
     */
    int updItemStatus(TbItem tbItem);
}
