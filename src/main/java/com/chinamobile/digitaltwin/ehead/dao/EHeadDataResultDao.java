package com.chinamobile.digitaltwin.ehead.dao;

import com.chinamobile.digitaltwin.base.dao.BaseDataDao;
import com.chinamobile.digitaltwin.ehead.bean.EHeadDataResult;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface EHeadDataResultDao extends BaseDataDao {

    void insertData(EHeadDataResult eHeadDataResult);

    EHeadDataResult getData(Map map);

}
