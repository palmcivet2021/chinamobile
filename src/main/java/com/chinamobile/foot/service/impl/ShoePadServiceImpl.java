package com.chinamobile.foot.service.impl;

import com.chinamobile.foot.bean.ShoePadData2;
import com.chinamobile.foot.dao.ShoePadDataDao;
import com.chinamobile.foot.service.ShoePadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
//public class ShoePadServiceImpl extends BaseDataServiceImpl<ShoePadData2> implements ShoePadService  {
public class ShoePadServiceImpl implements ShoePadService  {
    @Autowired
    private ShoePadDataDao shoePadDataDao;


    @Override
    public ShoePadData2 getShoePadData(Map map) {
        ShoePadData2 data = shoePadDataDao.getShoePadData(map);

        return data;
    }

}
