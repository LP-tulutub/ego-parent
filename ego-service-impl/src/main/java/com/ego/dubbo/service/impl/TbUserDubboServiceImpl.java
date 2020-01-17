package com.ego.dubbo.service.impl;

import com.ego.dubbo.service.TbUserDubboService;
import com.ego.mapper.TbUserMapper;
import com.ego.pojo.TbUser;
import com.ego.pojo.TbUserExample;

import javax.annotation.Resource;
import java.util.List;

public class TbUserDubboServiceImpl implements TbUserDubboService {
    @Resource
    private TbUserMapper tbUserMapper;

    /**
     * 查询是否有某用户
     * @param tbUser
     * @return
     */
    @Override
    public TbUser selByUser(TbUser tbUser) {
        TbUserExample tbUserExample = new TbUserExample();
        tbUserExample.createCriteria().andUsernameEqualTo(tbUser.getUsername()).andPasswordEqualTo(tbUser.getPassword());
        List<TbUser> users = tbUserMapper.selectByExample(tbUserExample);
        return users.get(0);
    }
}
