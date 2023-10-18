package com.app.dataBase;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Time;

@Data
public class Departments
{
    @TableId(value = "department_num")
    private int departmentNum;
    private String departmentName;
    private String workTime;
    private String closingTime;
}
