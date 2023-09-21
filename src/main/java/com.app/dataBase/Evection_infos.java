package com.app.dataBase;

import lombok.Data;

import java.sql.Date;

@Data
public class Evection_infos {
    private int evectionNum;
    private int workerNum;
    private String reason;
    private String    startTime;
    private String    endTime;
}
