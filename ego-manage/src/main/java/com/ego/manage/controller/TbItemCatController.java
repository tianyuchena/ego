package com.ego.manage.controller;

import com.ego.commons.pojo.EasyUITree;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbItemCatService;
import com.ego.pojo.TbItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: cty
 * @Date: 2020/5/8 21:03
 * @Description:
 * @version: 1.0
 */
@Controller
public class TbItemCatController {
    @Resource
    private TbItemCatService tbItemCatServiceImpl;

    @RequestMapping("item/cat/list")
    @ResponseBody
    public List<EasyUITree> show(@RequestParam(defaultValue="0") long id)
    {
        return tbItemCatServiceImpl.show(id);
    }

}
