package com.chinamobile.foot.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ShoePadData implements Serializable {
    private Integer id;
    private Integer userid;
    private Integer ad1;  //ad1~ad8压力传感器读数，共八组数据
    private Integer ad2;
    private Integer ad3;
    private Integer ad4;
    private Integer ad5;
    private Integer ad6;
    private Integer ad7;
    private Integer ad8;
    private Float acceleration_x;  //加速度，共x,y,x三组数据
    private Float acceleration_y;
    private Float acceleration_z;
    private Float angular_speed_x; //角速度，共x,y,x三组数据
    private Float angular_speed_y;
    private Float angular_speed_z;
    private Float angle_x;         //角度，共x,y,x三组数据
    private Float angle_y;
    private Float angle_z;
    private Float magnetic_strength_x; //磁场强度，共x,y,x三组数据
    private Float magnetic_strength_y;
    private Float magnetic_strength_z;
    private Integer deal_time;
    private String show_time;
}
