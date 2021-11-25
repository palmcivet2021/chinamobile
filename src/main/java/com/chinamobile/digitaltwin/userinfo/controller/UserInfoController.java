package com.chinamobile.digitaltwin.userinfo.controller;

import com.chinamobile.digitaltwin.base.service.BaseDataService;
import com.chinamobile.digitaltwin.userinfo.bean.UserInfo;
import com.chinamobile.digitaltwin.userinfo.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "用户管理相关接口")
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
    @ApiOperation("新增用户")
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
    @ApiOperation("根据用户名获取用户信息")
    @RequestMapping("/getUser")
    public UserInfo getUser(String username) {
        Map map = new HashMap();
        map.put("username", username);

        return (UserInfo) baseDataService.getData(new UserInfo(), map);
    }
}
