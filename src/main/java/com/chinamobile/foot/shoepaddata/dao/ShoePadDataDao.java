package com.chinamobile.foot.shoepaddata.dao;


import com.chinamobile.foot.base.dao.BaseDataDao;
import com.chinamobile.foot.shoepaddata.bean.ShoePadData;
import com.chinamobile.foot.shoepaddata.bean.ShoePadData2;
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
