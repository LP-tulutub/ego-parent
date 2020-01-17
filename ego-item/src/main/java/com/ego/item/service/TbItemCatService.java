package com.ego.item.service;

import com.ego.item.pojo.PortalMenu;

public interface TbItemCatService {
    /**
     * 导航页面信息，树结构
     * @return 树结构Cat表
     */
    PortalMenu selTbItemCatAll();

    /**
     * 测试
     * @return 测试自定义
     */
    PortalMenu test();
}
