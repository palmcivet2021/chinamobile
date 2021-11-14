package com.chinamobile.foot.userinfo.dao;

import com.chinamobile.foot.base.dao.BaseDataDao;
import com.chinamobile.foot.userinfo.bean.UserInfo;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface UserInfoDao<T> extends BaseDataDao<T> {

    void insertUser(UserInfo user);

    void insertData(UserInfo user);

    void updateData(UserInfo user);

    UserInfo getData(Map map);
}
