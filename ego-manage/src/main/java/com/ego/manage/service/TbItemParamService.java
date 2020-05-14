package com.ego.manage.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbItemParam;

/**
 * @Auther: cty
 * @Date: 2020/5/12 11:47
 * @Description:
 * @version: 1.0
 */
public interface TbItemParamService {

    /**
     * 显示商品规格参数
     * @param page
     * @param rows
     * @return
     */
    EasyUIDataGrid showParam(int page, int rows);

    /**
     * 批量删除商品规格参数
     * @param ids
     * @return
     */
    int delete(String ids) throws Exception;

    /**
     * 是否需要新增商品参数规格
     * @param catId
     * @return
     */
    EgoResult isNeedInsert(long catId);

    /**
     * 给指定商品类目添加规格参数
     * @param param
     * @return
     */
    EgoResult insertParam(TbItemParam param);
}
