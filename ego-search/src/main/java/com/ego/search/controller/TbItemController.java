package com.ego.search.controller;

import com.ego.search.service.TbItemService;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * @Auther: cty
 * @Date: 2020/5/24 12:24
 * @Description:
 * @version: 1.0
 */
@Controller
public class TbItemController {
    @Resource
    private TbItemService tbItemServiceImpl;

    @RequestMapping(value = "solr/init", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String init()
    {
        long start = System.currentTimeMillis();

        try {
            tbItemServiceImpl.init();
            long end = System.currentTimeMillis();
            return "初始化总时间：" + (end-start)/1000 + "秒";
        } catch (Exception e) {
            e.printStackTrace();
            return "初始化失败";
        }
    }  // end method init

    @RequestMapping("search.html")
    public String search(Model model, String q, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "12")int rows)
    {
        try {
            q = new String(q.getBytes("iso-8859-1"), "utf-8");
            Map<String, Object> map = tbItemServiceImpl.selByQuery(q, page, rows);
            model.addAttribute("query", q);
            model.addAttribute("itemList", map.get("itemList"));
            model.addAttribute("totalPages", map.get("totalPages"));
            model.addAttribute("page", page);
            return "search";

        } catch (Exception e) {
            e.printStackTrace();
            return "error/exception";
        }
    }  // end method search

    /**
     * solr新增
     * @param map
     * @return
     */
    @RequestMapping("solr/add")
    @ResponseBody
    public int add(@RequestBody Map<String, Object> map)  // RequestBody将Json字符串转化为对象
    {
        try {
            return tbItemServiceImpl.add(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
