package com.chinamobile.foot.dao;

import com.chinamobile.foot.bean.BodyData;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BodyDataDao extends BaseDataDao {

    void insertBodyData(BodyData userData);
    void insertData(BodyData userData);

    void insertBatchData(List list);

    List<BodyData> getDataList(Map map);
}
