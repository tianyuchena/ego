package com.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.dubbo.service.TbItemParamItemDubboService;
import com.ego.manage.service.TbItemParamItemService;
import com.ego.pojo.TbItemParamItem;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: cty
 * @Date: 2020/5/26 21:10
 * @Description:
 * @version: 1.0
 */
@Service
public class TbItemParamItemServiceImpl implements TbItemParamItemService {
    @Reference
    private TbItemParamItemDubboService tbItemParamItemDubboService;


    @Override
    public TbItemParamItem showParamItem(long itemId) {
        List<TbItemParamItem> paramItems = tbItemParamItemDubboService.selByItemId(itemId);
        if(paramItems != null && paramItems.size()>0)  // 列表的非空判断
            return paramItems.get(0);
        return null;
    }
}
