package com.ego.item.controller;

import com.ego.item.service.TbItemParamItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Auther: cty
 * @Date: 2020/5/26 22:37
 * @Description:
 * @version: 1.0
 */
@Controller
public class TbItemParamItemController {
    @Resource
    private TbItemParamItemService tbItemParamItemServiceImpl;

    @RequestMapping(value = "item/param/{itemId}.html", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String showParamItem(@PathVariable long itemId)
    {
        return tbItemParamItemServiceImpl.showParamItem(itemId);
    }

}
