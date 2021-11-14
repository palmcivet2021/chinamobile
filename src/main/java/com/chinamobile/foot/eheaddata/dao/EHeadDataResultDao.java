package com.chinamobile.foot.eheaddata.dao;

import com.chinamobile.foot.base.dao.BaseDataDao;
import com.chinamobile.foot.eheaddata.bean.EHeadDataResult;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface EHeadDataResultDao extends BaseDataDao {

    void insertData(EHeadDataResult eHeadDataResult);

    EHeadDataResult getData(Map map);

}
