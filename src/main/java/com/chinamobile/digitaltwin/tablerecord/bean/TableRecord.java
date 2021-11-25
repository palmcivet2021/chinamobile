package com.chinamobile.digitaltwin.tablerecord.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class TableRecord {
    private Integer id;
    private String table_name;
    private Date start_time;
    private Date end_time;
}
