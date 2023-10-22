package com.app.service;

import com.app.dataBase.*;
import com.app.mapper.*;
import com.app.service.CustomTypes.MonthAttendance;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class AttendanceAnalyzer {
    private final WorkersMapper workersMapper;
    private final DepartmentsMapper departmentsMapper;
    private final AttendancesMapper attendancesMapper;
    private final LeaveInfosMapper leaveInfosMapper;
    private final EvectionInfosMapper evection_infosMapper;

    public AttendanceAnalyzer(WorkersMapper workersMapper, DepartmentsMapper departmentsMapper, AttendancesMapper attendancesMapper, LeaveInfosMapper leaveInfosMapper, EvectionInfosMapper evection_infosMapper) {
        this.workersMapper = workersMapper;
        this.departmentsMapper = departmentsMapper;
        this.attendancesMapper = attendancesMapper;
        this.leaveInfosMapper = leaveInfosMapper;
        this.evection_infosMapper = evection_infosMapper;
    }

    public void analyze(int workerNum,int year,int month){

        MonthAttendance monthAttendance=srcAttendanceSummary(workerNum,year,month);

        monthAttendance=adjustAttendanceSummary(monthAttendance);

        monthAttendance.print();
    }

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
            String[] parts = dayDate.split("-");
            String dayStr = parts[2]; // 获取日期的天部分
            int day=Integer.parseInt(dayStr);


            String arriveTime=attendance.getArrivalTime();
            String leaveTime1=attendance.getLeaveTime();


            //判断是否正常出勤 到达时间在规定时间之前，离开时间在规定时间之后
            if(arriveTime.compareTo(workTime)<=0&&leaveTime1.compareTo(leaveTime)>=0){
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
        System.out.println("原始数据");
        monthAttendance.print();
        return monthAttendance;
    }

    //根据出差和请假记录，修正出勤情况
    public MonthAttendance adjustAttendanceSummary(MonthAttendance data){
        int year=data.year;
        int month=data.month;
        int workerNum=data.workerNum;



        //查询请假记录
        List<Leave_infos> leave_infosList=leaveInfosMapper.selectMonthLeaveInfos(workerNum,year,month);
        //遍历请假记录，修正出勤情况
        for(Leave_infos leave_info:leave_infosList){
            //跳过未审批通过的记录
            if(leave_info.getIsPass()==0) {
                continue;
            }
            String strStartDate=leave_info.getStartTime();
            String strEndDate=leave_info.getEndTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate=null;
            Date endDate=null;
            try{
                startDate = sdf.parse(strStartDate);
                endDate = sdf.parse(strEndDate);

            }catch (Exception e){
                e.printStackTrace();
            }
            for(int day=1;day<data.attendances.length;++day){
                Date dayDate=new Date(year-1900,month-1,day);
                if(dayDate.compareTo(startDate)>=0&&dayDate.compareTo(endDate)<=0){
                    //判断是事假还是病假
                    if(leave_info.getType()=="病假")
                        data.attendances[day]=MonthAttendance.ATTENDANCE_SICK_LEAVE;
                    else
                        data.attendances[day]=MonthAttendance.ATTENDANCE_PERSONAL_LEAVE;
                }
            }
        }
        //查询出差记录
        List<Evection_infos> evection_infosList=evection_infosMapper.selectMonthEvectionInfos(workerNum,year,month);
        //遍历出差记录，修正出勤情况
        for(Evection_infos evection_info:evection_infosList){
            //跳过未审批通过的记录
            if(evection_info.getIsPass()==0) {
                continue;
            }
            String strStartDate=evection_info.getStartTime();
            String strEndDate=evection_info.getEndTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate=null;
            Date endDate=null;
            try{
                startDate = sdf.parse(strStartDate);
                endDate = sdf.parse(strEndDate);

            }catch (Exception e){
                e.printStackTrace();
            }
            for(int day=1;day<data.attendances.length;++day){
                Date dayDate=new Date(year-1900,month-1,day);
                if(dayDate.compareTo(startDate)>=0&&dayDate.compareTo(endDate)<=0){
                    data.attendances[day]=MonthAttendance.ATTENDANCE_EVECTION;
                }
            }
        }
        return data;
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
