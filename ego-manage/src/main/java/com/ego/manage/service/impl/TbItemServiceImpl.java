package com.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.utils.IDUtils;
import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.manage.service.TbItemService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;

import javax.annotation.Resource;
import java.util.Date;

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
//    @Reference
//    private TbItemDescDubboService tbItemDescDubboServiceImpl;

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

    @Override
    public int insTbItemDesc(TbItem item, String desc) throws Exception {
        // 事务管理
        long id = IDUtils.genItemId();
        item.setId(id);
        Date date = new Date();
        item.setCreated(date);
        item.setUpdated(date);
        item.setStatus((byte)1);

        TbItemDesc itemDesc = new TbItemDesc();
//        itemDesc.setItemId(id);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(date);
        itemDesc.setUpdated(date);

        int index = 0;
        index = tbItemDubboServiceImpl.insTbItemDesc(item, itemDesc);
        return index;

        // 不考虑事务管理
/*        long id = IDUtils.genItemId();
        item.setId(id);
        Date date = new Date();
        item.setCreated(date);
        item.setUpdated(date);
        item.setStatus((byte)1);
        int index = tbItemDubboServiceImpl.insTbItem(item);

        if(index > 0)
        {
            TbItemDesc itemDesc = new TbItemDesc();
            itemDesc.setItemId(id);
            itemDesc.setItemDesc(desc);
            itemDesc.setCreated(date);
            itemDesc.setUpdated(date);
            index += tbItemDescDubboServiceImpl.insTbItemDesc(itemDesc);
        }

        if(index == 2)
            return 1;
        return 0;*/
    }
}
