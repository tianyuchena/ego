package com.ego.manage.controller;

import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbItemParamItemService;
import com.ego.pojo.TbItemParamItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Auther: cty
 * @Date: 2020/5/26 21:12
 * @Description:
 * @version: 1.0
 */
@Controller
public class TbItemParamItemController {
    @Resource
    private TbItemParamItemService tbItemParamItemServiceImpl;


    /**
     * 根据商品id查询规格参数内容
     * @param itemId
     * @return
     */
    @RequestMapping("rest/item/param/item/query/{itemId}")
    @ResponseBody
    public EgoResult showParamItem(@PathVariable long itemId)
    {
        EgoResult er = new EgoResult();
        TbItemParamItem paramItem = tbItemParamItemServiceImpl.showParamItem(itemId);
        if(paramItem !=null)
        {
            er.setStatus(200);
            er.setData(paramItem);
        }
        return er;
    }

}
