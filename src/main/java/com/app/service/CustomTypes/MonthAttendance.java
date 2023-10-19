package com.app.service.CustomTypes;

public class MonthAttendance {
    //定义常量
    public static final int ATTENDANCE_ABSENCE = 0;
    public static final int ATTENDANCE_NORMAL = 1;
    public static final int ATTENDANCE_LATE = 2;
    public static final int ATTENDANCE_LEAVE_EARLY = 3;
    //迟到并早退
    public static final int ATTENDANCE_LATE_AND_LEAVE_EARLY = 4;
    //请假
    public static final int ATTENDANCE_LEAVE = 5;
    //出差
    public static final int ATTENDANCE_BUSINESS_TRIP = 6;

    public int workerNum;
    public int month;
    public int year;
    public int[] attendances;
}
