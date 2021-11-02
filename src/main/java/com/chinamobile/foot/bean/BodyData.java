package com.chinamobile.foot.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
public class BodyData implements Serializable {
    private Integer id;
    private String device_code;
    //private Integer emo_per;
    private Integer positive_per;
    private Integer negative_per;
    private Integer neutral_per;
    private Double pressure_data;
    private String pressure_status;
    private String pressure_describe;
    private Double anxiety_data;
    private String anxiety_status;
    private Double depression_data;
    private String depression_status;
    private Double blood_pressure_emo_data;
    private String blood_pressure_emo_status;
    private String blood_pressure_emo_describe;
    private String start_time;
    private String end_time;
}
