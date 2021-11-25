package com.chinamobile.foot.shoepad.controller;

import com.chinamobile.auth.annotation.JwtIgnore;
import com.chinamobile.foot.base.service.BaseDataService;
import com.chinamobile.foot.shoepad.bean.ShoePadData;
import com.chinamobile.foot.shoepad.bean.ShoePadData2;
import com.chinamobile.foot.shoepad.bean.ShoePadData3;
import com.chinamobile.foot.shoepad.service.ShoePadService;
import com.chinamobile.foot.util.StringUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 鞋垫压力相关<br/>
 */
@Api(tags = "智能鞋垫相关接口")
@RestController
@CrossOrigin
public class ShoePadDataController {
    @Autowired
    private BaseDataService baseDataService;
    @Autowired
    private ShoePadService shoePadService;

    /**
     * 鞋垫压力数据列表
     *
     * @param start_time
     * @param end_time
     * @return
     */
    @ApiOperation("鞋垫压力数据列表")
    @RequestMapping("/getShoePadData")
    @JwtIgnore
    public PageInfo<ShoePadData> getShoePadData(String start_time, String end_time, @RequestParam(name = "pageNum", defaultValue = "1") int pageNum, @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        Map map = new HashMap();
        map.put("start_time", start_time);
        map.put("end_time", end_time);
        //PageInfo<BodyData> result = bodyDataService.getBodyDataByPage(map, 1, 10);
        PageInfo<ShoePadData> result = baseDataService.getDataByPage(new ShoePadData(), map, pageNum, pageSize);
        return result;
    }


    /**
     * 鞋垫压力数据列表
     *
     * @param start_time
     * @param end_time
     * @return
     */
    @ApiOperation("鞋垫压力数据列表2")
    @RequestMapping("/getShoePadData2")
    @JwtIgnore
    public ShoePadData2 getShoePadData2(String start_time, String end_time) {
        Map map = new HashMap();
        map.put("start_time", start_time);
        map.put("end_time", end_time);

        ShoePadData2 shoePadData2 = shoePadService.getShoePadData(map);
        ShoePadData3 shoePadData3 = new ShoePadData3();

        if (shoePadData2 == null) {
            shoePadData2 = new ShoePadData2();
        }
        return shoePadData2;
    }


    /**
     * 鞋垫压力数据列表
     *
     * @param start_time
     * @param end_time
     * @return
     */
    @ApiOperation("鞋垫压力数据列表3")
    @RequestMapping("/getShoePadData3")
    @JwtIgnore
    public ShoePadData3 getShoePadData3(String start_time, String end_time) {
        Map map = new HashMap();
        map.put("start_time", start_time);
        map.put("end_time", end_time);

        ShoePadData2 shoePadData2 = shoePadService.getShoePadData(map);
        ShoePadData3 shoePadData3 = new ShoePadData3();

        if (shoePadData2 != null) {
            String delimiter = ",";
            shoePadData3.setId(StringUtil.getIntArrayFromString(shoePadData2.getId(), delimiter));
            shoePadData3.setUserid(StringUtil.getIntArrayFromString(shoePadData2.getUserid(), delimiter));
            shoePadData3.setAd1(StringUtil.getIntArrayFromString(shoePadData2.getAd1(), delimiter));
            shoePadData3.setAd2(StringUtil.getIntArrayFromString(shoePadData2.getAd2(), delimiter));
            shoePadData3.setAd3(StringUtil.getIntArrayFromString(shoePadData2.getAd3(), delimiter));
            shoePadData3.setAd4(StringUtil.getIntArrayFromString(shoePadData2.getAd4(), delimiter));
            shoePadData3.setAd5(StringUtil.getIntArrayFromString(shoePadData2.getAd5(), delimiter));
            shoePadData3.setAd6(StringUtil.getIntArrayFromString(shoePadData2.getAd6(), delimiter));
            shoePadData3.setAd7(StringUtil.getIntArrayFromString(shoePadData2.getAd7(), delimiter));
            shoePadData3.setAd8(StringUtil.getIntArrayFromString(shoePadData2.getAd8(), delimiter));

            shoePadData3.setAcceleration_x(StringUtil.getFloatArrayFromString(shoePadData2.getAcceleration_x(), delimiter));
            shoePadData3.setAcceleration_y(StringUtil.getFloatArrayFromString(shoePadData2.getAcceleration_y(), delimiter));
            shoePadData3.setAcceleration_z(StringUtil.getFloatArrayFromString(shoePadData2.getAcceleration_z(), delimiter));

            shoePadData3.setAngular_speed_x(StringUtil.getFloatArrayFromString(shoePadData2.getAngular_speed_x(), delimiter));
            shoePadData3.setAngular_speed_y(StringUtil.getFloatArrayFromString(shoePadData2.getAngular_speed_y(), delimiter));
            shoePadData3.setAngular_speed_z(StringUtil.getFloatArrayFromString(shoePadData2.getAngular_speed_z(), delimiter));

            shoePadData3.setAngle_x(StringUtil.getFloatArrayFromString(shoePadData2.getAngle_x(), delimiter));
            shoePadData3.setAngle_y(StringUtil.getFloatArrayFromString(shoePadData2.getAngle_y(), delimiter));
            shoePadData3.setAngle_z(StringUtil.getFloatArrayFromString(shoePadData2.getAngle_z(), delimiter));

            shoePadData3.setMagnetic_strength_x(StringUtil.getFloatArrayFromString(shoePadData2.getMagnetic_strength_x(), delimiter));
            shoePadData3.setMagnetic_strength_y(StringUtil.getFloatArrayFromString(shoePadData2.getMagnetic_strength_y(), delimiter));
            shoePadData3.setMagnetic_strength_z(StringUtil.getFloatArrayFromString(shoePadData2.getMagnetic_strength_z(), delimiter));

            shoePadData3.setShow_time(shoePadData2.getShow_time().split(delimiter));

        }
        return shoePadData3;
    }
}
