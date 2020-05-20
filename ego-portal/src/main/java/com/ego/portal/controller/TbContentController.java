package com.ego.portal.controller;

import com.ego.portal.service.TbContentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Auther: cty
 * @Date: 2020/5/20 19:26
 * @Description:
 * @version: 1.0
 */
@Controller
public class TbContentController {
    @Resource
    private TbContentService tbContentServiceImpl;

    @RequestMapping("showBigPic")
    public String showBigPic(Model model)
    {
        model.addAttribute("ad1", tbContentServiceImpl.showBigPic());
        return "index";
    }

}
