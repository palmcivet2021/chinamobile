package com.chinamobile.foot.shoepad.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 鞋垫压力数据<br/>
 */
@Data
@NoArgsConstructor
public class ShoePadData3 implements Serializable {
    private int[] id;
    private int[] userid;
    private int[] ad1;  //ad1~ad8压力传感器读数，共八组数据
    private int[] ad2;
    private int[] ad3;
    private int[] ad4;
    private int[] ad5;
    private int[] ad6;
    private int[] ad7;
    private int[] ad8;
    private float[] acceleration_x;  //加速度，共x,y,x三组数据
    private float[] acceleration_y;
    private float[] acceleration_z;
    private float[] angular_speed_x; //角速度，共x,y,x三组数据
    private float[] angular_speed_y;
    private float[] angular_speed_z;
    private float[] angle_x;         //角度，共x,y,x三组数据
    private float[] angle_y;
    private float[] angle_z;
    private float[] magnetic_strength_x; //磁场强度，共x,y,x三组数据
    private float[] magnetic_strength_y;
    private float[] magnetic_strength_z;
    private int[] deal_time;
    private String[] show_time;
}
