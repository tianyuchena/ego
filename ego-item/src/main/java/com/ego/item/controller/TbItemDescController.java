package com.ego.item.controller;

import com.ego.item.service.TbItemDescService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Auther: cty
 * @Date: 2020/5/26 16:52
 * @Description:
 * @version: 1.0
 */
@Controller
public class TbItemDescController {
    @Resource
    private TbItemDescService tbItemDescServiceImpl;

    @RequestMapping(value = "item/desc/{id}.html", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String showDesc(@PathVariable long id)
    {
        return tbItemDescServiceImpl.showDesc(id);
    }

}
