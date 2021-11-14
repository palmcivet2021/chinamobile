package com.chinamobile.foot.userinfo.controller;

import com.chinamobile.foot.base.service.BaseDataService;
import com.chinamobile.foot.userinfo.bean.UserInfo;
import com.chinamobile.foot.userinfo.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private BaseDataService baseDataService;

    /**
     * 新增用户
     *
     * @param username
     * @param password
     */
    @RequestMapping("/addUser")
    public void addUser(String username, String password) {
        UserInfo userInfo = new UserInfo(username, password);
        //userInfoService.insertUser(userInfo);
        baseDataService.insertData(userInfo);
    }

    /**
     * 根据用户名获取用户信息
     *
     * @param username
     * @return
     */
    @RequestMapping("/getUser")
    public UserInfo getUser(String username) {
        Map map = new HashMap();
        map.put("username", username);

        return (UserInfo) baseDataService.getData(new UserInfo(), map);
    }


}
