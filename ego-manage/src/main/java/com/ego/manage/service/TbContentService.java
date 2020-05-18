package com.ego.manage.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbContent;

/**
 * @Auther: cty
 * @Date: 2020/5/18 21:04
 * @Description:
 * @version: 1.0
 */
public interface TbContentService {

    /**
     * 内容分页查询
     * @param categoryId
     * @param page
     * @param rows
     * @return
     */
    EasyUIDataGrid show(long categoryId, int page, int rows);

    /**
     * 内容新增
     * @param content
     * @return
     */
    EgoResult insert(TbContent content);

    /**
     * 内容删除
     * @param ids
     * @return
     */
    EgoResult delete(long ids);

    /**
     * 内容修改
     * @param content
     * @return
     */
    EgoResult update(TbContent content);
}
