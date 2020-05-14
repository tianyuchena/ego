package com.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.dubbo.service.TbItemParamDubboService;
import com.ego.manage.pojo.TbItemParamChild;
import com.ego.manage.service.TbItemParamService;
import com.ego.pojo.TbItemParam;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: cty
 * @Date: 2020/5/12 11:49
 * @Description:
 * @version: 1.0
 */
@Service
public class TbItemParamServiceImpl implements TbItemParamService {
    @Reference
    private TbItemParamDubboService tbItemParamDubboServiceImpl;
    @Reference
    private TbItemCatDubboService tbItemCatDubboServiceImpl;

    @Override
    public EasyUIDataGrid showParam(int page, int rows) {
        EasyUIDataGrid dataGrid = tbItemParamDubboServiceImpl.showParam(page, rows);
        List<TbItemParam> params = (List<TbItemParam>) dataGrid.getRows();
        List<TbItemParamChild> paramChildren  = new ArrayList<>();

        for(TbItemParam param : params)
        {
            TbItemParamChild paramChild = new TbItemParamChild();
            paramChild.setId(param.getId());
            paramChild.setItemCatId(param.getItemCatId());
            paramChild.setItemCatName(tbItemCatDubboServiceImpl.selById(param.getItemCatId()).getName());
            paramChild.setParamData(param.getParamData());
            paramChild.setCreated(param.getCreated());
            paramChild.setUpdated(param.getUpdated());
            paramChildren.add(paramChild);
        }

        dataGrid.setRows(paramChildren);
        return dataGrid;
    }

    @Override
    public int delete(String ids) throws Exception {
        return tbItemParamDubboServiceImpl.delete(ids);
    }

    @Override
    public EgoResult isNeedInsert(long catId) {
        EgoResult er = new EgoResult();
        TbItemParam param = tbItemParamDubboServiceImpl.selByCatId(catId);
        if(null != param)
        {
            er.setStatus(200);
            er.setData(param);
        }
        return er;
    }

    @Override
    public EgoResult insertParam(TbItemParam param) {
        EgoResult er = new EgoResult();
        int index = tbItemParamDubboServiceImpl.insParam(param);
        if(index == 1)
            er.setStatus(200);
        return er;
    }


}
