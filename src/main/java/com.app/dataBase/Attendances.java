package com.app.dataBase;

import lombok.Data;



@Data
public class Attendances {
    //使用String映射date和time类型字段,方便赋值和取值
    private int attendanceNum;
    private int workerNum;
    private String dayTime;
    private String arrivalTime;
    private String leaveTime;
}
