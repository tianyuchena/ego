package com.ego.dubbo.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;

import java.util.List;

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
     * 根据id修改商品内容
     * @param tbItem
     * @return
     */
    int updItem(TbItem tbItem);

    /**
     * 商品新增
     * @param tbItem
     * @return
     */
    int insTbItem(TbItem tbItem);

    /**
     * 新增商品、商品描述和规格参数
     * @param tbItem
     * @param tbItemDesc
     * @return
     */
    int insTbItemDesc(TbItem tbItem, TbItemDesc tbItemDesc, TbItemParamItem paramItem) throws Exception;

    /**
     * 修改商品、商品描述和规格参数
     * @param tbItem
     * @param tbItemDesc
     * @param paramItem
     * @return
     */
    int updTbItemDesc(TbItem tbItem, TbItemDesc tbItemDesc, TbItemParamItem paramItem) throws Exception;

    /**
     * 根据状态查询数据
     * @param status
     * @return
     */
    List<TbItem> selByStatus(byte status);

    /**
     * 根据id查询商品
     * @param id
     * @return
     */
    TbItem selById(long id);
}
