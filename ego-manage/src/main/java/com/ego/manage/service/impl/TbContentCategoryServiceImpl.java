package com.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUITree;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.IDUtils;
import com.ego.dubbo.service.TbContentCategoryDubboService;
import com.ego.manage.service.TbContentCategoryService;
import com.ego.pojo.TbContentCategory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Auther: cty
 * @Date: 2020/5/18 11:43
 * @Description:
 * @version: 1.0
 */
@Service
public class TbContentCategoryServiceImpl implements TbContentCategoryService {
    @Reference
    private TbContentCategoryDubboService tbContentCategoryDubboServiceImpl;

    @Override
    public List<EasyUITree> show(long pid) {
        List<EasyUITree> treeList = new ArrayList<>();
        List<TbContentCategory> tbContentCategoryList = tbContentCategoryDubboServiceImpl.selByPid(pid);

        for(TbContentCategory category : tbContentCategoryList)
        {
            EasyUITree tree = new EasyUITree();
            tree.setId(category.getId());
            tree.setText(category.getName());
            tree.setState(category.getIsParent()?"closed":"open");
            treeList.add(tree);
        }

        return treeList;
    }

    @Override
    public EgoResult create(TbContentCategory cate) {
        EgoResult er = new EgoResult();

        // 判断待新建节点的名称是否已经存在
        List<TbContentCategory> children =  tbContentCategoryDubboServiceImpl.selByPid(cate.getParentId());
        for(TbContentCategory child : children)
        {
            if(child.getName().equals(cate.getName()))
            {
                er.setData("该分类名已经存在");
                return er;
            }
        }

        long id = IDUtils.genItemId();
        Date date = new Date();

        cate.setId(id);
        cate.setStatus(1);
        cate.setSortOrder(1);
        cate.setIsParent(false);
        cate.setCreated(date);
        cate.setUpdated(date);

        int index = tbContentCategoryDubboServiceImpl.insTbContentCategory(cate);
        if(index > 0)  // 若创建成功，将父类目的isParent属性标记为1
        {
            TbContentCategory parent = new TbContentCategory();
            parent.setId(cate.getParentId());
            parent.setIsParent(true);
            tbContentCategoryDubboServiceImpl.updById(parent);
        }

        er.setStatus(200);
        Map<String, Long> map = new HashMap<>();
        map.put("id", id);
        er.setData(map);
        return er;
    }

    @Override
    public EgoResult update(TbContentCategory cate) {
        EgoResult er = new EgoResult();

        // 判断待修改的名称与父类目下的子类目是否重名
        TbContentCategory cateSelect = tbContentCategoryDubboServiceImpl.selById(cate.getId());
        List<TbContentCategory> children =  tbContentCategoryDubboServiceImpl.selByPid(cateSelect.getParentId());
        for(TbContentCategory child : children)
        {
            if(child.getName().equals(cate.getName()))
            {
                er.setData("该分类名已经存在");
                return er;
            }
        }

        Date date = new Date();
        cate.setUpdated(date);
        int index = tbContentCategoryDubboServiceImpl.updById(cate);
        if(index > 0)
            er.setStatus(200);

        return er;
    }

    @Override
    public EgoResult delete(TbContentCategory cate) {
        EgoResult er = new EgoResult();

        cate.setStatus(0);
        int index = tbContentCategoryDubboServiceImpl.updById(cate);
        if(index > 0)
        {
            TbContentCategory cur = tbContentCategoryDubboServiceImpl.selById(cate.getId());
            List<TbContentCategory> children = tbContentCategoryDubboServiceImpl.selByPid(cur.getParentId());
            if(children==null || children.size()==0)
            {
                TbContentCategory parent = new TbContentCategory();
                parent.setId(cur.getParentId());
                parent.setIsParent(false);
                int result = tbContentCategoryDubboServiceImpl.updById(parent);
                if(result > 0)
                    er.setStatus(200);
            }
            else
            {
                er.setStatus(200);
            }
        }

        return er;
    }
}
