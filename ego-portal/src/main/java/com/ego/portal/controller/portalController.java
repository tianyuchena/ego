package com.ego.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Auther: cty
 * @Date: 2020/5/14 15:07
 * @Description:
 * @version: 1.0
 */
@Controller
public class portalController {

    @RequestMapping("/")
    public String show()
    {
        return "index";
    }
}
