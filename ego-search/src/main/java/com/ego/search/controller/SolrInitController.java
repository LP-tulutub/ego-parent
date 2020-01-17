package com.ego.search.controller;

import com.ego.search.service.SolrInitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
public class SolrInitController {
    @Resource
    private SolrInitService solrInitServiceImpl;

    /**
     * 初始化solr数据
     * @return
     */
    @RequestMapping(value = "solr/init", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String init(){
        long start = System.currentTimeMillis();

        try {
            solrInitServiceImpl.solrInit();
            long end = System.currentTimeMillis();
            return "初始化完成，时间："+(end-start)/1000+"秒";
        } catch (Exception e) {
            e.printStackTrace();
            return "初始化失败";
        }
    }


    /**
     * 搜索功能
     * @param model
     * @param q
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("search.html")
    public String search(Model model, String q, @RequestParam(defaultValue="1") int page, @RequestParam(defaultValue="12") int rows){
        try {
            q = new String(q.getBytes("iso-8859-1"),"utf-8");
            Map<String, Object> map = solrInitServiceImpl.selByQuery(q, page, rows);
            model.addAttribute("query", q);
            model.addAttribute("itemList", map.get("itemList"));
            model.addAttribute("totalPages", map.get("totalPages"));
            model.addAttribute("page", page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "search";
    }

    /**
     * solr新增
     * @param map
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("solr/add")
    @ResponseBody
    public int add(@RequestBody Map<String,Object> map){
        try {
            return solrInitServiceImpl.solrAdd((Map<String, Object>) map.get("item"),map.get("desc").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
