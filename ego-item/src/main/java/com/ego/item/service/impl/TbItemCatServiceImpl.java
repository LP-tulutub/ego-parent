package com.ego.item.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.item.pojo.PortalMenu;
import com.ego.item.pojo.PortalMenuNode;
import com.ego.item.service.TbItemCatService;
import com.ego.pojo.TbItemCat;
import com.ego.redis.dao.JedisDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@SuppressWarnings("unchecked")
@Service
public class TbItemCatServiceImpl implements TbItemCatService {
    @Reference
    TbItemCatDubboService tbItemCatDubboServiceImpl;
    @Resource
    private JedisDao jedisDaoImpl;
    @Value("${redis.menu.key}")
    private String menuKey;

    /**
     * 导航页面信息，树结构
     * @return 树结构Cat表
     */
    @Override
    public PortalMenu selTbItemCatAll() {
        //一级菜单
        List<TbItemCat> list =  tbItemCatDubboServiceImpl.selTbItemCat(0);
        PortalMenu portalMenu = new PortalMenu();
        portalMenu.setData(selAllMenu(list));

        return portalMenu;
    }

    /**
     * 最终返回结果所有查询到的结果
     * @param list
     * @return
     */
    private List<Object> selAllMenu(List<TbItemCat> list) {
        if (jedisDaoImpl.exists(menuKey)){
            String menuJson = jedisDaoImpl.get(menuKey);
            List<Object> menuList = JsonUtils.jsonToList(menuJson, Object.class);
            System.out.println("ego-item(TbItemCatServiceImpl)# "+"redis获取："+menuList);
            return menuList;
        }

        List<Object> listNode = selMenuNode(list);

        //导入redis
        jedisDaoImpl.set(menuKey, JsonUtils.objectToJson(listNode));
        System.out.println("ego-item(TbItemCatServiceImpl)# "+"mysql获取："+listNode);
        return listNode;
    }

    /**
     * 此方法为递归，while版是selMenuNodeWhile
     * @param list
     * @return
     */
    private  List<Object> selMenuNode(List<TbItemCat> list){
        List<Object> listNode = new ArrayList<>();
        for (TbItemCat tbItemCat : list) {
            if (tbItemCat.getIsParent()) {
                PortalMenuNode pmd = new PortalMenuNode();
                pmd.setU("/products/" + tbItemCat.getId() + ".html");
                pmd.setN("<a href='/products/" + tbItemCat.getId() + ".html'>" + tbItemCat.getName() + "</a>");
                pmd.setI(selMenuNode(tbItemCatDubboServiceImpl.selTbItemCat(tbItemCat.getId())));
                listNode.add(pmd);
            } else {
                listNode.add("/products/" + tbItemCat.getId() + ".html|" + tbItemCat.getName());
            }
        }

        return listNode;
    }

    /**
     * 此方法为循环，递归版selMenuNode
     * @param list
     * @return
     */
    private List<Object> selMenuNodeWhile(List<TbItemCat> list){
        List<Object> listNode = new ArrayList<>();
        Deque stack = new LinkedList();
        List<TbItemCat> cats = tbItemCatDubboServiceImpl.selByIsParent(false);
        Map<Object, ArrayList<Object>> map = new HashMap<>();
        for (TbItemCat cat : cats){
            if (!map.containsKey(cat.getParentId())){
                map.put(cat.getParentId(), new ArrayList<Object>());
                map.get(cat.getParentId()).add("/products/" + cat.getId() + ".html|" + cat.getName());
                stack.add(cat.getParentId());
            }else {
                map.get(cat.getParentId()).add("/products/" + cat.getId() + ".html|" + cat.getName());
            }
        }
        while (!stack.isEmpty()){
            Object pop = stack.pop();
            TbItemCat tbItemCat = tbItemCatDubboServiceImpl.selTbItemCatId((long) pop);
            PortalMenuNode pmd = new PortalMenuNode();
            pmd.setU("/products/" + tbItemCat.getId() + ".html");
            pmd.setN("<a href='/products/" + tbItemCat.getId() + ".html'>" + tbItemCat.getName() + "</a>");
            pmd.setI(map.get(pop));
            if (!map.containsKey(tbItemCat.getParentId())){
                map.put(tbItemCat.getParentId(), new ArrayList<Object>());
                map.get(tbItemCat.getParentId()).add(pmd);
                if (tbItemCat.getParentId()!=(long)0){
                    stack.add(tbItemCat.getParentId());
                }
            }else {
                map.get(tbItemCat.getParentId()).add(pmd);
            }
        }
        System.out.println("ego-item(TbItemCatServiceImpl-selMenuNodeWhile)# map："+map.get((long)0));
        listNode = map.get((long)0);

        return listNode;
    }

    @Override
    public PortalMenu test() {
        PortalMenu portalMenu = new PortalMenu();
        portalMenu.setData(selMenuNodeWhile(null));

        return portalMenu;
    }

}
