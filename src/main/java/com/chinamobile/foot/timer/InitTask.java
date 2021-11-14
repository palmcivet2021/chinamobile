package com.chinamobile.foot.timer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chinamobile.foot.base.service.BaseDataService;
import com.chinamobile.foot.body.bean.BodyData;
import com.chinamobile.foot.body.bean.BodyDataDetail;
import com.chinamobile.foot.body.service.BodyDataService;
import com.chinamobile.foot.ehead.obtain.EheadServer;
import com.chinamobile.foot.footdata.bean.FootData;
import com.chinamobile.foot.footdata.bean.FootDataDetail;
import com.chinamobile.foot.footdata.service.FootDataService;
import com.chinamobile.foot.shoepad.obtain.ShoePadServer;
import com.chinamobile.foot.tablerecord.bean.TableRecord;
import com.chinamobile.foot.util.HttpUtil;
import com.chinamobile.foot.util.Sha256Utils;
import com.chinamobile.foot.util.ThreadPoolUtil;
import com.github.pagehelper.util.StringUtil;
import com.google.gson.Gson;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class InitTask implements ApplicationRunner {
    @Autowired
    private BaseDataService baseDataService;
    @Autowired
    private FootDataService footDataService;
    @Autowired
    private BodyDataService bodyDataService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("通过实现ApplicationRunner接口，在spring boot项目启动时执行");
        String[] sourceArgs = args.getSourceArgs();
        for (String arg : sourceArgs) {
            System.out.print(arg + " ");
        }
        System.out.println();

        ExecutorService threadPool = ThreadPoolUtil.getIntance();
        threadPool.submit(new Runnable() {
            @Override
            public void run() {
                //启动接收脑电数据线程
                EheadServer.getEHeadData();
            }
        });


        threadPool.submit(new Runnable() {
            @Override
            public void run() {
                //启动接收智能鞋垫数据线程
                //SerialServer.getSerialPortData();
                ShoePadServer.getShoePadData();
            }
        });


        //Map map = new HashMap();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();

        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(2);

        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                int period = 2; //2 minutes
                System.out.println("############延迟1分钟后每10分钟获取体征数据一次#########");
                String start_time = "2021-04-20 18:05:00";
                Map map = new HashMap();
                map.put("table_name", "tb_body_data");
                TableRecord record = (TableRecord)baseDataService.getData(new TableRecord(), map);
                if(record == null){
                    record = new TableRecord();
                    record.setTable_name("tb_body_data");
                }
                if(record.getEnd_time() != null){  //record != null && record.getEnd_time() != null
                    start_time = sdf.format(record.getEnd_time());
                }


                //Date end = new Date();
                //cal.setTime(end);
                //cal.set(Calendar.SECOND, 0);
                ////cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) - period);
                //String end_time = sdf.format(cal.getTime());

                try {
                    Date start = sdf.parse(start_time);
                    Date end = new Date();
                    long minutes = (end.getTime() - start.getTime()) / (60 *1000);

                    for(int i=0; i<minutes/period; i++){
                        end = DateUtils.addMinutes(start, period);
                        insertBodyData(sdf.format(start), sdf.format(end), record);
                        start = end;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }



                //insertBodyData(start_time, end_time, record);
            }
        }, 100, 10, TimeUnit.MINUTES);

        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                int period = 10; //10 minutes
                System.out.println("***********延迟1分钟后每10分钟获取脚型数据一次**********");

                String start_time = "2019-1-1 00:00:00";
                Map map = new HashMap();
                map.put("table_name", "tb_foot_data");
                TableRecord record = (TableRecord)baseDataService.getData(new TableRecord(), map);
                if(record == null){
                    record = new TableRecord();
                    record.setTable_name("tb_foot_data");
                }
                if(record.getEnd_time() != null){
                    start_time = sdf.format(record.getEnd_time());
                }

                cal.setTime(new Date());
                cal.set(Calendar.SECOND, 0);
                //cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) - period);
                String end_time = sdf.format(cal.getTime());

                insertFootData(start_time, end_time, record);
            }
        }, 100, 10, TimeUnit.MINUTES);


    }


    /**
     * 获取脚型数据
     */
    private void insertFootData(String start_time, String end_time, TableRecord tableRecord) {
        System.out.println("+++++%%%%%%%%%%++++++insertFootData() begin,date="+new Date());
        int page = 1;  //初始页
        int perpage = 20;  //每页条数，默认20

        //String json = "access_token=ab964d45eea731050bb632d624b7549c&end_time=2021-4-1 23:59:59&page=1&perpage=10&scan_type=2&start_time=2019-3-26 00:00:00";
        Map map=new LinkedHashMap();
        map.put("access_token","ab964d45eea731050bb632d624b7549c");
        map.put("end_time",end_time==null?"2021-4-1 23:59:59":end_time);
        map.put("page",page);
        map.put("perpage",perpage);
        map.put("scan_type","2");
        map.put("start_time",start_time==null?"2019-3-26 00:00:00":start_time);

        List<FootData> list = new ArrayList();

        int count = insertFootData(map, list);
        int maxPage = count % perpage == 0 ? count / perpage : count / perpage + 1;
        while(page < maxPage){
            page++;
            map.put("page",page);
            insertFootData(map, list);
        }

        //批量插入
        //footDataService.insertBatchData(list);
        //更新调用时间
        /*if(tableRecord == null) {
            insertTableRecord("tb_foot_data", map);
        }else {
            //更新调用时间
            System.out.println("#######insertFootData.table_name="+tableRecord.getTable_name()+",id="+tableRecord.getId()+",start_time="+tableRecord.getStart_time()+",end_time="+tableRecord.getEnd_time());
            updateTableRecord(tableRecord, map);
        }*/
        updateTableRecord(tableRecord, map);
    }


    //返回总条数
    private int insertFootData(Map map, List<FootData> list){
        int count = 0;

        Gson gson=new Gson();
        String params=gson.toJson(map);
        String url="https://api.semsx.com/Admin/FootScan/getCompanyScanList";
        String data = HttpUtil.post(url, params);

        JSONObject jsonObject = JSONObject.parseObject(data);
        int errno = jsonObject.getIntValue("errno");
        String errmsg = jsonObject.getString("errmsg");
        if(errno == 0 && errmsg.equals("success")){
            count = jsonObject.getJSONObject("result").getIntValue("count");
            JSONArray jsonArray = jsonObject.getJSONObject("result").getJSONArray("data");
            for(int i=0; i<jsonArray.size(); i++){
                FootData footData = gson.fromJson(String.valueOf(jsonArray.getJSONObject(i)), FootData.class);
                //System.out.println(footData.getFoot_scan_id());
                //footDataService.insertFootData(footData);
                FootDataDetail footDataDetail = getFootDataDetail(footData.getFoot_scan_id());
                footDataDetail.setLeft_length(footData.getLeft_length());
                footDataDetail.setLeft_width(footData.getLeft_width());
                footDataDetail.setLeft_height(footData.getLeft_height());
                footDataDetail.setRight_length(footData.getRight_length());
                footDataDetail.setRight_width(footData.getRight_width());
                footDataDetail.setRight_height(footData.getRight_height());

                baseDataService.insertData(footData);
                baseDataService.insertData(footDataDetail);

                list.add(footData);
            }
        }

        return count;
    }


    /**
     * 获取详细脚型数据
     */
    private FootDataDetail getFootDataDetail(Integer foot_scan_id){
        Map<String, String> map=new LinkedHashMap();
        map.put("access_token","ab964d45eea731050bb632d624b7549c");
        map.put("foot_scan_id",String.valueOf(foot_scan_id));

        Gson gson=new Gson();
        String params=gson.toJson(map);
        String url="https://api.semsx.com/Admin/FootScan/getFootScanInfo";
        String data = HttpUtil.post(url, params);
        JSONObject jsonObject = JSONObject.parseObject(data).getJSONObject("result").getJSONObject("foot_scan_json").getJSONObject("foot_presure_info");

        FootDataDetail footDataDetail = new FootDataDetail();
        footDataDetail.setFoot_scan_id(foot_scan_id); //主键

        //压力百分比
        float left_foot_total_ad = jsonObject.getJSONObject("left_foot_ad").getFloat("foot_total_ad");
        float right_foot_total_ad = jsonObject.getJSONObject("right_foot_ad").getFloat("foot_total_ad");
        double tt = left_foot_total_ad/(left_foot_total_ad+right_foot_total_ad) * 100;
        int foot_presure_left_percent = Math.round(left_foot_total_ad/(left_foot_total_ad+right_foot_total_ad) * 100);
        int foot_presure_right_percent = Math.round(right_foot_total_ad/(left_foot_total_ad+right_foot_total_ad) * 100);

        footDataDetail.setFoot_presure_left(left_foot_total_ad);
        footDataDetail.setFoot_presure_right(right_foot_total_ad);
        footDataDetail.setFoot_presure_left_percent(foot_presure_left_percent);
        footDataDetail.setFoot_presure_right_percent(foot_presure_right_percent);

        //足弓指标
        float left_foot_ad = jsonObject.getJSONObject("left_foot_ad").getFloat("a_ad");
        float left_foot_bd = jsonObject.getJSONObject("left_foot_ad").getFloat("b_ad");
        float left_foot_cd = jsonObject.getJSONObject("left_foot_ad").getFloat("c_ad");
        float right_foot_ad = jsonObject.getJSONObject("right_foot_ad").getFloat("a_ad");
        float right_foot_bd = jsonObject.getJSONObject("right_foot_ad").getFloat("b_ad");
        float right_foot_cd = jsonObject.getJSONObject("right_foot_ad").getFloat("c_ad");

        float d = Math.round(left_foot_bd/(left_foot_ad+left_foot_bd+left_foot_cd) * 100);
        double dd = d /100;
        float arch_index_left = (float)(Math.round(left_foot_bd/(left_foot_ad+left_foot_bd+left_foot_cd) * 100)) / 100;
        float arch_index_right = (float)(Math.round(right_foot_bd/(right_foot_ad+right_foot_bd+right_foot_cd) * 100)) / 100;

        footDataDetail.setArch_index_left(arch_index_left);
        footDataDetail.setArch_index_right(arch_index_right);

        //压力六区分布
        int pressure_area_left_ad = Math.round(left_foot_ad/(left_foot_ad+left_foot_bd+left_foot_cd) * 100);
        int pressure_area_left_bd = Math.round(left_foot_bd/(left_foot_ad+left_foot_bd+left_foot_cd) * 100);
        int pressure_area_left_cd = Math.round(left_foot_cd/(left_foot_ad+left_foot_bd+left_foot_cd) * 100);
        int pressure_area_right_ad = Math.round(right_foot_ad/(right_foot_ad+right_foot_bd+right_foot_cd) * 100);
        int pressure_area_right_bd = Math.round(right_foot_bd/(right_foot_ad+right_foot_bd+right_foot_cd) * 100);
        int pressure_area_right_cd = Math.round(right_foot_cd/(right_foot_ad+right_foot_bd+right_foot_cd) * 100);

        footDataDetail.setPressure_area_left_ad(pressure_area_left_ad);
        footDataDetail.setPressure_area_left_bd(pressure_area_left_bd);
        footDataDetail.setPressure_area_left_cd(pressure_area_left_cd);
        footDataDetail.setPressure_area_right_ad(pressure_area_right_ad);
        footDataDetail.setPressure_area_right_bd(pressure_area_right_bd);
        footDataDetail.setPressure_area_right_cd(pressure_area_right_cd);

        jsonObject = JSONObject.parseObject(data).getJSONObject("result").getJSONObject("foot_scan_json").getJSONObject("result_info");
        String foot_type_left = jsonObject.getJSONObject("arch_info").getString("left");
        String foot_type_right = jsonObject.getJSONObject("arch_info").getString("right");
        String shoe_size_left = jsonObject.getJSONObject("design_shoes_size").getString("left");
        String shoe_size_right = jsonObject.getJSONObject("design_shoes_size").getString("right");

        footDataDetail.setFoot_type_left(foot_type_left);
        footDataDetail.setFoot_type_right(foot_type_right);
        footDataDetail.setShoe_size_left(shoe_size_left);
        footDataDetail.setShoe_size_right(shoe_size_right);

        String presure_img = JSONObject.parseObject(data).getJSONObject("result").getString("pressure_img");
        if(StringUtil.isNotEmpty(presure_img)) {
            footDataDetail.setFoot_presure_img("https://api.semsx.com/Common/StoreFile/getFileContent/?file_id=" + presure_img);
        }

        return footDataDetail;

    }


    /**
     * 获取体征数据
     */
    private void insertBodyData(String start_time, String end_time, TableRecord tableRecord){
        Gson gson=new Gson();
        Map<String, String> map=new LinkedHashMap();
        map.put("start_time",start_time==null?"2021-04-20 18:05:00":start_time);
        map.put("end_time",end_time==null?"2021-04-20 18:07:00":end_time);
        //map.put("device_code","EC:B9:77:13:3E:7E");
        //map.put("timestamp","12345");
        map.put("timestamp",String.valueOf(System.currentTimeMillis()));
        //map.put("sign","67c23f50f9b41f36fda4c18a886a3c27ee6b948ca758e19a989d996f57791fc1");

        //String url1 = "https://www.aicaring.com/test/ring/external_cooperation/user/create/";
        //Map<String, String> map1=new LinkedHashMap();

        String url = "https://www.aicaring.com/test/ring/external_cooperation/health/data/get/";
        List<String> deviceList = baseDataService.getDeviceCode("body");
        if(!CollectionUtils.isEmpty(deviceList)) {
            for (String device : deviceList) {
                //map1.put("device_code",device);
                //HttpUtil.post(url1, gson.toJson(map1));

                map.put("device_code",device);
                Sha256Utils.getSHA256StrJava(map);

                String params = gson.toJson(map);
                String data = HttpUtil.post(url, params);
                insertBodyData(data, map);

                map.remove("sign");
            }


            //更新调用时间
            /*if(tableRecord == null) {
                insertTableRecord("tb_body_data", map);
            }else {
                //更新调用时间
                System.out.println("#######insertBodyData.table_name="+tableRecord.getTable_name()+",id="+tableRecord.getId()+",end_time="+tableRecord.getEnd_time()+",end_time="+tableRecord.getEnd_time());
                updateTableRecord(tableRecord, map);
            }*/
            updateTableRecord(tableRecord, map);
        }

    }


    /**
     * 插入体征数据
     */
    private void insertBodyData(String data, Map<String, String> map){

        JSONObject jsonObject = JSONObject.parseObject(data);
        boolean status = jsonObject.getBoolean("status");
        if(status){
            int positive_per = jsonObject.getJSONObject("data").getJSONObject("emo_per").getIntValue("positive_per");
            int negative_per = jsonObject.getJSONObject("data").getJSONObject("emo_per").getIntValue("negative_per");
            int neutral_per = jsonObject.getJSONObject("data").getJSONObject("emo_per").getIntValue("neutral_per");

            if(positive_per == 0 && negative_per == 0 && neutral_per == 0){
                //如果没有数据，直接返回不再入库
                return;
            }

            double pressure_data = jsonObject.getJSONObject("data").getJSONObject("health_data").getJSONObject("pressure_data").getJSONObject("result_analysis").getDouble("data");
            String pressure_status = jsonObject.getJSONObject("data").getJSONObject("health_data").getJSONObject("pressure_data").getJSONObject("result_analysis").getString("status");
            String pressure_describe = jsonObject.getJSONObject("data").getJSONObject("health_data").getJSONObject("pressure_data").getJSONObject("result_analysis").getString("describe");
            JSONArray pressure_data_list = jsonObject.getJSONObject("data").getJSONObject("health_data").getJSONObject("pressure_data").getJSONArray("list");

            double anxiety_data = jsonObject.getJSONObject("data").getJSONObject("health_data").getJSONObject("anxiety_data").getJSONObject("result_analysis").getDouble("data");
            String anxiety_status = jsonObject.getJSONObject("data").getJSONObject("health_data").getJSONObject("anxiety_data").getJSONObject("result_analysis").getString("status");
            JSONArray anxiety_data_list = jsonObject.getJSONObject("data").getJSONObject("health_data").getJSONObject("anxiety_data").getJSONArray("list");

            double depression_data = jsonObject.getJSONObject("data").getJSONObject("health_data").getJSONObject("depression_data").getJSONObject("result_analysis").getDouble("data");
            String depression_status = jsonObject.getJSONObject("data").getJSONObject("health_data").getJSONObject("depression_data").getJSONObject("result_analysis").getString("status");
            JSONArray depression_data_list = jsonObject.getJSONObject("data").getJSONObject("health_data").getJSONObject("depression_data").getJSONArray("list");

            double blood_pressure_emo_data = jsonObject.getJSONObject("data").getJSONObject("blood_pressure_emo").getJSONObject("result_analysis").getDouble("data");
            String blood_pressure_emo_status = jsonObject.getJSONObject("data").getJSONObject("blood_pressure_emo").getJSONObject("result_analysis").getString("status");
            String blood_pressure_emo_describe = jsonObject.getJSONObject("data").getJSONObject("blood_pressure_emo").getJSONObject("result_analysis").getString("describe");
            JSONArray blood_pressure_emo_data_list = jsonObject.getJSONObject("data").getJSONObject("blood_pressure_emo").getJSONArray("list");

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

            //插入体征表tb_body_data
            //bodyDataService.insertBodyData(bodyData);
            baseDataService.insertData(bodyData);

            //插入体征详细表tb_body_data_detail
            for (int i=0; i<pressure_data_list.size(); i++) {
                BodyDataDetail bodyDataDetail = new BodyDataDetail();
                bodyDataDetail.setBody_id(bodyData.getId());
                bodyDataDetail.setPressure_data(pressure_data_list.getJSONObject(i).getFloat("data"));
                bodyDataDetail.setStart_time(pressure_data_list.getJSONObject(i).getString("start_time").replaceFirst("T", " "));
                bodyDataDetail.setEnd_time(pressure_data_list.getJSONObject(i).getString("end_time").replaceFirst("T", " "));

                bodyDataDetail.setAnxiety_data(anxiety_data_list.getJSONObject(i).getFloat("data"));
                bodyDataDetail.setDepression_data(depression_data_list.getJSONObject(i).getFloat("data"));

                bodyDataDetail.setBlood_pressure_dbp(blood_pressure_emo_data_list.getJSONObject(i).getString("dbp"));
                bodyDataDetail.setBlood_pressure_sbp(blood_pressure_emo_data_list.getJSONObject(i).getString("sbp"));
                bodyDataDetail.setBlood_pressure_hr(blood_pressure_emo_data_list.getJSONObject(i).getInteger("hr"));
                bodyDataDetail.setBlood_pressure_tp(blood_pressure_emo_data_list.getJSONObject(i).getInteger("tp"));
                bodyDataDetail.setBlood_pressure_step_count(blood_pressure_emo_data_list.getJSONObject(i).getInteger("step_count"));
                String emo_list = Arrays.toString(blood_pressure_emo_data_list.getJSONObject(i).getJSONArray("emo_list").toArray(new BigDecimal[]{}));
                if(emo_list.startsWith("[") && emo_list.endsWith("]")){
                    emo_list = emo_list.substring(emo_list.indexOf("[")+1, emo_list.lastIndexOf("]"));
                }
                bodyDataDetail.setBlood_pressure_emo_list(emo_list);

                baseDataService.insertData(bodyDataDetail);
            }
        }else{
            System.out.println("####devicecode="+map.get("device_code")+" status="+status);
        }

    }

    /**
     * 插入记录表
     */
    private void insertTableRecord(String table_name, Map map) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            TableRecord tableRecord = new TableRecord();
            tableRecord.setTable_name(table_name);
            tableRecord.setStart_time(sdf.parse((String)map.get("start_time")));
            tableRecord.setEnd_time(sdf.parse((String)map.get("end_time")));
            baseDataService.insertData(tableRecord);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    /**
     * 更新记录
     */
    private void updateTableRecord(TableRecord tableRecord, Map map){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            if(tableRecord == null){
                tableRecord = new TableRecord();
                tableRecord.setTable_name((String)map.get("table_name"));
            }
            tableRecord.setStart_time(sdf.parse((String)map.get("start_time")));
            tableRecord.setEnd_time(sdf.parse((String)map.get("end_time")));

            if(tableRecord.getId() != null && tableRecord.getId() > 0) {
                baseDataService.updateData(tableRecord);
            }else{
                baseDataService.insertData(tableRecord);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


}
