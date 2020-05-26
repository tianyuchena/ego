package com.ego.item.controller;

import com.ego.item.service.TbItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @Auther: cty
 * @Date: 2020/5/26 16:51
 * @Description:
 * @version: 1.0
 */
@Controller
public class TbItemController {

    @Resource
    private TbItemService tbItemServiceImpl;


    /**
     * 显示商品详情
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("item/{id}.html")
    public String showItem(Model model, @PathVariable long id)
    {
        model.addAttribute("item", tbItemServiceImpl.showItem(id));
        return "item";
    }
}
