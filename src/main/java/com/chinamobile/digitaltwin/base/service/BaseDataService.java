package com.chinamobile.digitaltwin.base.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface BaseDataService<T> {
    //单个插入
    public void insertData(T t);

    //批量插入
    public void insertBatchData(List<T> list);

    //更新
    public void updateData(T t);

    //查询
    public List<T> getData(T t, Map map, int pageNum, int pageSize);

    PageInfo<T> getDataByPage(T t, Map map, int pageNum, int pageSize);

    T getData(T t, Map map);

    List<String> getDeviceCode(String device_type);
}
