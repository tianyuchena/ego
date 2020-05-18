package com.ego.manage.service;

import com.ego.commons.pojo.EasyUITree;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbContentCategory;

import java.util.List;

/**
 * @Auther: cty
 * @Date: 2020/5/18 11:41
 * @Description:
 * @version: 1.0
 */
public interface TbContentCategoryService {
    List<EasyUITree> show(long pid);

    EgoResult create(TbContentCategory cate);

    EgoResult update(TbContentCategory cate);

    EgoResult delete(TbContentCategory cate);
}
