package com.chinamobile.foot.body.controller;

import com.chinamobile.auth.annotation.JwtIgnore;
import com.chinamobile.foot.base.service.BaseDataService;
import com.chinamobile.foot.body.bean.BodyData;
import com.chinamobile.foot.body.bean.BodyDataDetail;
import com.chinamobile.foot.body.service.BodyDataService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "情感设备相关接口")
@RestController
@CrossOrigin
public class BodyDataController {
    @Autowired
    private BodyDataService bodyDataService;
    @Autowired
    private BaseDataService baseDataService;


    /**
     * 情感数据列表
     *
     * @param start_time
     * @param end_time
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/getBodyData")
    @JwtIgnore
    public PageInfo<BodyData> getBodyData(String start_time, String end_time, @RequestParam(name = "pageNum", defaultValue = "1") int pageNum, @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        Map map = new HashMap();
        map.put("start_time", start_time);
        map.put("end_time", end_time);
        //PageInfo<BodyData> result = bodyDataService.getBodyDataByPage(map, 1, 10);
        PageInfo<BodyData> result = baseDataService.getDataByPage(new BodyData(), map, pageNum, pageSize);

        //response.addHeader("Access-Control-Allow-Origin", "*");   //用于ajax post跨域（*，最好指定确定的http等协议+ip+端口号）
        //response.setCharacterEncoding("utf-8");

        return result;
    }

    /**
     * 单条情感数据
     *
     * @param body_id
     * @return
     */
    @RequestMapping("/getBodyDataDetail")
    @JwtIgnore
    public PageInfo<BodyDataDetail> getBodyDataDetail(Integer body_id) {
        Map map = new HashMap();
        map.put("body_id", body_id);
        //PageInfo<BodyData> result = bodyDataService.getBodyDataByPage(map, 1, 10);
        PageInfo<BodyDataDetail> result = baseDataService.getDataByPage(new BodyDataDetail(), map, 1, 10);

        //response.addHeader("Access-Control-Allow-Origin", "*");   //用于ajax post跨域（*，最好指定确定的http等协议+ip+端口号）
        //response.setCharacterEncoding("utf-8");

        return result;
    }
}
