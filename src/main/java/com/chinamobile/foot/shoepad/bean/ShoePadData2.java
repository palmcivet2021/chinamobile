package com.chinamobile.foot.shoepad.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 鞋垫压力数据<br/>
 */
@Data
@NoArgsConstructor
public class ShoePadData2 implements Serializable {
    private String id;
    private String userid;
    private String ad1;  //ad1~ad8压力传感器读数，共八组数据
    private String ad2;
    private String ad3;
    private String ad4;
    private String ad5;
    private String ad6;
    private String ad7;
    private String ad8;
    private String acceleration_x;  //加速度，共x,y,x三组数据
    private String acceleration_y;
    private String acceleration_z;
    private String angular_speed_x; //角速度，共x,y,x三组数据
    private String angular_speed_y;
    private String angular_speed_z;
    private String angle_x;         //角度，共x,y,x三组数据
    private String angle_y;
    private String angle_z;
    private String magnetic_strength_x; //磁场强度，共x,y,x三组数据
    private String magnetic_strength_y;
    private String magnetic_strength_z;
    private String deal_time;
    private String show_time;
}
