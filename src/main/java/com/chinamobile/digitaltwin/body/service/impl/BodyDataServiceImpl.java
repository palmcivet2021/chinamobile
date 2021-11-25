package com.chinamobile.digitaltwin.body.service.impl;

import com.chinamobile.digitaltwin.body.bean.BodyData;
import com.chinamobile.digitaltwin.body.dao.BodyDataDao;
import com.chinamobile.digitaltwin.body.service.BodyDataService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BodyDataServiceImpl implements BodyDataService {
    @Autowired
    private BodyDataDao bodyDataDao;

    @Override
    public void insertBodyData(BodyData userData) {
        bodyDataDao.insertBodyData(userData);
    }

    @Override
    public List<BodyData> getBodyData(Map map, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<BodyData> lists = bodyDataDao.getDataList(map);
        return lists;
    }

    @Override
    public PageInfo<BodyData> getBodyDataByPage(Map map, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<BodyData> lists = bodyDataDao.getDataList(map);
        PageInfo<BodyData> pageInfo = new PageInfo<BodyData>(lists);

        return pageInfo;
    }
}
