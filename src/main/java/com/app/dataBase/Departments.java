package com.app.dataBase;

import lombok.Data;

import java.sql.Time;

@Data
public class Departments
{
    private int departmentNum;
    private String departmentName;
    private String workTime;
    private String closingTime;
}
