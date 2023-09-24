package com.app.dataBase;

import lombok.Data;


@Data
public class Leave_infos {
    private int leaveNum;
    private int workerNum;
    private String type;
    private String reason;
    private String startTime;
    private String endTime;
    private byte isPass;
}
