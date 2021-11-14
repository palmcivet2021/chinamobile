package com.chinamobile.foot.footdata.dao;

import com.chinamobile.foot.base.dao.BaseDataDao;
import com.chinamobile.foot.footdata.bean.FootData;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FootDataDao extends BaseDataDao {

    void insertFootData(FootData footData);
    void insertData(FootData footData);

    //void insertBatchData(List<FootData> list);

    List<FootData> getDataList(Map map);
}
