package com.chinamobile.foot.dao;

import com.chinamobile.foot.bean.TableRecord;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface TableRecordDao<T> extends BaseDataDao<T> {

    void insertData(TableRecord record);

    void updateData(TableRecord record);

    TableRecord getData(Map map);
}
