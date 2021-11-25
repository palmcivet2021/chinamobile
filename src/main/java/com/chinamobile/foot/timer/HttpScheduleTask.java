package com.chinamobile.foot.timer;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chinamobile.foot.body.bean.BodyData;
import com.chinamobile.foot.body.service.BodyDataService;
import com.chinamobile.foot.footdata.bean.FootData;
import com.chinamobile.foot.footdata.service.FootDataService;
import com.chinamobile.foot.util.HttpUtil;
import com.chinamobile.foot.util.Sha256Utils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class HttpScheduleTask {
    @Autowired
    private FootDataService footDataService;
    @Autowired
    private BodyDataService bodyDataService;

    public void schedule() {
        //ScheduledThreadPoolExecutor pool = new ScheduledThreadPoolExecutor(3);
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(3);
        pool.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("表示延迟1秒后每3秒执行一次。");
            }
        }, 3, TimeUnit.SECONDS);
    }

    /**
     * 获取脚型数据，凌晨0:00
     */
    @Scheduled(cron = "0 05 12 * * ?")
    private void getFootData() {
        System.out.println("+++++getFootData() begin");
        int page = 1;  //初始页
        int perpage = 20;  //每页条数，默认20

        //String json = "access_token=ab964d45eea731050bb632d624b7549c&end_time=2021-4-1 23:59:59&page=1&perpage=10&scan_type=2&start_time=2019-3-26 00:00:00";
        Map map = new LinkedHashMap();
        map.put("access_token", "ab964d45eea731050bb632d624b7549c");
        map.put("end_time", "2021-4-1 23:59:59");
        map.put("page", page);
        map.put("perpage", perpage);
        map.put("scan_type", "2");
        map.put("start_time", "2019-3-26 00:00:00");

        /*
        //用户中心>用户数据管理>用户数据列表
        Gson gson=new Gson();
        String params=gson.toJson(map);
        String url="https://api.semsx.com/Admin/FootScan/getCompanyScanList";
        String data = HttpUtil.post(url, params);
        System.out.println("+++data="+data);

        //转换String
        JSONObject jsonObject = JSONObject.parseObject(data);
        int errno = jsonObject.getIntValue("errno");
        String errmsg = jsonObject.getString("errmsg");
        if(errno == 0 && errmsg.equals("success")){
            int count = jsonObject.getJSONObject("result").getIntValue("count");
            JSONArray jsonArray = jsonObject.getJSONObject("result").getJSONArray("data");
            for(int i=0; i<jsonArray.size(); i++){
                FootData userData = gson.fromJson(String.valueOf(jsonArray.getJSONObject(i)), FootData.class);
                //System.out.println(userData.getFoot_scan_id());
                footDataService.insertUserData(userData);
            }
        }
        */

        int count = insertFootData(map);
        int maxPage = count % perpage == 0 ? count / perpage : count / perpage + 1;
        while (page < maxPage) {
            page++;
            map.put("page", page);
            insertFootData(map);
        }

    }

    //返回总条数
    private int insertFootData(Map map) {
        int count = 0;

        Gson gson = new Gson();
        String params = gson.toJson(map);
        String url = "https://api.semsx.com/Admin/FootScan/getCompanyScanList";
        String data = HttpUtil.post(url, params);

        JSONObject jsonObject = JSONObject.parseObject(data);
        int errno = jsonObject.getIntValue("errno");
        String errmsg = jsonObject.getString("errmsg");
        if (errno == 0 && errmsg.equals("success")) {
            count = jsonObject.getJSONObject("result").getIntValue("count");
            JSONArray jsonArray = jsonObject.getJSONObject("result").getJSONArray("data");
            for (int i = 0; i < jsonArray.size(); i++) {
                FootData footData = gson.fromJson(String.valueOf(jsonArray.getJSONObject(i)), FootData.class);
                //System.out.println(footData.getFoot_scan_id());
                footDataService.insertFootData(footData);
            }
        }

        return count;
    }


    //
    @Scheduled(cron = "0 27 23 * * ?")
    private void insertBodyData() {
        Gson gson = new Gson();
        Map<String, String> map = new LinkedHashMap();
        map.put("start_time", "2021-04-20 18:05:00");
        map.put("end_time", "2021-04-20 18:07:00");
        map.put("device_code", "EC:B9:77:13:3E:7E");
        //map.put("timestamp","12345");
        map.put("timestamp", String.valueOf(System.currentTimeMillis()));
        //map.put("sign","67c23f50f9b41f36fda4c18a886a3c27ee6b948ca758e19a989d996f57791fc1");
        Sha256Utils.getSHA256StrJava(map);

        String params = gson.toJson(map);
        String url = "https://www.aicaring.com/test/ring/external_cooperation/health/data/get/";
        String data = HttpUtil.post(url, params);

        JSONObject jsonObject = JSONObject.parseObject(data);
        boolean status = jsonObject.getBoolean("status");
        if (status) {
            int positive_per = jsonObject.getJSONObject("data").getJSONObject("emo_per").getIntValue("positive_per");
            int negative_per = jsonObject.getJSONObject("data").getJSONObject("emo_per").getIntValue("negative_per");
            int neutral_per = jsonObject.getJSONObject("data").getJSONObject("emo_per").getIntValue("neutral_per");

            double pressure_data = jsonObject.getJSONObject("data").getJSONObject("health_data").getJSONObject("pressure_data").getJSONObject("result_analysis").getDouble("data");
            String pressure_status = jsonObject.getJSONObject("data").getJSONObject("health_data").getJSONObject("pressure_data").getJSONObject("result_analysis").getString("status");
            String pressure_describe = jsonObject.getJSONObject("data").getJSONObject("health_data").getJSONObject("pressure_data").getJSONObject("result_analysis").getString("describe");

            double anxiety_data = jsonObject.getJSONObject("data").getJSONObject("health_data").getJSONObject("anxiety_data").getJSONObject("result_analysis").getDouble("data");
            String anxiety_status = jsonObject.getJSONObject("data").getJSONObject("health_data").getJSONObject("anxiety_data").getJSONObject("result_analysis").getString("status");

            double depression_data = jsonObject.getJSONObject("data").getJSONObject("health_data").getJSONObject("depression_data").getJSONObject("result_analysis").getDouble("data");
            String depression_status = jsonObject.getJSONObject("data").getJSONObject("health_data").getJSONObject("depression_data").getJSONObject("result_analysis").getString("status");

            double blood_pressure_emo_data = jsonObject.getJSONObject("data").getJSONObject("blood_pressure_emo").getJSONObject("result_analysis").getDouble("data");
            String blood_pressure_emo_status = jsonObject.getJSONObject("data").getJSONObject("blood_pressure_emo").getJSONObject("result_analysis").getString("status");
            String blood_pressure_emo_describe = jsonObject.getJSONObject("data").getJSONObject("blood_pressure_emo").getJSONObject("result_analysis").getString("describe");

            BodyData bodyData = new BodyData();
            bodyData.setPositive_per(positive_per);
            bodyData.setNegative_per(negative_per);
            bodyData.setNeutral_per(neutral_per);

            bodyData.setPressure_data(pressure_data);
            bodyData.setPressure_status(pressure_status);
            bodyData.setPressure_describe(pressure_describe);

            bodyData.setAnxiety_data(anxiety_data);
            bodyData.setAnxiety_status(anxiety_status);

            bodyData.setDepression_data(depression_data);
            bodyData.setDepression_status(depression_status);

            bodyData.setBlood_pressure_emo_data(blood_pressure_emo_data);
            bodyData.setBlood_pressure_emo_status(blood_pressure_emo_status);
            bodyData.setBlood_pressure_emo_describe(blood_pressure_emo_describe);

            bodyData.setDevice_code(map.get("device_code"));
            bodyData.setStart_time(map.get("start_time"));
            bodyData.setEnd_time(map.get("end_time"));

            bodyDataService.insertBodyData(bodyData);
        }
    }
}
