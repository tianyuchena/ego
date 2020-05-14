package com.ego.manage.controller;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbItemParamService;
import com.ego.pojo.TbItemParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Auther: cty
 * @Date: 2020/5/12 11:53
 * @Description:
 * @version: 1.0
 */
@Controller
public class TbItemParamController {
    @Resource
    private TbItemParamService tbItemParamServiceImpl;

    @RequestMapping("item/param/list")
    @ResponseBody
    public EasyUIDataGrid showParam(int page, int rows)
    {
        return tbItemParamServiceImpl.showParam(page, rows);
    }

    @RequestMapping("item/param/delete")
    @ResponseBody
    public EgoResult delete(String ids)
    {
        EgoResult er = new EgoResult();
        try {
            int index = tbItemParamServiceImpl.delete(ids);
            if(index == 1)
                er.setStatus(200);
        } catch (Exception e) {
            e.printStackTrace();
            er.setData(e.getMessage());
        }
        return er;
    }

    @RequestMapping("item/param/query/itemcatid/{catId}")
    @ResponseBody
    public EgoResult isNeedInsert(@PathVariable String catId)
    {
        return tbItemParamServiceImpl.isNeedInsert(Long.parseLong(catId));
    }

    @RequestMapping("item/param/save/{catId}")
    @ResponseBody
    public EgoResult save(TbItemParam param, @PathVariable long catId)
    {
        param.setItemCatId(catId);
        return tbItemParamServiceImpl.insertParam(param);
    }
}  // end class TbItemParamController
