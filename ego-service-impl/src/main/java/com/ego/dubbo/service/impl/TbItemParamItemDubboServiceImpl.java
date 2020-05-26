package com.ego.dubbo.service.impl;

import com.ego.dubbo.service.TbItemParamItemDubboService;
import com.ego.mapper.TbItemParamItemMapper;
import com.ego.pojo.TbItemParamItem;
import com.ego.pojo.TbItemParamItemExample;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: cty
 * @Date: 2020/5/26 21:03
 * @Description:
 * @version: 1.0
 */
public class TbItemParamItemDubboServiceImpl implements TbItemParamItemDubboService {
    @Resource
    private TbItemParamItemMapper tbItemParamItemMapper;

    @Override
    public List<TbItemParamItem> selByItemId(long itemId) {
        TbItemParamItemExample example = new TbItemParamItemExample();
        example.createCriteria().andItemIdEqualTo(itemId);
        return tbItemParamItemMapper.selectByExampleWithBLOBs(example);
    }
}
