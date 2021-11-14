package com.chinamobile.foot.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.chinamobile.foot.footdata.bean.FootData;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpUtil {

    private static OkHttpClient client;

    private HttpUtil(){

    }
    public static OkHttpClient getInstance() {
        if (client == null) {
            synchronized (HttpUtil.class) {
                if (client == null) {
                    client = new OkHttpClient();
                }
            }
        }
        return client;
    }

    public static String get(String url) {
        String result = null;
        Request request = new Request.Builder()
                .url(url)
                .addHeader("access_token","ab964d45eea731050bb632d624b7549c")
                .get()
                .build();
        Call call = HttpUtil.getInstance().newCall(request);
        Response response = null;
        try {
            response = call.execute();
            result = response.body().string();
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("发送GET失败...检查网络地址...");
        }

        return result;
    }


    public static String post(String url, String json)  {
        String result = null;
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody) //post请求
                .build();
        Call call = HttpUtil.getInstance().newCall(request);
        Response response = null;
        try {
            response = call.execute();
            //System.out.println(response.body().string());
            result = response.body().string();
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("发送失败...检查网络地址...");
        }

        return result;

    }




    public static void main(String[] args){
        String json = "access_token=ab964d45eea731050bb632d624b7549c&end_time=2021-4-1 23:59:59&page=1&perpage=10&scan_type=2&start_time=2019-3-26 00:00:00";
        Gson gson=new Gson();
        Map map=new LinkedHashMap();
        map.put("access_token","ab964d45eea731050bb632d624b7549c");
        map.put("end_time","2021-1-6 09:30:00");
        map.put("page","1");
        map.put("perpage","10");
        map.put("scan_type","2");
        map.put("start_time","2021-1-6 09:00:00");
        //用户中心>用户数据管理>用户数据列表
        String params=gson.toJson(map);
        String url="https://api.semsx.com/Admin/FootScan/getCompanyScanList";
        String data = post(url, params);

        //转换String
        JSONObject jsonObject = JSONObject.parseObject(data);
        String pretty = JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);

        System.out.println(pretty);

        int errno = jsonObject.getIntValue("errno");
        String errmsg = jsonObject.getString("errmsg");
        if(errno == 0 && errmsg.equals("success")){
            int count = jsonObject.getJSONObject("result").getIntValue("count");
            JSONArray jsonArray = jsonObject.getJSONObject("result").getJSONArray("data");
            for(int i=0; i<jsonArray.size(); i++){
                FootData footData = gson.fromJson(String.valueOf(jsonArray.getJSONObject(i)), FootData.class);
                System.out.println(footData.getFoot_scan_id());
            }
        }

        url="https://api.semsx.com/Admin/CompanyOrdinaryUser/getCompanyOrdinaryUserInfo?company_user_id=897";
        get(url);

        //用户中心>用户数据管理>脚型报告
        url="https://api.semsx.com/Admin/FootScan/getFootScanInfo";
        map.clear();
        map.put("access_token","ab964d45eea731050bb632d624b7549c");
        map.put("foot_scan_id","18760");
        //map.put("scan_type","2");
        params=gson.toJson(map);
        data = post(url, params);
        jsonObject = JSONObject.parseObject(data).getJSONObject("result").getJSONObject("foot_scan_json").getJSONObject("foot_presure_info");

        //压力百分比
        float left_foot_total_ad = jsonObject.getJSONObject("left_foot_ad").getFloat("foot_total_ad");
        float right_foot_total_ad = jsonObject.getJSONObject("right_foot_ad").getFloat("foot_total_ad");
        double tt = left_foot_total_ad/(left_foot_total_ad+right_foot_total_ad) * 100;
        int left_foot_percent = Math.round(left_foot_total_ad/(left_foot_total_ad+right_foot_total_ad) * 100);
        int right_foot_percent = Math.round(right_foot_total_ad/(left_foot_total_ad+right_foot_total_ad) * 100);

        //System.out.println("ad="+left_foot_ad+",left_foot_bd="+left_foot_bd+",left_foot_cd="+left_foot_cd+",left_foot_percent="+left_foot_percent+"%,right_foot_percent="+right_foot_percent+"%");
        System.out.println("压力百分比左足：left_foot_percent="+left_foot_percent+"%,压力百分比左足：right_foot_percent="+right_foot_percent+"%");

        //足弓指标
        float left_foot_ad = jsonObject.getJSONObject("left_foot_ad").getFloat("a_ad");
        float left_foot_bd = jsonObject.getJSONObject("left_foot_ad").getFloat("b_ad");
        float left_foot_cd = jsonObject.getJSONObject("left_foot_ad").getFloat("c_ad");
        float right_foot_ad = jsonObject.getJSONObject("right_foot_ad").getFloat("a_ad");
        float right_foot_bd = jsonObject.getJSONObject("right_foot_ad").getFloat("b_ad");
        float right_foot_cd = jsonObject.getJSONObject("right_foot_ad").getFloat("c_ad");

        float d = Math.round(left_foot_bd/(left_foot_ad+left_foot_bd+left_foot_cd) * 100);
        double dd = d /100;
        float leftArchNum = (float)(Math.round(left_foot_bd/(left_foot_ad+left_foot_bd+left_foot_cd) * 100)) / 100;
        float rightArchNum = (float)(Math.round(right_foot_bd/(right_foot_ad+right_foot_bd+right_foot_cd) * 100)) / 100;

        System.out.println("足弓左足：leftArchNum="+leftArchNum+",足弓右足：rightArchNum="+rightArchNum);

        //压力六区分布
        int leftA = Math.round(left_foot_ad/(left_foot_ad+left_foot_bd+left_foot_cd) * 100);
        int leftB = Math.round(left_foot_bd/(left_foot_ad+left_foot_bd+left_foot_cd) * 100);
        int leftC = Math.round(left_foot_cd/(left_foot_ad+left_foot_bd+left_foot_cd) * 100);
        int rightA = Math.round(right_foot_ad/(right_foot_ad+right_foot_bd+right_foot_cd) * 100);
        int rightB = Math.round(right_foot_bd/(right_foot_ad+right_foot_bd+right_foot_cd) * 100);
        int rightC = Math.round(right_foot_cd/(right_foot_ad+right_foot_bd+right_foot_cd) * 100);
        System.out.println("压力六区左足："+leftA+"%,"+leftB+"%,"+leftC+"%,压力六区右足："+rightA+"%,"+rightB+"%,"+rightC+"%");

        jsonObject = JSONObject.parseObject(data).getJSONObject("result").getJSONObject("foot_scan_json").getJSONObject("result_info");
        String leftArch = jsonObject.getJSONObject("arch_info").getString("left");
        String rightArch = jsonObject.getJSONObject("arch_info").getString("right");
        String leftSize = jsonObject.getJSONObject("design_shoes_size").getString("left");
        String rightSize = jsonObject.getJSONObject("design_shoes_size").getString("right");

        System.out.println("leftArch="+leftArch+",rightArch="+rightArch+",leftSize="+leftSize+",rightSize="+rightSize);

        url="https://api.semsx.com/Common/StoreFile/getFileContent/?file_id=148261";
        data = get(url);
        //System.out.println("pic="+data);



        //压力数据测试
        gson=new Gson();
        map.clear();
        map.put("start_time","2021-07-29 18:16:00");
        map.put("end_time","2021-07-29 18:18:00");
        map.put("timestamp","1627553973521");
        map.put("device_code","EC:B9:77:13:3E:7E");  //   D0:25:58:6F:F5:AE

        //map.put("timestamp",String.valueOf(System.currentTimeMillis()));
        //map.put("sign","67c23f50f9b41f36fda4c18a886a3c27ee6b948ca758e19a989d996f57791fc1");
        Sha256Utils.getSHA256StrJava(map);


        //用户中心>用户数据管理>用户数据列表
        params=gson.toJson(map);
        url="https://www.aicaring.com/test/ring/external_cooperation/health/data/get/";
        data = post(url, params);

        jsonObject = JSONObject.parseObject(data);
        pretty = JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);

        System.out.println(pretty);

        JSONArray pressure_data_list = jsonObject.getJSONObject("data").getJSONObject("health_data").getJSONObject("pressure_data").getJSONArray("list");
        JSONArray anxiety_data_list = jsonObject.getJSONObject("data").getJSONObject("health_data").getJSONObject("anxiety_data").getJSONArray("list");
        JSONArray depression_data_list = jsonObject.getJSONObject("data").getJSONObject("health_data").getJSONObject("depression_data").getJSONArray("list");
        JSONArray blood_pressure_emo_data_list = jsonObject.getJSONObject("data").getJSONObject("blood_pressure_emo").getJSONArray("list");


        for (int i=0; i<blood_pressure_emo_data_list.size(); i++) {
            int hr = blood_pressure_emo_data_list.getJSONObject(i).getIntValue("hr");
            BigDecimal[] emo_lists = blood_pressure_emo_data_list.getJSONObject(i).getJSONArray("emo_list").toArray(new BigDecimal[]{});
            String emo_list = Arrays.toString(blood_pressure_emo_data_list.getJSONObject(i).getJSONArray("emo_list").toArray(new BigDecimal[]{}));
            System.out.println("####emo_list="+emo_list);
            if(emo_list.startsWith("[") && emo_list.endsWith("]")){
                emo_list = emo_list.substring(emo_list.indexOf("[")+1, emo_list.lastIndexOf("]"));
                System.out.println("####emo_list="+emo_list);
            }
        }

    }
}
