package com.chinamobile.foot.footdata.dao;


import com.chinamobile.foot.base.dao.BaseDataDao;
import com.chinamobile.foot.footdata.bean.FootDataDetail;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface FootDataDetailDao extends BaseDataDao {

    void insertData(FootDataDetail footDataDetail);

    FootDataDetail getData(Map map);

}
