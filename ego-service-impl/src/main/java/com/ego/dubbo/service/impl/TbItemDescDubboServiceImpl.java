package com.ego.dubbo.service.impl;

import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.mapper.TbItemDescMapper;
import com.ego.pojo.TbItemDesc;

import javax.annotation.Resource;

/**
 * @Auther: cty
 * @Date: 2020/5/10 10:45
 * @Description:
 * @version: 1.0
 */
public class TbItemDescDubboServiceImpl implements TbItemDescDubboService {
    @Resource
    private TbItemDescMapper tbItemDescMapper;

    @Override
    public int insTbItemDesc(TbItemDesc tbItemDesc) {
        return tbItemDescMapper.insert(tbItemDesc);
    }

    @Override
    public TbItemDesc selById(long id) {
        return tbItemDescMapper.selectByPrimaryKey(id);
    }
}
