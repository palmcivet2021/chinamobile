package com.chinamobile.digitaltwin.base.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BaseDataDao<T> {
    void insertData(T t);

    void insertBatchData(List<T> list);

    void updateData(T t);

    List<T> getDataList(Map map);

    <T> T getData(Map map);

    List<String> getDeviceCode(String device_type);
}
