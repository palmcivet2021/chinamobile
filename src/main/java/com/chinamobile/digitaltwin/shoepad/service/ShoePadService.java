package com.chinamobile.digitaltwin.shoepad.service;


import com.chinamobile.digitaltwin.shoepad.bean.ShoePadData2;

import java.util.Map;

//public interface ShoePadService extends BaseDataService<ShoePadData2> {
public interface ShoePadService {
    public ShoePadData2 getShoePadData(Map map);
}
