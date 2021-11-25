package com.chinamobile.digitaltwin.userinfo.service.impl;


import com.chinamobile.digitaltwin.userinfo.bean.UserInfo;
import com.chinamobile.digitaltwin.userinfo.dao.UserInfoDao;
import com.chinamobile.digitaltwin.userinfo.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public void insertUser(UserInfo userInfo) {
        userInfoDao.insertUser(userInfo);
    }

}
