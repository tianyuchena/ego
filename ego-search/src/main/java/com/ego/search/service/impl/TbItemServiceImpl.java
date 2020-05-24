package com.ego.search.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemDesc;
import com.ego.search.pojo.TbItemChild;
import com.ego.search.service.TbItemService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * @Auther: cty
 * @Date: 2020/5/24 11:55
 * @Description:
 * @version: 1.0
 */
@Service
public class TbItemServiceImpl implements TbItemService {

    @Reference
    private TbItemDubboService tbItemDubboServiceImpl;
    @Reference
    private TbItemCatDubboService tbItemCatDubboServiceImpl;
    @Reference
    private TbItemDescDubboService tbItemDescDubboServiceImpl;
    @Resource
    private CloudSolrClient solrClient;

    @Override
    public void init() throws IOException, SolrServerException {

        List<TbItem> tbItems = tbItemDubboServiceImpl.selByStatus((byte) 1);
        for(TbItem item : tbItems)
        {
            TbItemDesc desc = tbItemDescDubboServiceImpl.selById(item.getId());
            TbItemCat cat = tbItemCatDubboServiceImpl.selById(item.getCid());

            SolrInputDocument doc = new SolrInputDocument();
            doc.addField("id", item.getId());
            doc.addField("item_title", item.getTitle());
            doc.addField("item_sell_point", item.getSellPoint());
            doc.addField("item_price", item.getPrice());
            doc.addField("item_image", item.getImage());
            doc.addField("item_category_name", cat.getName());
            doc.addField("item_desc", desc.getItemDesc());
            solrClient.add(doc);
        }
        solrClient.commit();
    }  // end method init


    @Override
    public Map<String, Object> selByQuery(String query, int page, int rows) throws IOException, SolrServerException {

        SolrQuery params = new SolrQuery();

        // 设置查询字段
        params.setQuery("item_keywords:" + query);
        // 设置分页条件
        params.setStart(rows*(page-1));
        params.setRows(rows);
        // 设置高亮
        params.setHighlight(true);
        params.addHighlightField("item_title");
        params.setHighlightSimplePre("<span style='color:red'>");
        params.setHighlightSimplePost("</span>");

        // 查询并获取返回结果
        QueryResponse response = solrClient.query(params);
        // 获取未高亮内容
        SolrDocumentList docs = response.getResults();
        // 获取高亮内容
        Map<String, Map<String, List<String>>> hls = response.getHighlighting();

        List<TbItemChild> itemChildList = new ArrayList<>();
        for(SolrDocument doc : docs)
        {
            TbItemChild itemChild = new TbItemChild();

            // 设置id
            itemChild.setId(Long.parseLong(doc.getFieldValue("id").toString()));
            // 设置标题
            List<String> hlTitleList = hls.get(doc.getFieldValue("id").toString()).get("item_title");  // 从高亮中获取标题
            if(hlTitleList!=null && hlTitleList.size()>0)
                itemChild.setTitle(hlTitleList.get(0));
            else
                itemChild.setTitle(doc.getFieldValue("item_title").toString());
            // 设置价格
            itemChild.setPrice((Long)doc.getFieldValue("item_price"));
            // 设置图片
            Object image = doc.getFieldValue("item_image");  // 图片可能不存在
            itemChild.setImages(image==null||image.equals("")?new String[1]:image.toString().split(","));
            // 设置卖点
            itemChild.setSellPoint(doc.getFieldValue("item_sell_point").toString());

            itemChildList.add(itemChild);
        }  // end for

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("itemList", itemChildList);
        long total = docs.getNumFound();
        resultMap.put("totalPages", total%rows==0?total:total%rows+1);

        return resultMap;
    }

    @Override
    public int add(Map<String, Object> map) throws IOException, SolrServerException {

        LinkedHashMap<String, Object> item = (LinkedHashMap)map.get("item");
        String desc = map.get("desc").toString();

        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", item.get("id"));
        doc.addField("item_title", item.get("title"));
        doc.addField("item_sell_point", item.get("sellPoint"));
        doc.addField("item_price", item.get("price"));
        doc.addField("item_image", item.get("image"));
        doc.addField("item_category_name", tbItemCatDubboServiceImpl.selById((Integer)(item.get("cid"))).getName());
        doc.addField("item_desc", desc);
        UpdateResponse response = solrClient.add(doc);
        solrClient.commit();

        if(response.getStatus() == 0)
            return 1;
        return 0;
    }

}
