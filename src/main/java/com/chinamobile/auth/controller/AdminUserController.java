package com.chinamobile.auth.controller;

import com.alibaba.fastjson.JSONObject;
import com.chinamobile.auth.annotation.JwtIgnore;
import com.chinamobile.auth.bean.Audience;
import com.chinamobile.auth.util.JwtTokenUtil;
import com.chinamobile.auth.util.ResultSet;
import com.chinamobile.foot.bean.UserInfo;
import com.chinamobile.foot.service.BaseDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * ========================
 * Created with IntelliJ IDEA.
 * User：pyy
 * Date：2019/7/18 10:41
 * Version: v1.0
 * ========================
 */
@Slf4j
@RestController
public class AdminUserController {

    @Autowired
    private Audience audience;
    @Autowired
    private BaseDataService baseDataService;

    private String token = null;

    @RequestMapping("/hello")
    public String hello(){
        return "Future Auth Hello!";
    }

    @RequestMapping("/login")
    @JwtIgnore
    public ResultSet adminLogin(HttpServletResponse response, String username, String password) {
        // 这里模拟测试, 默认登录成功，返回用户ID和角色信息
        Assert.notNull(username, "用户名不能为空");
        Assert.notNull(password, "密码不能为空");

        Map map = new HashMap();
        map.put("username", username);
        map.put("password", password);
        UserInfo user = (UserInfo) baseDataService.getData(new UserInfo(), map);
        Assert.notNull(user, "用户名或密码错");

        //String userId = UUID.randomUUID().toString();
        String userId = user.getId().toString();
        String role = "admin";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", user.getUsername());
        jsonObject.put("sex", "男");
        String input = JSONObject.toJSONString(jsonObject);
        // 创建token
        //String token = JwtTokenUtil.createJWT(userId, username, role, audience);
        //String token = JwtTokenUtil.createJWT(userId, input, role, audience);
        token = JwtTokenUtil.createJWT(userId, input, role, audience);
        if (token == null) {
            log.error("===== 用户签名失败 =====");
            return null;
        }
        log.info("### 登录成功, token={} ###", token);

        // 将token放在响应头
        response.setHeader(JwtTokenUtil.AUTH_HEADER_KEY, JwtTokenUtil.TOKEN_PREFIX + token);
        // 将token响应给客户端
        JSONObject result = new JSONObject();
        //result.put("token", token);
        result.put("token", JwtTokenUtil.getAuthorizationHeader(token));

        return ResultSet.success(result);
        //return result;
    }


    @RequestMapping("/logout")
    @JwtIgnore
    public ResultSet logout(HttpServletRequest request) throws Exception {
        String authHeader = request.getHeader(JwtTokenUtil.AUTH_HEADER_KEY);
        String token = authHeader.substring(JwtTokenUtil.TOKEN_PREFIX.length());
        boolean expiration = JwtTokenUtil.isExpiration(token, audience.getSecret());

        return ResultSet.success();
    }


    @RequestMapping("/users")
    public ResultSet userList(HttpServletRequest request, HttpServletResponse response) {
        log.info("### 查询所有用户列表 ###");
        //String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiYWRtaW4iLCJ1c2VySWQiOiJjMzc2MjU1Ni00ZGQ1LTRjNzctODI5Mi1kZDJiMDkzMmNmNzciLCJzdWIiOiJ7XCJzZXhcIjpcIueUt1wiLFwibmFtZVwiOlwiemhhbmdzYW5cIixcImFnZVwiOjIwfSIsImlhdCI6MTU4OTI4MTk4NywiZXhwIjoxNTg5MjgyNTg3LCJuYmYiOjE1ODkyODE5ODd9.U8pmjCuL8DbufjAVt98oJ4AOJfCZ2eNKzkR2ci0v0No";
        response.setHeader(JwtTokenUtil.AUTH_HEADER_KEY, JwtTokenUtil.TOKEN_PREFIX + token);
        return ResultSet.success();
    }

    @RequestMapping("/list")
    @JwtIgnore
    public ResultSet list(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("access_token");
        //boolean isExpiration = JwtTokenUtil.isExpiration(token, audience.getSecret());
        //String username = JwtTokenUtil.getUsername(token, audience.getSecret());


        return ResultSet.success();
    }

}
