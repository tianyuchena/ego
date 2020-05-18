package com.ego.manage.controller;

import com.ego.commons.pojo.EasyUITree;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbContentCategoryService;
import com.ego.pojo.TbContentCategory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: cty
 * @Date: 2020/5/18 11:50
 * @Description:
 * @version: 1.0
 */
@Controller
public class TbContentCategoryController {
    @Resource
    private TbContentCategoryService tbContentCategoryServiceImpl;

    /**
     * 查询内容类目
     * @param id
     * @return
     */
    @RequestMapping("content/category/list")
    @ResponseBody
    public List<EasyUITree> show(@RequestParam(defaultValue = "0") long id)
    {
        return tbContentCategoryServiceImpl.show(id);
    }

    /**
     * 创建内容类目
     * @param cate
     * @return
     */
    @RequestMapping("content/category/create")
    @ResponseBody
    public EgoResult create(TbContentCategory cate)
    {
        return tbContentCategoryServiceImpl.create(cate);
    }

    /**
     * 更新内容类目
     * @param cate
     * @return
     */
    @RequestMapping("content/category/update")
    @ResponseBody
    public EgoResult update(TbContentCategory cate)
    {
        return tbContentCategoryServiceImpl.update(cate);
    }

    /**
     * 删除内容类目
     * @param cate
     * @return
     */
    @RequestMapping("content/category/delete")
    @ResponseBody
    public EgoResult delete(TbContentCategory cate)
    {
        return tbContentCategoryServiceImpl.delete(cate);
    }
}
