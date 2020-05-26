package com.ego.manage.controller;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbItemService;
import com.ego.pojo.TbItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Auther: cty
 * @Date: 2020/5/5 22:02
 * @Description:
 * @version: 1.0
 */
@Controller
public class TbItemController {
    @Resource
    private TbItemService tbItemServiceImpl;

    /**
     * 商品分页显示
     * @param page  显示第几页
     * @param rows  该页有多少条数据
     * @return
     */
    @RequestMapping("item/list")
    @ResponseBody
    public EasyUIDataGrid show(int page, int rows){
        return tbItemServiceImpl.show(page, rows);
    }

    /**
     * 显示商品修改页面
     * @return
     */
    @RequestMapping("rest/page/item-edit")
    public String showItemEdit(){
        return "item-edit";
    }

    /**
     * 批量删除商品
     * @param ids
     * @return
     */
    @RequestMapping("rest/item/delete")
    @ResponseBody
    public EgoResult delete(String ids){
        EgoResult er = new EgoResult();
        int index = tbItemServiceImpl.updItemStatus(ids, (byte)3);
        if(1 == index)
            er.setStatus(200);
        return er;
    }

    /**
     * 批量下架商品
     * @param ids
     * @return
     */
    @RequestMapping("rest/item/instock")
    @ResponseBody
    public EgoResult instock(String ids){
        EgoResult er = new EgoResult();
        int index = tbItemServiceImpl.updItemStatus(ids, (byte)2);
        if(1 == index)
            er.setStatus(200);
        return er;
    }

    /**
     * 批量上架商品
     * @param ids
     * @return
     */
    @RequestMapping("rest/item/reshelf")
    @ResponseBody
    public EgoResult reshelf(String ids){
        EgoResult er = new EgoResult();
        int index = tbItemServiceImpl.updItemStatus(ids, (byte)1);
        if(1 == index)
            er.setStatus(200);
        return er;
    }

    /**
     * 新增商品内容、商品描述和规格参数
     * @param item
     * @param desc
     * @return
     */
    @RequestMapping("item/save")
    @ResponseBody
    public EgoResult save(TbItem item, String desc, String itemParams)
    {
        EgoResult er = new EgoResult();
        int index;

        try {
            index = tbItemServiceImpl.insTbItemDesc(item, desc, itemParams);
            if(index == 1)
                er.setStatus(200);
        } catch (Exception e) {
//            e.printStackTrace();
            er.setData(e.getMessage());
        }
        return er;
    }

    /**
     * 修改商品内容、商品描述和规格参数
     * @param tbItem
     * @param desc
     * @param itemParamId
     * @param itemParams
     * @return
     */
    @RequestMapping("rest/item/update")
    @ResponseBody
    public EgoResult update(TbItem tbItem, String desc, String itemParamId, String itemParams)
    {
        EgoResult er = new EgoResult();
        try {
            int index = tbItemServiceImpl.updItemDesc(tbItem, desc, itemParamId, itemParams);
            if(index == 1)
                er.setStatus(200);
        } catch (Exception e) {
            // e.printStackTrace();
            er.setData(e.getMessage());
        }
        return er;
    }

}
