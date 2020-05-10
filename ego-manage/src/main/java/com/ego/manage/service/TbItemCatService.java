package com.ego.manage.service;

import com.ego.commons.pojo.EasyUITree;

import java.util.List;

/**
 * @Auther: cty
 * @Date: 2020/5/8 20:53
 * @Description: TbItemCat服务层接口
 * @version: 1.0
 */
public interface TbItemCatService {
    /**
     * 根据父菜单id显示所有子菜单
     * @param pid
     * @return
     */
    List<EasyUITree> show(long pid);
}
