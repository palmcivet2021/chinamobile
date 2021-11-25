package com.chinamobile.digitaltwin.shoepad.dao;


import com.chinamobile.digitaltwin.base.dao.BaseDataDao;
import com.chinamobile.digitaltwin.shoepad.bean.ShoePadData;
import com.chinamobile.digitaltwin.shoepad.bean.ShoePadData2;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ShoePadDataDao extends BaseDataDao {

    void insertData(ShoePadData userData);

    void insertBatchData(List list);

    List<ShoePadData> getDataList(Map map);

    ShoePadData2 getShoePadData(Map map);
}
