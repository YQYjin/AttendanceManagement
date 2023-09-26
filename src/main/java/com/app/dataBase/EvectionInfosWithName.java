package com.app.dataBase;

import lombok.Data;

@Data
public class EvectionInfosWithName {
    private int evectionNum;
    private int workerNum;
    private String reason;
    private String startTime;
    private String endTime;
    private byte isPass;
    private String workerName;
}
