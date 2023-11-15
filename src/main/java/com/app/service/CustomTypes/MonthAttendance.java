package com.app.service.CustomTypes;

public class MonthAttendance {
    //定义常量
    public static final int ATTENDANCE_ABSENCE = 0;
    public static final int ATTENDANCE_NORMAL = 1;
    public static final int ATTENDANCE_LATE = 2;
    public static final int ATTENDANCE_LEAVE_EARLY = 3;
    //迟到并早退
    public static final int ATTENDANCE_LATE_AND_LEAVE_EARLY = 4;
    //病假
    public static final int ATTENDANCE_SICK_LEAVE = 5;
    //事假
    public static final int ATTENDANCE_PERSONAL_LEAVE = 6;
    //出差
    public static final int ATTENDANCE_EVECTION = 7;

    public int workerNum;
    public String workerName;
    public int month;
    public int year;
    public int[] attendances;
    public int sickTimes;
    public int personalTimes;
    public int evectionTimes;
    public int absenceTimes;

    public double overtimeHours;
    public String isFullAttendance;

     public void print(){
         System.out.println("员工编号:"+workerNum);
         System.out.println("年:"+year+"月:"+month);
         System.out.println("出勤情况:");
         for(int i=1;i<attendances.length;i++) {
             String type;
             if (attendances[i] == ATTENDANCE_ABSENCE)
                 type = "缺勤";
             else if (attendances[i] == ATTENDANCE_NORMAL)
                 type = "正常";
             else if (attendances[i] == ATTENDANCE_LATE)
                 type = "迟到";
             else if (attendances[i] == ATTENDANCE_LEAVE_EARLY)
                 type = "早退";
             else if (attendances[i] == ATTENDANCE_LATE_AND_LEAVE_EARLY)
                 type = "迟到并早退";
             else if (attendances[i] == ATTENDANCE_SICK_LEAVE)
                 type = "病假";
             else if (attendances[i] == ATTENDANCE_PERSONAL_LEAVE)
                 type = "事假";
             else
                 type = "出差";
             System.out.print("日期:" + i + " " + type + " ");
             if (i % 7 == 0){
                 System.out.println();
            }
         }
         System.out.println("病假次数:"+sickTimes+"事假次数:"+personalTimes+"出差次数:"+evectionTimes+"缺勤次数:"+absenceTimes+"加班时长:"+overtimeHours);

    }
}
