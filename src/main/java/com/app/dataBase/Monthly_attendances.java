package com.app.dataBase;

import lombok.Data;

import java.sql.Date;

@Data
public class Monthly_attendances {
    private int attendanceNum;
    private  int workerNum;
    private String monthTime;
    private byte fullAttendance;
    private  int sickLeave;
    private int generalLeave;
    private int evectionLeave;
    private int overtime;
}
