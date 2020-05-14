package com.ego.dubbo.service;

import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemCat;

import java.util.List;

/**
 * @Auther: cty
 * @Date: 2020/5/8 20:26
 * @Description: TbItemCat服务接口
 * @version: 1.0
 */
public interface TbItemCatDubboService {

    /**
     * 根据父菜单id显示所有子菜单
     * @param pid
     * @return
     */
    List<TbItemCat> show(long pid);

    /**
     * 根据id查询商品
     * @param id
     * @return
     */
    TbItemCat selById(long id);
}
