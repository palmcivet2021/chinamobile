package com.chinamobile.foot.service.impl;

import com.chinamobile.foot.bean.*;
import com.chinamobile.foot.dao.*;
import com.chinamobile.foot.service.BaseDataService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BaseDataServiceImpl<T> implements BaseDataService<T> {
    @Autowired
    private BaseDataDao baseDataDao;
    @Autowired
    private BodyDataDao bodyDataDao;
    @Autowired
    private BodyDataDetailDao bodyDataDetailDao;
    @Autowired
    private FootDataDao footDataDao;
    @Autowired
    private FootDataDetailDao footDataDetailDao;
    @Autowired
    private TableRecordDao tableRecordDao;
    @Autowired
    private EHeadDataDao eHeadDataDao;
    @Autowired
    private EHeadDataResultDao eHeadDataResultDao;
    @Autowired
    private ShoePadDataDao shoePadDataDao;
    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public void insertData(T t) {
        /*if(t instanceof BodyData){
            baseDataDao = bodyDataDao;
        }else if(t instanceof FootData){
            baseDataDao = footDataDao;
        }else if(t instanceof TableRecord){
            baseDataDao = tableRecordDao;
        }*/
        baseDataDao = getBaseDataDao(t);
        baseDataDao.insertData(t);
    }

    //批量插入
    public void insertBatchData(List<T> list){
        baseDataDao = getBaseDataDao(list.get(0));
        baseDataDao.insertBatchData(list);
    }

    //更新
    public void updateData(T t){
        baseDataDao = getBaseDataDao(t);
        baseDataDao.updateData(t);
    }



    @Override
    public List<T> getData(T t, Map map, int pageNum, int pageSize) {
        baseDataDao = getBaseDataDao(t);
        List<T> lists = baseDataDao.getDataList(map);
        return lists;
    }

    @Override
    public PageInfo<T> getDataByPage(T t, Map map, int pageNum, int pageSize) {
        /*if(t instanceof BodyData){
            baseDataDao = bodyDataDao;
        }*/
        baseDataDao = getBaseDataDao(t);
        PageHelper.startPage(pageNum, pageSize);
        List<T> lists = baseDataDao.getDataList(map);
        PageInfo<T> pageInfo = new PageInfo<T>(lists);

        return pageInfo;
    }

    @Override
    public T getData(T t, Map map) {
        //T t = baseDataDao.getData(map);
        baseDataDao = getBaseDataDao(t);
        return (T)baseDataDao.getData(map);
    }


    public List<String> getDeviceCode(String device_type){
        return baseDataDao.getDeviceCode(device_type);
    }


    public BaseDataDao getBaseDataDao(T t){
        if(t instanceof BodyData){
            return bodyDataDao;
        }else if(t instanceof BodyDataDetail){
            return bodyDataDetailDao;
        }else if(t instanceof FootData){
            return footDataDao;
        }else if(t instanceof FootDataDetail){
            return footDataDetailDao;
        }else if(t instanceof TableRecord){
            baseDataDao = tableRecordDao;
        }else if(t instanceof EHeadData){
            baseDataDao = eHeadDataDao;
        }else if(t instanceof EHeadDataResult){
            baseDataDao = eHeadDataResultDao;
        }else if(t instanceof ShoePadData){
            baseDataDao = shoePadDataDao;
        }else if(t instanceof UserInfo){
            baseDataDao = userInfoDao;
        }

        return baseDataDao;
    }

}
