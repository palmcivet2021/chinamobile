package com.chinamobile.foot.dao;

import com.chinamobile.foot.bean.UserInfo;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface UserInfoDao<T> extends BaseDataDao<T> {

    void insertUser(UserInfo user);

    void insertData(UserInfo user);

    void updateData(UserInfo user);

    UserInfo getData(Map map);
}
