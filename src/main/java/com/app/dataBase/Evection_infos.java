package com.app.dataBase;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Date;

@Data
public class Evection_infos {
    @TableId(value = "evection_num")
    private int evectionNum;
    private int workerNum;
    private String reason;
    private String   startTime;
    private String   endTime;
    private byte isPass;
}
