package com.chinamobile.foot.service.impl;

import com.chinamobile.foot.bean.UserInfo;
import com.chinamobile.foot.dao.UserInfoDao;
import com.chinamobile.foot.service.UserInfoService;
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
