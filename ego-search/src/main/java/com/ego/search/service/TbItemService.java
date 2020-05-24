package com.ego.search.service;

import com.ego.pojo.TbItem;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;
import java.util.Map;

/**
 * @Auther: cty
 * @Date: 2020/5/24 11:53
 * @Description:
 * @version: 1.0
 */
public interface TbItemService {

    /**
     * solr数据初始化
     */
    void init() throws IOException, SolrServerException;

    /**
     * 分页查询
     * @param query
     * @param page
     * @param rows
     * @return
     */
    Map<String, Object> selByQuery(String query, int page, int rows) throws IOException, SolrServerException;

    /**
     * solr新增
     * @param map
     * @return
     */
    int add(Map<String, Object> map) throws IOException, SolrServerException;
}
