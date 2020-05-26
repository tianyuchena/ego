package com.ego.manage.controller;

import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbItemDescService;
import com.ego.pojo.TbItemDesc;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Auther: cty
 * @Date: 2020/5/26 18:48
 * @Description:
 * @version: 1.0
 */
@Controller
public class TbItemDescController {
    @Resource
    private TbItemDescService tbItemDescServiceImpl;

    @RequestMapping("rest/item/query/item/desc/{id}")
    @ResponseBody
    public EgoResult showDesc(@PathVariable long id)
    {
        EgoResult er = new EgoResult();
        TbItemDesc itemDesc = tbItemDescServiceImpl.showDesc(id);
        if(itemDesc != null)
        {
            er.setStatus(200);
            er.setData(itemDesc);
        }
        return er;
    }
}
