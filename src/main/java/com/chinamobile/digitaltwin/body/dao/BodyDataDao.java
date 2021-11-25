package com.chinamobile.digitaltwin.body.dao;

import com.chinamobile.digitaltwin.base.dao.BaseDataDao;
import com.chinamobile.digitaltwin.body.bean.BodyData;
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
