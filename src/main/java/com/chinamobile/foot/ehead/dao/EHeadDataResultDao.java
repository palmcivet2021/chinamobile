package com.chinamobile.foot.ehead.dao;

import com.chinamobile.foot.base.dao.BaseDataDao;
import com.chinamobile.foot.ehead.bean.EHeadDataResult;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface EHeadDataResultDao extends BaseDataDao {

    void insertData(EHeadDataResult eHeadDataResult);

    EHeadDataResult getData(Map map);

}
