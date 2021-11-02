package com.chinamobile.foot.service.impl;

import com.chinamobile.foot.bean.FootData;
import com.chinamobile.foot.dao.FootDataDao;
import com.chinamobile.foot.service.FootDataService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FootDataServiceImpl implements FootDataService {
    @Autowired
    private FootDataDao footDataDao;

    @Override
    public void insertFootData(FootData footData) {
        //footDataDao.insertFootData(footData);
        footDataDao.insertData(footData);
    }

    @Override
    public void insertBatchData(List<FootData> list) {
        footDataDao.insertBatchData(list);
    }


    @Override
    public List<FootData> getFootData(Map map, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<FootData> lists = footDataDao.getDataList(map);
        return lists;
    }

    @Override
    public PageInfo<FootData> getFootDataByPage(Map map, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<FootData> lists = footDataDao.getDataList(map);
        PageInfo<FootData> pageInfo = new PageInfo<FootData>(lists);

        return pageInfo;
    }


}
