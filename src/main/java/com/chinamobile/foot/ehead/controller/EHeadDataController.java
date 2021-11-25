package com.chinamobile.foot.ehead.controller;

import com.chinamobile.auth.annotation.JwtIgnore;
import com.chinamobile.foot.base.service.BaseDataService;
import com.chinamobile.foot.ehead.obtain.EHeadTrainData;
import com.chinamobile.foot.ehead.obtain.EheadServer;
import com.chinamobile.foot.ehead.bean.EHeadData;
import com.chinamobile.foot.ehead.bean.EHeadDataResult;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "脑机设备相关接口")
@RestController
@CrossOrigin
public class EHeadDataController {
    @Autowired
    private BaseDataService baseDataService;

    @ApiOperation("获取头部数据")
    @RequestMapping("/getEHeadData")
    public PageInfo<EHeadData> getEHeadData(String start_time, String end_time) {
        Map map = new HashMap();
        map.put("start_time", start_time);
        map.put("end_time", end_time);
        //PageInfo<BodyData> result = bodyDataService.getBodyDataByPage(map, 1, 10);
        PageInfo<EHeadData> result = baseDataService.getDataByPage(new EHeadData(), map, 1, 10);

        //response.addHeader("Access-Control-Allow-Origin", "*");   //用于ajax post跨域（*，最好指定确定的http等协议+ip+端口号）
        //response.setCharacterEncoding("utf-8");

        return result;
    }

    @ApiOperation("获取训练数据")
    @RequestMapping("/getTrainData")
    public int getTrainData() {
        int i = 0;
        try {
            i = EHeadTrainData.getTrainData();
            System.out.println("####getTrainData.i=" + i);
            EHeadTrainData.imap.put(i % 4, EHeadTrainData.imap.get(i % 4) + 1);
            System.out.println(EHeadTrainData.imap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return i;
    }

    @ApiOperation("获取头部结果")
    @RequestMapping("/getEheadResult")
    @JwtIgnore
    public EHeadDataResult getEheadResult() {
        EHeadDataResult eHeadDataResult = (EHeadDataResult) baseDataService.getData(new EHeadDataResult(), null);
        return eHeadDataResult;
    }


    @RequestMapping("/startEhead")
    @JwtIgnore
    public void startEhead() {
        //EheadServer.setStart(true);
        EheadServer.isStart = true;
    }

    @RequestMapping("/endEhead")
    @JwtIgnore
    public void endEhead() {
        //EheadServer.setStart(false);
        EheadServer.isStart = false;
    }
}
