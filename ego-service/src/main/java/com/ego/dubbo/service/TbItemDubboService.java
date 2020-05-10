package com.ego.dubbo.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;

/**
 * @Auther: cty
 * @Date: 2020/5/5 21:30
 * @Description: TbItem服务接口
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

    /**
     * 商品新增
     * @param tbItem
     * @return
     */
    int insTbItem(TbItem tbItem);

    /**
     * 新增商品和商品描述
     * @param tbItem
     * @param tbItemDesc
     * @return
     */
    int insTbItemDesc(TbItem tbItem, TbItemDesc tbItemDesc) throws Exception;
}
