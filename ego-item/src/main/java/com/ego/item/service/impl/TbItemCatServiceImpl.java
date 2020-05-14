package com.ego.item.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.item.pojo.PortalMenu;
import com.ego.item.pojo.PortalMenuNode;
import com.ego.item.service.TbItemCatService;
import com.ego.pojo.TbItemCat;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: cty
 * @Date: 2020/5/14 16:44
 * @Description:
 * @version: 1.0
 */
@Service
public class TbItemCatServiceImpl implements TbItemCatService {
    @Reference
    private TbItemCatDubboService tbItemCatDubboServiceImpl;

    @Override
    public PortalMenu showCatMenu() {
        // 查询所有一级菜单
        List<TbItemCat> list = tbItemCatDubboServiceImpl.show(0);
        // 封装所有查询结果
        PortalMenu pm = new PortalMenu();
        pm.setData(selAllMenu(list));

        return pm;
    }

    private List<Object> selAllMenu(List<TbItemCat> list)
    {
        List<Object> listNode = new ArrayList<>();
        for(TbItemCat tbItemCat : list)
        {
            if(tbItemCat.getIsParent())
            {
                PortalMenuNode pmn = new PortalMenuNode();
                pmn.setU("/products/"+tbItemCat.getId()+".html");
                pmn.setN("<a href='/products/"+tbItemCat.getId()+".html'>"+tbItemCat.getName()+"</a>");
                pmn.setI(selAllMenu(tbItemCatDubboServiceImpl.show(tbItemCat.getId())));
                listNode.add(pmn);
            }
            else
            {
                listNode.add("/products/"+tbItemCat.getId()+".html|"+tbItemCat.getName());
            }
        }

        return listNode;
    }
}
