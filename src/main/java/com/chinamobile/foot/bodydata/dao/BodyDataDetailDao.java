package com.chinamobile.foot.bodydata.dao;

import com.chinamobile.foot.base.dao.BaseDataDao;
import com.chinamobile.foot.bodydata.bean.BodyDataDetail;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BodyDataDetailDao extends BaseDataDao {

    void insertData(BodyDataDetail userData);

    List<BodyDataDetail> getDataList(Map map);
}
