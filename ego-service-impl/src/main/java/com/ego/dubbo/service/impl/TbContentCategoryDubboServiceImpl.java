package com.ego.dubbo.service.impl;

import com.ego.commons.pojo.DubboResult;
import com.ego.dubbo.service.TbContentCategoryDubboService;
import com.ego.mapper.TbContentCategoryMapper;
import com.ego.pojo.TbContentCategory;
import com.ego.pojo.TbContentCategoryExample;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TbContentCategoryDubboServiceImpl implements TbContentCategoryDubboService {
    @Resource
    private TbContentCategoryMapper tbContentCategoryMapper;

    /**
     * 按父id查询
     * @param pid 父id
     * @return 按父id查询结果
     */
    @Override
    public List<TbContentCategory> selTbContentCategoryPid(long pid) {
        TbContentCategoryExample tbContentCategoryExample = new TbContentCategoryExample();
        tbContentCategoryExample.createCriteria().andParentIdEqualTo(pid).andStatusEqualTo(1);
        return tbContentCategoryMapper.selectByExample(tbContentCategoryExample);
    }

    /**
     * 事务回滚+插入升级新的内容
     * @param tbContentCategory 要插入的内容
     * @param pid 插入的父id
     * @return
     */
    @Override
    public DubboResult insUpdTbContentCategory(TbContentCategory tbContentCategory, long pid) throws Exception {
        DubboResult dubboResult = new DubboResult();
        TbContentCategory parent = new TbContentCategory();
        parent.setId(pid);
        parent.setIsParent(true);
        int index = 0;
        try {
            index+= tbContentCategoryMapper.updateByPrimaryKeySelective(parent);
            index+= tbContentCategoryMapper.insertSelective(tbContentCategory);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (index==2){
            dubboResult.setStatus(200);
            Map<String,Long> map = new HashMap<>();
            map.put("id", tbContentCategory.getId());
            dubboResult.setData(map);
            return dubboResult;
        }else{
            throw new Exception("新增出现服务错误");
        }


    }

    /**
     * 商品类目重命名
     * @param id 商品类目id
     * @param name 新的商品类目名称
     * @return
     */
    @Override
    public int updTbContentCategoryName(long id, String name) {
        TbContentCategory tbContentCategory = new TbContentCategory();
        tbContentCategory.setId(id);
        tbContentCategory.setName(name);
        return tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory);
    }

    @Override
    public TbContentCategory selTbContentCategoryid(long id) {
        return tbContentCategoryMapper.selectByPrimaryKey(id);
    }

    /**
     * 事务回滚+删除
     * @param id 删除商品类目的id
     * @return
     */
    @Override
    public int updTbContentCategoryStatus(Long id) throws Exception {
        TbContentCategory cate = new TbContentCategory();
        cate.setId(id);
        cate.setStatus(0);

        try {
            tbContentCategoryMapper.updateByPrimaryKeySelective(cate);

            TbContentCategory tbContentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
            TbContentCategoryExample tbContentCategoryExample = new TbContentCategoryExample();
            tbContentCategoryExample.createCriteria().andParentIdEqualTo(tbContentCategory.getParentId()).andStatusEqualTo(1);
            List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(tbContentCategoryExample);
            if (list==null||list.size()==0){
                TbContentCategory parent = new TbContentCategory();
                parent.setId(tbContentCategory.getParentId());
                parent.setIsParent(false);
                tbContentCategoryMapper.updateByPrimaryKeySelective(parent);
            }
            return 1;
        }catch (Exception e){
            throw new Exception("删除失败");
        }

    }



}
