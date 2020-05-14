package com.ego.dubbo.service.impl;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.dubbo.service.TbItemParamDubboService;
import com.ego.mapper.TbItemParamMapper;
import com.ego.pojo.TbItemParam;
import com.ego.pojo.TbItemParamExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: cty
 * @Date: 2020/5/12 11:34
 * @Description:
 * @version: 1.0
 */
public class TbItemParamDubboServiceImpl implements TbItemParamDubboService {
    @Resource
    private TbItemParamMapper tbItemParamMapper;

    @Override
    public EasyUIDataGrid showParam(int page, int rows) {
        // 设置分页条件
        PageHelper.startPage(page, rows);
        // 查询全部
        /** 重点：
         * 如果表中有一个或一个以上的列是text类型，要使用xxxWithBlobs的方法
         * 否则，方法会忽略text类型的列
         */
        List<TbItemParam> params = tbItemParamMapper.selectByExampleWithBLOBs(new TbItemParamExample());
        // 获取分页信息
        PageInfo<TbItemParam> pi = new PageInfo<>(params);
        // 将分页信息放到实体类
        EasyUIDataGrid dataGrid = new EasyUIDataGrid();
        dataGrid.setRows(pi.getList());
        dataGrid.setTotal(pi.getTotal());

        return dataGrid;
    }

    @Override
    public int delete(String ids) throws Exception {
        String[] idStrs = ids.split(",");
        int index = 0;

        for(String idStr : idStrs)
            index += tbItemParamMapper.deleteByPrimaryKey(Long.parseLong(idStr));

        if(index == idStrs.length)
            return 1;
        else
            throw new Exception("<br/>原因：可能数据已经不存在");
    }

    @Override
    public TbItemParam selByCatId(long catId) {
        TbItemParamExample example = new TbItemParamExample();
        example.createCriteria().andItemCatIdEqualTo(catId);
        List<TbItemParam> params = tbItemParamMapper.selectByExampleWithBLOBs(example);

        if(null != params && params.size()>0)
            return params.get(0);
        return null;
    }

    @Override
    public int insParam(TbItemParam param) {
        return tbItemParamMapper.insertSelective(param);
    }

}
