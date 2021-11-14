package com.chinamobile.foot.ehead.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class EHeadData implements Serializable {
    private Integer id;
    private Integer channel_1;
    private Integer channel_2;
    private Integer channel_3;
    private Integer channel_4;
    private Integer channel_5;
    private Integer channel_6;
    private Integer channel_7;
    private Integer channel_8;
    private Integer deal_time;
}
