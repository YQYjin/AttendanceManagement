package com.app.service;

import com.app.dataBase.Attendances;
import com.app.dataBase.Departments;
import com.app.dataBase.Workers;
import com.app.mapper.AttendancesMapper;
import com.app.mapper.DepartmentsMapper;
import com.app.mapper.LeaveInfosMapper;
import com.app.mapper.WorkersMapper;
import com.app.service.CustomTypes.MonthAttendance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class AttendanceAnalyzer {
    @Autowired
    private WorkersMapper workersMapper;
    @Autowired
    private DepartmentsMapper departmentsMapper;
    @Autowired
    private AttendancesMapper attendancesMapper;
    @Autowired
    private LeaveInfosMapper leaveInfosMapper;


    //获取数据库数据，统计原始出勤情况
    public MonthAttendance srcAttendanceSummary(int workerNum,int year,int month){
        MonthAttendance monthAttendance=new MonthAttendance();
        monthAttendance.workerNum=workerNum;
        monthAttendance.year=year;
        monthAttendance.month=month;
        //获取当月天数
        YearMonth yearMonth = YearMonth.of(year, month);
        int daysInMonth = yearMonth.lengthOfMonth();
        //初始化数组，从1开始方便计算
        monthAttendance.attendances=new int[daysInMonth+1];
        //获取所属部门信息，上班时间和下班时间
        int departmentNum=getDepartmentNum(workerNum);
        String workTime=getWorkTime(departmentNum);
        String leaveTime=getLeaveTime(departmentNum);

        //查询当月出勤记录
        List<Attendances> attendancesList=attendancesMapper.selectMonthAttendance(workerNum,year,month);
        //遍历出勤记录，统计出勤情况
        //不统计缺勤,因为缺勤的记录不在数据库中,并且int数组初始值为0,而 ATTENDANCE_ABSENCE = 0
        for(Attendances attendance:attendancesList){

            //获取日期
            String dayDate=attendance.getDayTime();
            SimpleDateFormat dateFormat=new SimpleDateFormat("dd");
            String dayStr=dateFormat.format(dayDate);
            int day=Integer.parseInt(dayStr);


            String arriveTime=attendance.getArrivalTime();
            String leaveTime1=attendance.getLeaveTime();


            //判断是否正常出勤 到达时间在规定时间之前，离开时间在规定时间之后
            if(arriveTime.compareTo(workTime)<0&&leaveTime1.compareTo(leaveTime)>=0){
                monthAttendance.attendances[day]=MonthAttendance.ATTENDANCE_NORMAL;
            }
            //判断是否迟到 到达时间在规定时间之后
            else if(arriveTime.compareTo(workTime)>0){
                monthAttendance.attendances[day]=MonthAttendance.ATTENDANCE_LATE;
            }
            //判断是否早退 离开时间在规定时间之前
            else if(leaveTime1.compareTo(leaveTime)<0){
                monthAttendance.attendances[day]=MonthAttendance.ATTENDANCE_LEAVE_EARLY;
            }
            //判断是否迟到并早退
            else if(arriveTime.compareTo(workTime)>0&&leaveTime1.compareTo(leaveTime)<0){
                monthAttendance.attendances[day]=MonthAttendance.ATTENDANCE_LATE_AND_LEAVE_EARLY;
            }
        }
        return monthAttendance;
    }

    //根据出差和请假记录，修正出勤情况
    public void adjustAttendanceSummary(MonthAttendance data){
        int year=data.year;
        int month=data.month;

        //查询请假记录


    }



    private int getDepartmentNum(int workerNum){
        Workers worker=workersMapper.selectById(workerNum);
        int departmentNum=worker.getDepartmentNum();
        return departmentNum;

    }
    private String getWorkTime(int departmentNum){
        Departments department=departmentsMapper.selectById(departmentNum);
        String workTime=department.getWorkTime();
        return workTime;
    }
    private String getLeaveTime(int departmentNum){
        Departments department=departmentsMapper.selectById(departmentNum);
        String leaveTime=department.getClosingTime();
        return leaveTime;
    }
}
