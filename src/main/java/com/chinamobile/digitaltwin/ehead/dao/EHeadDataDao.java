package com.chinamobile.digitaltwin.ehead.dao;

import com.chinamobile.digitaltwin.base.dao.BaseDataDao;
import com.chinamobile.digitaltwin.ehead.bean.EHeadData;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface EHeadDataDao extends BaseDataDao {

    void insertData(EHeadData userData);

    void insertBatchData(List list);

    List<EHeadData> getDataList(Map map);
}
