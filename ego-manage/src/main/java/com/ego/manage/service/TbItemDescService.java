package com.ego.manage.service;

import com.ego.pojo.TbItemDesc;

/**
 * @Auther: cty
 * @Date: 2020/5/26 18:44
 * @Description:
 * @version: 1.0
 */
public interface TbItemDescService {

    /**
     * 根据id查询商品描述
     * @param id
     * @return
     */
    TbItemDesc showDesc(long id);
}
