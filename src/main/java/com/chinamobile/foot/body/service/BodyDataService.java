package com.chinamobile.foot.body.service;

import com.chinamobile.foot.body.bean.BodyData;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface BodyDataService {
    //新增情感数据
    public void insertBodyData(BodyData bodyData);

    //查询情感数据
    public List<BodyData> getBodyData(Map map, int pageNum, int pageSize);

    PageInfo<BodyData> getBodyDataByPage(Map map, int pageNum, int pageSize);
}
