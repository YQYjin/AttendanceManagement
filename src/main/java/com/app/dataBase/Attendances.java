package com.app.dataBase;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;



@Data
public class Attendances {
    //使用String映射date和time类型字段,方便赋值和取值
    @TableId(value = "attendance_num")
    private int attendanceNum;
    private int workerNum;
    private String dayTime;
    private String arrivalTime;
    private String leaveTime;
}
