package com.ego.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auther: cty
 * @Date: 2020/5/4 22:17
 * @Description:
 * @version: 1.0
 */
@Controller
public class PageController
{
    @RequestMapping("/")
    public String welcome()
    {
        return "index";
    }

    @RequestMapping("{page}")
    public String showPage(String page)
    {
        return page;
    }
}
