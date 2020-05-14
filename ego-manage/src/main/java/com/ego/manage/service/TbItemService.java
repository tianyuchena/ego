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
    int update(String ids, byte status);

    /**
     * 新增商品和商品描述
     * @param tbItem
     * @param desc
     * @return
     */
    int insTbItemDesc(TbItem tbItem, String desc, String itemParams) throws Exception;

}
