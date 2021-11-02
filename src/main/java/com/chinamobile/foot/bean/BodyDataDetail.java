package com.chinamobile.foot.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class BodyDataDetail implements Serializable {
    private Integer id;
    private Integer body_id;
    private Float pressure_data;
    private Float anxiety_data;
    private Float depression_data;
    private String blood_pressure_dbp;
    private String blood_pressure_sbp;
    private Integer blood_pressure_hr;
    private Integer blood_pressure_tp;
    private Integer blood_pressure_step_count;
    private String blood_pressure_emo_list;
    private String start_time;
    private String end_time;
}
