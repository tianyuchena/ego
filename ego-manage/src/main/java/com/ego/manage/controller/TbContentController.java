package com.ego.manage.controller;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbContentService;
import com.ego.pojo.TbContent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Auther: cty
 * @Date: 2020/5/18 21:08
 * @Description:
 * @version: 1.0
 */
@Controller
public class TbContentController {
    @Resource
    private TbContentService tbContentServiceImpl;

    /**
     * 内容分页查询
     * @param categoryId
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("content/query/list")
    @ResponseBody
    public EasyUIDataGrid show(long categoryId, int page, int rows)
    {
        return tbContentServiceImpl.show(categoryId, page, rows);
    }

    /**
     * 内容新增
     * @param content
     * @return
     */
    @RequestMapping("content/save")
    @ResponseBody
    public EgoResult insert(TbContent content)
    {
        return tbContentServiceImpl.insert(content);
    }

    /**
     * 内容删除
     * @param ids
     * @return
     */
    @RequestMapping("content/delete")
    @ResponseBody
    public EgoResult delete(long ids)
    {
        return tbContentServiceImpl.delete(ids);
    }

    @RequestMapping("rest/content/edit")
    @ResponseBody
    public EgoResult update(TbContent content)
    {
        return tbContentServiceImpl.update(content);
    }

}
