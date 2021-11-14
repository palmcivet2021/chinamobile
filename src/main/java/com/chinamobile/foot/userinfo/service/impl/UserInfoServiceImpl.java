package com.chinamobile.foot.userinfo.service.impl;


import com.chinamobile.foot.userinfo.bean.UserInfo;
import com.chinamobile.foot.userinfo.dao.UserInfoDao;
import com.chinamobile.foot.userinfo.service.UserInfoService;
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
