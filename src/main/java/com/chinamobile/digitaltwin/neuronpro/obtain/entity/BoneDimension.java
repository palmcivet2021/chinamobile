package com.chinamobile.digitaltwin.neuronpro.obtain.entity;

public class BoneDimension {
    // Command start token: 0xAAFF
    short Token1;
    short CommandType;
    short CommandId;
    // Num bytes in payload
    int nDataBytes;
    // Make this struct to align to 8 bytes
    int nReserved;
    // Package end token: 0xBBFF
    short Token2;
}
