package com.chinamobile.digitaltwin.foot.controller;

import com.chinamobile.auth.annotation.JwtIgnore;
import com.chinamobile.digitaltwin.base.service.BaseDataService;
import com.chinamobile.digitaltwin.foot.bean.FootData;
import com.chinamobile.digitaltwin.foot.bean.FootDataDetail;
import com.chinamobile.digitaltwin.foot.service.FootDataService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 足型仪设备相关<br/>
 */
@Api(tags = "脚型数据接口")
@RestController
@CrossOrigin
public class FootDataController {
    @Autowired
    private FootDataService footDataService;
    @Autowired
    private BaseDataService baseDataService;

    /**
     * 全部脚型数据列表
     *
     * @return
     */
    @ApiOperation("全部脚型数据列表")
    @RequestMapping("/getFootData1")
    public List<FootData> getFootData1() {
        Map map = new HashMap();
        //List<FootData> result = footDataService.getFootData(map, 1, 10);
        List<FootData> result = baseDataService.getData(new FootData(), map, 1, 10);

        return result;
    }

    /**
     * 分页脚型数据列表
     *
     * @param start_time
     * @param end_time
     * @param pageNum
     * @param pageSize
     * @return
     */
    //@HystrixCommand(fallbackMethod="fallback")
    @ApiOperation(value = "获取分页脚型数据列表", notes = "分页脚型数据列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "start_time", value = "开始时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "end_time", value = "结束时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "pageNum", value = "页码"),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数")
    })
    @RequestMapping("/getFootData2")
    @JwtIgnore
    public PageInfo<FootData> getFootData2(String start_time, String end_time, @RequestParam(name = "pageNum", defaultValue = "1") int pageNum, @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        Map map = new HashMap();
        map.put("start_time", start_time);
        map.put("end_time", end_time);
        //PageInfo<FootData> result = footDataService.getFootDataByPage(map, 1, 10);
        PageInfo<FootData> result = baseDataService.getDataByPage(new FootData(), map, pageNum, pageSize);

        return result;
    }

    /**
     * 单条脚型详细数据
     *
     * @param foot_scan_id
     * @return
     */
    @ApiOperation(value = "获取单条脚型详细数据", notes = "根据foot_scan_id获取单条脚型详细数据")
    @RequestMapping("/getFootDataDetail")
    @JwtIgnore
    public FootDataDetail getFootDataDetail(Integer foot_scan_id) {
        Map map = new HashMap();
        map.put("foot_scan_id", foot_scan_id);
        FootDataDetail data = (FootDataDetail) baseDataService.getData(new FootDataDetail(), map);

        return data;
    }
}
