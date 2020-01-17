package com.ego.manage.controller;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbItemService;
import com.ego.pojo.TbItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class TbItemController {
    @Resource
    private TbItemService tbItemServiceImpl;

    /**
     * 分页显示商品
     * @return
     */
    @RequestMapping("item/list")
    @ResponseBody
    public EasyUIDataGrid show(int page, int rows){
        return tbItemServiceImpl.show(page, rows);
    }

    /**
     * 编辑
     * @return
     */
    @RequestMapping("rest/page/item-edit")
    public String showItemEdit(){
        return "item-edit";
    }

    /**
     * 上架
     * @param ids 多个id
     * @return
     */
    @RequestMapping("rest/item/reshelf")
    @ResponseBody
    public EgoResult reshelf(String ids){
        EgoResult egoResult = new EgoResult();
        int index = tbItemServiceImpl.updItemState(ids, (byte)1);
        if(index==1){
            egoResult.setStatus(200);
        }
        return egoResult;
    }

    /**
     * 下架
     * @param ids 多个id
     * @return
     */
    @RequestMapping("rest/item/instock")
    @ResponseBody
    public EgoResult instock(String ids){
        EgoResult egoResult = new EgoResult();
        int index = tbItemServiceImpl.updItemState(ids, (byte)2);
        if(index==1){
            egoResult.setStatus(200);
        }
        return egoResult;
    }

    /**
     * 删除
     * @param ids 多个id
     * @return
     */
    @RequestMapping("rest/item/delete")
    @ResponseBody
    public EgoResult delete(String ids){
        EgoResult egoResult = new EgoResult();
        int index = tbItemServiceImpl.updItemState(ids, (byte)3);
        if(index==1){
            egoResult.setStatus(200);
        }
        return egoResult;
    }

    /**
     * 添加新的商品信息
     * @param item 商品信息
     * @param desc 商品描述
     * @param itemParams 商品规格参数
     * @return
     */
    @RequestMapping("item/save")
    @ResponseBody
    public EgoResult save(TbItem item, String desc, String itemParams){
        EgoResult er = new EgoResult();
        int index;
        try {
            index = tbItemServiceImpl.insTtemADesc(item, desc, itemParams);
            if(index==1){
                er.setStatus(200);
            }
        } catch (Exception e) {
			e.printStackTrace();

        }
        return er;
    }
}
