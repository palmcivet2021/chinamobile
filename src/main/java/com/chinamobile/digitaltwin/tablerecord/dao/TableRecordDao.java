package com.chinamobile.digitaltwin.tablerecord.dao;

import com.chinamobile.digitaltwin.base.dao.BaseDataDao;
import com.chinamobile.digitaltwin.tablerecord.bean.TableRecord;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface TableRecordDao<T> extends BaseDataDao<T> {

    void insertData(TableRecord record);

    void updateData(TableRecord record);

    TableRecord getData(Map map);
}
