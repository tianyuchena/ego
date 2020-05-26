package com.ego.dubbo.service;

import com.ego.pojo.TbItemDesc;

/**
 * @Auther: cty
 * @Date: 2020/5/10 10:39
 * @Description:
 * @version: 1.0
 */
public interface TbItemDescDubboService {

    /**
     * 新增商品描述
     * @param tbItemDesc
     * @return
     */
    int insTbItemDesc(TbItemDesc tbItemDesc);

    /**
     * 根据id查询商品描述
     * @param id
     * @return
     */
    TbItemDesc selById(long id);

}
