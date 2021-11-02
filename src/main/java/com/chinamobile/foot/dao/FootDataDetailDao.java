package com.chinamobile.foot.dao;

import com.chinamobile.foot.bean.FootDataDetail;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FootDataDetailDao extends BaseDataDao{

    void insertData(FootDataDetail footDataDetail);

    FootDataDetail getData(Map map);

}
