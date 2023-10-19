package com.app.dataBase;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Workers {
    @TableId(value = "worker_num")
    private int workerNum;
    private String workerName;
    private String gender;
    private String phoneNumber;
    private int salary;
    private int departmentNum;
    private String password;

}
