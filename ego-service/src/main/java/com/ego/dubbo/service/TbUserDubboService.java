package com.ego.dubbo.service;

import com.ego.pojo.TbUser;

public interface TbUserDubboService {
    /**
     * 查询数据库是否有某用户
     * @param tbUser
     * @return
     */
    TbUser selByUser(TbUser tbUser);
}
