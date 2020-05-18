package com.ego.dubbo.service.impl;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.mapper.TbContentMapper;
import com.ego.pojo.TbContent;
import com.ego.pojo.TbContentExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: cty
 * @Date: 2020/5/18 20:52
 * @Description:
 * @version: 1.0
 */
public class TbContentDubboServiceImpl implements TbContentDubboService {
    @Resource
    private TbContentMapper tbContentMapper;

    @Override
    public EasyUIDataGrid show(long categoryId, int page, int rows) {
        // 设置分页条件
        PageHelper.startPage(page, rows);
        // 查询
        TbContentExample example = new TbContentExample();
        if(categoryId != 0)  // 等于0则不设置，默认查询全部
        {
            example.createCriteria().andCategoryIdEqualTo(categoryId);
        }
        List<TbContent> contents = tbContentMapper.selectByExampleWithBLOBs(example);

        // 配置分页数据
        PageInfo<TbContent> pi = new PageInfo<>(contents);

        // 封装分页数据
        EasyUIDataGrid dataGrid = new EasyUIDataGrid();
        dataGrid.setRows(pi.getList());
        dataGrid.setTotal(pi.getTotal());
        return dataGrid;
    }

    @Override
    public int insert(TbContent content) {
        return tbContentMapper.insert(content);
    }

    @Override
    public int delete(long ids) {
        return tbContentMapper.deleteByPrimaryKey(ids);
    }

    @Override
    public int update(TbContent content) {
        return tbContentMapper.updateByPrimaryKeySelective(content);
    }
}
