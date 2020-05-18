package com.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.manage.service.TbContentService;
import com.ego.pojo.TbContent;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Auther: cty
 * @Date: 2020/5/18 21:06
 * @Description:
 * @version: 1.0
 */
@Service
public class TbContentServiceImpl implements TbContentService {
    @Reference
    private TbContentDubboService tbContentDubboServiceImpl;

    @Override
    public EasyUIDataGrid show(long categoryId, int page, int rows) {
        return tbContentDubboServiceImpl.show(categoryId, page, rows);
    }

    @Override
    public EgoResult insert(TbContent content) {
        EgoResult er = new EgoResult();

        Date date = new Date();
        content.setCreated(date);
        content.setUpdated(date);
        int index = tbContentDubboServiceImpl.insert(content);
        if(index > 0)
        {
            er.setStatus(200);
        }
        return er;
    }

    @Override
    public EgoResult delete(long ids) {
        EgoResult er = new EgoResult();

        int index = tbContentDubboServiceImpl.delete(ids);
        if(index > 0)
        {
            er.setStatus(200);
        }

        return er;
    }

    @Override
    public EgoResult update(TbContent content) {
        EgoResult er = new EgoResult();

        Date date = new Date();
        content.setUpdated(date);
        int index = tbContentDubboServiceImpl.update(content);
        if(index > 0)
        {
            er.setStatus(200);
        }
        return er;
    }
}
