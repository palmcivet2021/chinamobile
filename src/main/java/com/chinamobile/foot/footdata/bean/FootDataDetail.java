package com.chinamobile.foot.footdata.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class FootDataDetail implements Serializable {
    private Integer foot_scan_id;
    private Integer left_length;
    private Integer left_width;
    private Integer left_height;
    private Integer right_length;
    private Integer right_width;
    private Integer right_height;
    private Float foot_presure_left; //左足压力值
    private Float foot_presure_right; //右足压力值
    private Integer foot_presure_left_percent; //左足压百分比
    private Integer foot_presure_right_percent; //右足压百分比
    private Float arch_index_left; //足弓指标左足
    private Float arch_index_right; //足弓指标右足
    private Integer pressure_area_left_ad; //压力六区左足
    private Integer pressure_area_left_bd; //压力六区左足
    private Integer pressure_area_left_cd; //压力六区左足
    private Integer pressure_area_right_ad; //压力六区右足
    private Integer pressure_area_right_bd; //压力六区右足
    private Integer pressure_area_right_cd; //压力六区右足
    private String foot_type_left; //足型判定左足
    private String foot_type_right; //足型判定右足
    private String shoe_size_left; //建议选鞋码数左足
    private String shoe_size_right; //建议选鞋码数右足
    private String foot_presure_img; //足压图形
}
