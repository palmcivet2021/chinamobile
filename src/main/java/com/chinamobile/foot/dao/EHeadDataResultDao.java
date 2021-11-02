package com.chinamobile.foot.dao;

import com.chinamobile.foot.bean.EHeadDataResult;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface EHeadDataResultDao extends BaseDataDao {

    void insertData(EHeadDataResult eHeadDataResult);

    EHeadDataResult getData(Map map);

}
