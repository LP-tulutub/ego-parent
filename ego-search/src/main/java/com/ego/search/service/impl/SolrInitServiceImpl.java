package com.ego.search.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemDesc;
import com.ego.commons.pojo.TbItemChild;
import com.ego.search.service.SolrInitService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SolrInitServiceImpl implements SolrInitService {
    @Reference
    private TbItemDubboService tbItemDubboServiceImpl;
    @Reference
    private TbItemCatDubboService tbItemCatDubboServiceImpl;
    @Reference
    private TbItemDescDubboService tbItemDescDubboServiceImpl;

    @Resource
    private CloudSolrClient solrClient;

    /**
     * solr初始化数据
     */
    @Override
    public void solrInit() throws IOException, SolrServerException {

        List<TbItem> list = tbItemDubboServiceImpl.selAllByStatus((byte)1);
        for (TbItem item : list){
            TbItemCat cat = tbItemCatDubboServiceImpl.selTbItemCatId(item.getCid());
            TbItemDesc desc = tbItemDescDubboServiceImpl.selById(item.getId());

            SolrInputDocument doc = new SolrInputDocument();

            doc.addField("id", item.getId());
            doc.addField("item_title", item.getTitle());
            doc.addField("item_sell_point", item.getSellPoint());
            doc.addField("item_price",item.getPrice() );
            doc.addField("item_image", item.getImage());
            doc.addField("item_category_name", cat.getName());
            doc.addField("item_desc", desc.getItemDesc());

            solrClient.add(doc);
        }
        solrClient.commit();

    }

    /**
     * 搜索功能
     * @param query 查询输入的文本
     * @param page 查询第几页
     * @param rows 每页多少条
     * @return
     */
    @Override
    public Map<String, Object> selByQuery(String query, int page, int rows) throws IOException, SolrServerException {
        SolrQuery params = new SolrQuery();
        //设置分页条件
        params.setStart(rows*(page-1));
        params.setRows(rows);
        //设置条件
        params.setQuery("item_keywords:"+query);
        //设置高亮
        params.setHighlight(true);
        params.addHighlightField("item_title");
        params.setHighlightSimplePre("<span style='color:red'>");
        params.setHighlightSimplePost("</span>");

        QueryResponse response = solrClient.query(params);

        List<TbItemChild> listChild = new ArrayList<>();
        //未高亮内容
        SolrDocumentList listSolr = response.getResults();
        //高亮内容
        Map<String, Map<String, List<String>>> hh = response.getHighlighting();

        for (SolrDocument doc : listSolr) {
            TbItemChild  child = new TbItemChild();

            child.setId(Long.parseLong(doc.getFieldValue("id").toString()));
            List<String> list = hh.get(doc.getFieldValue("id")).get("item_title");
            System.out.println("ego-search(solrInitServiceImpl)# "+hh.get(doc.getFieldValue("id")));
            System.out.println("ego-search(solrInitServiceImpl)# "+list);
            System.out.println("-------------------------------------------");
            if(list!=null&&list.size()>0){
                child.setTitle(list.get(0));
            }else{
                child.setTitle(doc.getFieldValue("item_title").toString());
            }
            child.setPrice((Long)doc.getFieldValue("item_price"));
            Object image = doc.getFieldValue("item_image");
            child.setImages(image==null||image.equals("")?new String[1]:image.toString().split(","));
            child.setSellPoint(doc.getFieldValue("item_sell_point").toString());

            listChild.add(child);
        }


        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("itemList", listChild);
        resultMap.put("totalPages", listSolr.getNumFound()%rows==0?listSolr.getNumFound()/rows:listSolr.getNumFound()/rows+1);


        return resultMap;
    }

    /**
     * solr新增
     * @param map 新增需要前端上传的内容
     * @param desc
     * @return
     */
    @Override
    public int solrAdd(Map<String,Object> map,String desc) throws IOException, SolrServerException {
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", map.get("id"));
        doc.addField("item_title", map.get("title"));
        doc.addField("item_sell_point", map.get("sellPoint"));
        doc.addField("item_price", map.get("price"));
        doc.addField("item_image", map.get("image"));
        doc.addField("item_category_name", tbItemCatDubboServiceImpl.selTbItemCatId((Integer)map.get("cid")).getName());
        doc.addField("item_desc", desc);
        UpdateResponse response = solrClient.add(doc);
        solrClient.commit();
        if(response.getStatus()==0){
            return 1;
        }
        return 0;
    }
}
