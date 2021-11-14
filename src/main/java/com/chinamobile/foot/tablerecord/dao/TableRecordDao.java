package com.chinamobile.foot.tablerecord.dao;

import com.chinamobile.foot.base.dao.BaseDataDao;
import com.chinamobile.foot.tablerecord.bean.TableRecord;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface TableRecordDao<T> extends BaseDataDao<T> {

    void insertData(TableRecord record);

    void updateData(TableRecord record);

    TableRecord getData(Map map);
}
