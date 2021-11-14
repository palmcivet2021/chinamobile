package com.chinamobile.foot.ehead.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class EHeadDataResult implements Serializable {
    private Integer id;
    private Integer userid;
    private Integer result;
    private Date deal_time;
}
