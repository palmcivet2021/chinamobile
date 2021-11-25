package com.chinamobile.digitaltwin.body.dao;

import com.chinamobile.digitaltwin.base.dao.BaseDataDao;
import com.chinamobile.digitaltwin.body.bean.BodyDataDetail;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BodyDataDetailDao extends BaseDataDao {

    void insertData(BodyDataDetail userData);

    List<BodyDataDetail> getDataList(Map map);
}
