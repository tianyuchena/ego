package com.ego.dubbo.service.impl;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.mapper.TbItemDescMapper;
import com.ego.mapper.TbItemMapper;
import com.ego.mapper.TbItemParamItemMapper;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemExample;
import com.ego.pojo.TbItemParamItem;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: cty
 * @Date: 2020/5/5 21:34
 * @Description: TbItem相关服务具体实现（提供者）
 * @version: 1.0
 */
public class TbItemDubboServiceImpl implements TbItemDubboService {
    @Resource
    private TbItemMapper tbItemMapper;
    @Resource
    private TbItemDescMapper tbItemDescMapper;
    @Resource
    private TbItemParamItemMapper tbItemParamItemMapper;

    @Override
    public EasyUIDataGrid show(int page, int rows) {
        // 设置分页条件
        PageHelper.startPage(page, rows);  // page-显示第几页  rows-该页有多少条数据
        // 查询全部
        List<TbItem> list = tbItemMapper.selectByExample(new TbItemExample());
        // 获取分页信息
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

    @Override
    public int insTbItem(TbItem tbItem) {
        return tbItemMapper.insert(tbItem);
    }

    @Override
    public int insTbItemDesc(TbItem tbItem, TbItemDesc tbItemDesc, TbItemParamItem paramItem) throws Exception {

        int index = 0;

        try{
            index = tbItemMapper.insert(tbItem);
            index += tbItemDescMapper.insert(tbItemDesc);
            index += tbItemParamItemMapper.insertSelective(paramItem);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(index == 3)
            return 1;
        else
            throw new Exception("商品或描述插入数据库失败，数据回滚");
    }

    @Override
    public List<TbItem> selByStatus(byte status) {
        TbItemExample example = new TbItemExample();
        example.createCriteria().andStatusEqualTo(status);
        return tbItemMapper.selectByExample(example);
    }

}
