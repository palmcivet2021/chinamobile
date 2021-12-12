package com.chinamobile.digitaltwin.insole.bean;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "insole")
public class Insole {
    @Id
    private String id;
    private String deviceId;
    //ad1-ad8为压力传感器数据
    private Integer ad1;
    private Integer ad2;
    private Integer ad3;
    private Integer ad4;
    private Integer ad5;
    private Integer ad6;
    private Integer ad7;
    private Integer ad8;
    //惯性传感器角度数据
    private Double angle_x;
    private Double angle_y;
    private Double angle_z;
    //惯性传感器加速度数据
    private Double acceleration_x;
    private Double acceleration_y;
    private Double acceleration_z;
    //惯性传感器角度加速度数据
    private Double angular_speed_x;
    private Double angular_speed_y;
    private Double angular_speed_z;
    //惯性传感器磁场强度数据
    private Double magnetic_strength_x;
    private Double magnetic_strength_y;
    private Double magnetic_strength_z;



}
