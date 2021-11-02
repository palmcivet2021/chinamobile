package com.chinamobile.foot.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 用户数据列表
 * </p>
 */
@Data
@NoArgsConstructor
public class FootData implements Serializable {

    private Integer foot_scan_id;
    private String foot_scan_sign;
    private Integer left_length;
    private Integer right_length;
    private Integer left_width;
    private Integer right_width;
    private Integer left_height;
    private Integer right_height;
    private String left_arch;
    private String right_arch;
    private String left_heel;
    private String right_heel;
    private Integer user_id;
    private Integer company_id;
    private Integer foot_scan_group_id;
    private String measure_time;
    private Integer scan_type;
    private Integer store_id;
    private String phone;
    private String crm_remark_name;
    private String crm_remark_remark;
    private String crm_remark_sex;
    private String crm_remark_size;
    private String store_name;

}
