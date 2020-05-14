package com.ego.dubbo.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbItemParam;

import java.util.List;

/**
 * @Auther: cty
 * @Date: 2020/5/12 11:31
 * @Description:
 * @version: 1.0
 */
public interface TbItemParamDubboService {

    /**
     * 显示参数规格
     * @param page 显示第几页
     * @param rows 该页有多少条数据
     * @return
     */
    EasyUIDataGrid showParam(int page, int rows);

    /**
     * 批量删除参数规格
     * @param ids
     * @return
     */
    int delete(String ids) throws Exception;

    /**
     * 根据类目id查询
     * @param catId
     * @return
     */
    TbItemParam selByCatId(long catId);

    /**
     * 新增商品规格参数
     * @return
     */
    int insParam(TbItemParam param);

}
