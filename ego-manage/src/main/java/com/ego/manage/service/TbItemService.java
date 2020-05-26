package com.ego.manage.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;

/**
 * @Auther: cty
 * @Date: 2020/5/5 21:58
 * @Description:
 * @version: 1.0
 */
public interface TbItemService {
    /**
     * 显示商品
     * @param page
     * @param rows
     * @return
     */
    EasyUIDataGrid show(int page, int rows);

    /**
     * 批量修改商品状态
     * @param ids
     * @param status
     * @return
     */
    int updItemStatus(String ids, byte status);

    /**
     * 新增商品内容、商品描述和规格参数
     * @param tbItem
     * @param desc
     * @param itemParams
     * @return
     * @throws Exception
     */
    int insTbItemDesc(TbItem tbItem, String desc, String itemParams) throws Exception;

    /**
     * 修改商品内容、商品描述和规格参数
     * @param tbItem
     * @param desc
     * @param itemParamId
     * @param itemParams
     * @return
     * @throws Exception
     */
    int updItemDesc(TbItem tbItem, String desc, String itemParamId, String itemParams) throws Exception;

}
