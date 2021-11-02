package com.chinamobile.foot.dao;

import com.chinamobile.foot.bean.BodyData;
import com.chinamobile.foot.bean.BodyDataDetail;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BodyDataDetailDao extends BaseDataDao {

    void insertData(BodyDataDetail userData);

    List<BodyDataDetail> getDataList(Map map);
}
