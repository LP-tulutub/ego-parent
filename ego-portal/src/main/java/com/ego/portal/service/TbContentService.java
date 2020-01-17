package com.ego.portal.service;

public interface TbContentService {
    /**
     * 大广告显示
     * @param count 显示个数
     * @param isSort 是否倒序排序
     * @return
     */
    String showBigAd(int count, boolean isSort);
}
