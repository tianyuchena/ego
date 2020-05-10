package com.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.ego.commons.pojo.EasyUITree;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.manage.service.TbItemCatService;
import com.ego.pojo.TbItemCat;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: cty
 * @Date: 2020/5/8 20:55
 * @Description: TbItemCat服务层实现类
 * @version: 1.0
 */
@Service
public class TbItemCatServiceImpl implements TbItemCatService {
    @Reference
    private TbItemCatDubboService tbItemCatDubboServiceImpl;

    @Override
    public List<EasyUITree> show(long pid) {
        List<TbItemCat> list = tbItemCatDubboServiceImpl.show(pid);
        List<EasyUITree> listTree = new ArrayList<>();
        for(TbItemCat cat : list)
        {
            EasyUITree tree = new EasyUITree();
            tree.setId(cat.getId());
            tree.setText(cat.getName());
            tree.setState(cat.getIsParent()?"closed":"open");
            listTree.add(tree);
        }
        return listTree;
    }
}
