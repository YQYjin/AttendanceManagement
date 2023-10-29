package com.app.dataBase;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Date;

@Data
public class Monthly_attendances {
    @TableId(value = "attendance_num")
    private int attendanceNum;
    private  int workerNum;
    private String monthTime;
    private byte fullAttendance;
    private  int sickLeave;
    private int generalLeave;
    private int evectionLeave;
    private int absence_times;
    private double overtime;
}
