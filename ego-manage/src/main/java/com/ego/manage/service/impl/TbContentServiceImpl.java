package com.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.IDUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.manage.service.TbContentService;
import com.ego.pojo.TbContent;
import com.ego.redis.dao.JedisDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class TbContentServiceImpl implements TbContentService {
    @Reference
    private TbContentDubboService tbContentDubboServiceImpl;
    @Resource
    private JedisDao jedisDaoImpl;
    @Value("${redis.bigpic.key}")
    private String key;

    /**
     * 内容查询功能
     * @param categoryId
     * @param page 第几页
     * @param rows 每页多少条
     * @return
     */
    @Override
    public EasyUIDataGrid list(long categoryId, int page, int rows) {
        return tbContentDubboServiceImpl.sqlByCidPage(categoryId, page, rows);
    }

    /**
     * 新增内容
     * @param tbContent
     * @return
     */
    @Override
    public EgoResult save(TbContent tbContent) {
        EgoResult egoResult = new EgoResult();

        Date date = new Date();
        long id = IDUtils.genItemId();
        tbContent.setId(id);
        tbContent.setUpdated(date);
        tbContent.setCreated(date);

        int index = tbContentDubboServiceImpl.insTbContent(tbContent);
        if (index == 1) {
            egoResult.setStatus(200);
        }else{
            egoResult.setData(0);
            egoResult.setData("添加失败");
        }

        //redis同步到前台
        if (index==1&&jedisDaoImpl.exists(key)){
            String str = jedisDaoImpl.get(key);
            if(str!=null&&!str.equals("")){
                List<HashMap> list = JsonUtils.jsonToList(str, HashMap.class);
                HashMap<String,Object> map = new HashMap<>();

                map.put("srcB", tbContent.getPic2());
                map.put("height", 240);
                map.put("alt", "对不起,加载图片失败");
                map.put("width", 670);
                map.put("src", tbContent.getPic());
                map.put("widthB", 550);
                map.put("href", tbContent.getUrl() );
                map.put("heightB", 240);

                if (list.size()==6){
                    list.remove(5);
                }
                list.add(0, map);
                jedisDaoImpl.set(key, JsonUtils.objectToJson(list));
            }
        }

        return egoResult;
    }
}
