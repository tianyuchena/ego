package com.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.manage.service.TbItemService;
import com.ego.pojo.TbItem;

/**
 * @Auther: cty
 * @Date: 2020/5/5 21:59
 * @Description:
 * @version: 1.0
 */
@Service
public class TbItemServiceImpl implements TbItemService {
    @Reference
    private TbItemDubboService tbItemDubboServiceImpl;

    @Override
    public EasyUIDataGrid show(int page, int rows) {
        return tbItemDubboServiceImpl.show(page, rows);
    }

    @Override
    public int update(String ids, byte status) {
        int index = 0;
        TbItem item = new TbItem();
        String[] idsStr = ids.split(",");
        for(String id : idsStr){
            item.setId(Long.parseLong(id));
            item.setStatus(status);
            index += tbItemDubboServiceImpl.updItemStatus(item);
        }
        if(index == idsStr.length)
            return 1;
        return 0;
    }
}
