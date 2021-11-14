package com.chinamobile.foot.body.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
public class BodyData implements Serializable {
    private Integer id;
    /**
     * 设备编码
     */
    private String device_code;
    //private Integer emo_per;
    private Integer positive_per;
    private Integer negative_per;
    private Integer neutral_per;
    /**
     * 压力数据
     */
    private Double pressure_data;
    /**
     * 压力状态
     */
    private String pressure_status;
    /**
     * 压力描述
     */
    private String pressure_describe;
    /**
     * 焦虑数据
     */
    private Double anxiety_data;
    /**
     * 焦虑状态
     */
    private String anxiety_status;
    /**
     * 抑郁数据
     */
    private Double depression_data;
    /**
     * 抑郁状态
     */
    private String depression_status;
    /**
     * 血压emo数据
     */
    private Double blood_pressure_emo_data;
    /**
     * 血压emo状态
     */
    private String blood_pressure_emo_status;
    /**
     * 血压emo描述
     */
    private String blood_pressure_emo_describe;
    private String start_time;
    private String end_time;
}
