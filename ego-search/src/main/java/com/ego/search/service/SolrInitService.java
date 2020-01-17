package com.ego.search.service;

import com.ego.pojo.TbItem;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;
import java.util.Map;

public interface SolrInitService {
    /**
     * solr初始化数据
     */
    void solrInit() throws IOException, SolrServerException;

    /**
     * 搜索功能
     * @param query 查询输入的文本
     * @param page 查询第几页
     * @param rows 每页多少条
     * @return
     */
    Map<String, Object> selByQuery(String query, int page, int rows) throws IOException, SolrServerException;

    /**
     * solr新增
     * @param map 新增需要前端上传的内容
     * @param desc
     * @return
     */
    int solrAdd(Map<String,Object> map,String desc) throws IOException, SolrServerException;
}
