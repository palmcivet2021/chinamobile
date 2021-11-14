package com.chinamobile.foot.bodydata.dao;

import com.chinamobile.foot.base.dao.BaseDataDao;
import com.chinamobile.foot.bodydata.bean.BodyData;
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
