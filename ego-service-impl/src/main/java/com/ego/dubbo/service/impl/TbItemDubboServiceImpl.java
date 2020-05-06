package com.ego.dubbo.service.impl;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.mapper.TbItemMapper;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: cty
 * @Date: 2020/5/5 21:34
 * @Description: 服务具体实现（提供者）
 * @version: 1.0
 */
public class TbItemDubboServiceImpl implements TbItemDubboService {
    @Resource
    private TbItemMapper tbItemMapper;

    @Override
    public EasyUIDataGrid show(int page, int rows) {
        // 设置分页条件
        PageHelper.startPage(page, rows);
        // 查询全部
        List<TbItem> list = tbItemMapper.selectByExample(new TbItemExample());
        // 获取分页信息类
        PageInfo<TbItem> pi = new PageInfo<>(list);
        // 将分页信息放到实体类
        EasyUIDataGrid dataGrid = new EasyUIDataGrid();
        dataGrid.setRows(pi.getList());
        dataGrid.setTotal(pi.getTotal());

        return dataGrid;
    }

    @Override
    public int updItemStatus(TbItem tbItem) {
        return tbItemMapper.updateByPrimaryKeySelective(tbItem);
    }
}
