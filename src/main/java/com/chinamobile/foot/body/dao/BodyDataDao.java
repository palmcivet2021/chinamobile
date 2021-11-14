package com.chinamobile.foot.body.dao;

import com.chinamobile.foot.base.dao.BaseDataDao;
import com.chinamobile.foot.body.bean.BodyData;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BodyDataDao extends BaseDataDao {

    void insertData(BodyData userData);

    void insertBatchData(List list);

    List<BodyData> getDataList(Map map);
}
