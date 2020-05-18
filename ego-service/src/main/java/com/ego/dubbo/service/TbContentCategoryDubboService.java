package com.ego.dubbo.service;

import com.ego.pojo.TbContentCategory;

import java.util.List;

/**
 * @Auther: cty
 * @Date: 2020/5/18 11:22
 * @Description:
 * @version: 1.0
 */
public interface TbContentCategoryDubboService {
    /**
     * 根据父类目id查询所有子类目
     * @param id
     * @return
     */
    List<TbContentCategory> selByPid(long id);

    /**
     * 新增
     * @param cate
     * @return
     */
    int insTbContentCategory(TbContentCategory cate);

    /**
     * 通过id修改信息
     * @param cate
     * @return
     */
    int updById(TbContentCategory cate);

    /**
     * 根据id查询类目全部信息
     * @param id
     * @return
     */
    TbContentCategory selById(long id);
}
