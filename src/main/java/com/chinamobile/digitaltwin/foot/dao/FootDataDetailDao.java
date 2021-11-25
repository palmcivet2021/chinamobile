package com.chinamobile.digitaltwin.foot.dao;


import com.chinamobile.digitaltwin.base.dao.BaseDataDao;
import com.chinamobile.digitaltwin.foot.bean.FootDataDetail;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface FootDataDetailDao extends BaseDataDao {

    void insertData(FootDataDetail footDataDetail);

    FootDataDetail getData(Map map);

}
