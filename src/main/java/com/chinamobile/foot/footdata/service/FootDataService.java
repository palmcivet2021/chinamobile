package com.chinamobile.foot.footdata.service;


import com.chinamobile.foot.footdata.bean.FootData;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface FootDataService {
    //新增脚型数据
    public void insertFootData(FootData footData);

    //批量插入脚型数据
    public void insertBatchData(List<FootData> list);

    //查询脚型数据
    public List<FootData> getFootData(Map map, int pageNum, int pageSize);

    PageInfo<FootData> getFootDataByPage(Map map, int pageNum, int pageSize);
}
