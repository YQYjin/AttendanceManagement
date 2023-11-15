package com.app.service;

import com.app.dataBase.*;
import com.app.mapper.*;
import com.app.service.CustomTypes.MonthAttendance;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
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

    public MonthAttendance analyze(int workerNum, int year, int month) {
        System.out.println("analyze:"+"员工编号:"+workerNum+"年:"+year+"月:"+month);

        MonthAttendance monthAttendance = srcAttendanceSummary(workerNum, year, month);

        monthAttendance = adjustAttendanceSummary(monthAttendance);

        //monthAttendance.print();
        return monthAttendance;
    }

    //获取数据库数据，统计原始出勤情况
    public MonthAttendance srcAttendanceSummary(int workerNum, int year, int month) {
        MonthAttendance monthAttendance = new MonthAttendance();
        monthAttendance.workerNum = workerNum;
        monthAttendance.year = year;
        monthAttendance.month = month;
        monthAttendance.workerName=getWorkerName(workerNum);
        //获取当月天数
        YearMonth yearMonth = YearMonth.of(year, month);
        int daysInMonth = yearMonth.lengthOfMonth();
        //初始化数组，从1开始方便计算
        monthAttendance.attendances = new int[daysInMonth + 1];
        //获取所属部门信息，上班时间和下班时间
        int departmentNum = getDepartmentNum(workerNum);
        String workTime = getWorkTime(departmentNum);
        String leaveTime = getLeaveTime(departmentNum);

        //查询当月出勤记录
        //List<Attendances> attendancesList = attendancesMapper.selectMonthAttendance(workerNum, year, month);
        List<Attendances> attendancesList = attendancesMapper.selectMonthAttendanceProcedure(workerNum, year, month);
        //遍历出勤记录，统计出勤情况
        //不统计缺勤,因为缺勤的记录不在数据库中,并且int数组初始值为0,而 ATTENDANCE_ABSENCE = 0
        if (attendancesList != null) {
            for (Attendances attendance : attendancesList) {
                System.out.println("出勤编号:"+attendance.getAttendanceNum());
                //获取日期
                String dayDate = attendance.getDayTime();
                String[] parts = dayDate.split("-");
                String dayStr = parts[2]; // 获取日期的天部分
                int day = Integer.parseInt(dayStr);

                String arriveTime = attendance.getArrivalTime();
                String leaveTime1 = attendance.getLeaveTime();
                System.out.print("到达时间:" + arriveTime + " 离开时间:" + leaveTime1+"上班时间:"+workTime+"下班时间:"+leaveTime);
                //判断是否有下班时间,若下班时间为空则改日缺勤
                if (leaveTime1 == null) {
                    continue;
                }
                //计算加班时间
                if (leaveTime1.compareTo(leaveTime) > 0) {
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    try {
                        Date date1 = sdf.parse(leaveTime);
                        Date date2 = sdf.parse(leaveTime1);
                        // 将时间转换为毫秒并计算差值
                        long time1Millis = date1.getTime();
                        long time2Millis = date2.getTime();
                        long timeDifferenceMillis = Math.abs(time2Millis - time1Millis);
                        // 将差值转换为小时
                        double timeDifferenceHours = (double) timeDifferenceMillis / (60 * 60 * 1000);
                        monthAttendance.overtimeHours += timeDifferenceHours;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                //判断是否正常出勤 到达时间在规定时间之前，离开时间在规定时间之后
                if (arriveTime.compareTo(workTime) <= 0 && leaveTime1.compareTo(leaveTime) >= 0) {
                    monthAttendance.attendances[day] = MonthAttendance.ATTENDANCE_NORMAL;
                }
                //判断是否迟到 到达时间在规定时间之后
                else if (arriveTime.compareTo(workTime) > 0) {
                    monthAttendance.attendances[day] = MonthAttendance.ATTENDANCE_LATE;
                }
                //判断是否早退 离开时间在规定时间之前
                else if (leaveTime1.compareTo(leaveTime) < 0) {
                    monthAttendance.attendances[day] = MonthAttendance.ATTENDANCE_LEAVE_EARLY;
                }
                //判断是否迟到并早退
                else if (arriveTime.compareTo(workTime) > 0 && leaveTime1.compareTo(leaveTime) < 0) {
                    monthAttendance.attendances[day] = MonthAttendance.ATTENDANCE_LATE_AND_LEAVE_EARLY;
                }
                System.out.println(" 出勤情况:" + monthAttendance.attendances[day]);
            }
        }
        System.out.println("原始数据");
        monthAttendance.print();
        return monthAttendance;
    }

    //根据出差和请假记录，修正出勤情况
    public MonthAttendance adjustAttendanceSummary(MonthAttendance data) {
        int year = data.year;
        int month = data.month;
        int workerNum = data.workerNum;

        //根据周末进行修正,如果是周末,都设为正常出勤
        for(int day=1;day<data.attendances.length;++day){
            LocalDate date = LocalDate.of(year, month, day);
            if(isWeekend(date)){
                data.attendances[day]=MonthAttendance.ATTENDANCE_NORMAL;
            }
        }


        //查询请假记录
        //List<Leave_infos> leave_infosList = leaveInfosMapper.selectMonthLeaveInfos(workerNum, year, month);
        List<Leave_infos> leave_infosList = leaveInfosMapper.selectMonthLeaveInfosProcedure(workerNum, year, month);
        //遍历请假记录，修正出勤情况
        if (leave_infosList != null) {
            for (Leave_infos leave_info : leave_infosList) {
                //跳过未审批通过的记录
                if (leave_info.getIsPass() != 1) {
                    continue;
                }
                String strStartDate = leave_info.getStartTime();
                String strEndDate = leave_info.getEndTime();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date startDate = null;
                Date endDate = null;
                try {
                    startDate = sdf.parse(strStartDate);
                    endDate = sdf.parse(strEndDate);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                for (int day = 1; day < data.attendances.length; ++day) {
                    Date dayDate = new Date(year - 1900, month - 1, day);
                    if (dayDate.compareTo(startDate) >= 0 && dayDate.compareTo(endDate) <= 0) {
                        //判断是事假还是病假
                        if (leave_info.getType().equals("病假")) {
                            data.attendances[day] = MonthAttendance.ATTENDANCE_SICK_LEAVE;
                            data.sickTimes += 1;
                        } else {
                            data.attendances[day] = MonthAttendance.ATTENDANCE_PERSONAL_LEAVE;
                            data.personalTimes += 1;
                        }
                    }
                }
            }
        }
        //查询出差记录
        //List<Evection_infos> evection_infosList = evection_infosMapper.selectMonthEvectionInfos(workerNum, year, month);
        List<Evection_infos> evection_infosList = evection_infosMapper.selectMonthEvectionInfosByProcedure(workerNum, year, month);
        //遍历出差记录，修正出勤情况
        if(evection_infosList != null) {
            for (Evection_infos evection_info : evection_infosList) {
                //跳过未审批通过的记录
                if (evection_info.getIsPass() != 1) {
                    continue;
                }
                String strStartDate = evection_info.getStartTime();
                String strEndDate = evection_info.getEndTime();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date startDate = null;
                Date endDate = null;
                try {
                    startDate = sdf.parse(strStartDate);
                    endDate = sdf.parse(strEndDate);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                for (int day = 1; day < data.attendances.length; ++day) {
                    Date dayDate = new Date(year - 1900, month - 1, day);
                    if (dayDate.compareTo(startDate) >= 0 && dayDate.compareTo(endDate) <= 0) {
                        data.attendances[day] = MonthAttendance.ATTENDANCE_EVECTION;
                        data.evectionTimes += 1;
                    }
                }
            }
        }
        //遍历,统计缺勤次数:
        for (int day = 1; day < data.attendances.length; ++day) {
            //既不是正常出勤也不是出差请假,则为缺勤

            if (data.attendances[day] != MonthAttendance.ATTENDANCE_NORMAL&&data.attendances[day]!=MonthAttendance.ATTENDANCE_EVECTION
                    &&data.attendances[day]!=MonthAttendance.ATTENDANCE_PERSONAL_LEAVE&&data.attendances[day]!=MonthAttendance.ATTENDANCE_SICK_LEAVE) {
                data.absenceTimes += 1;
            }
        }
        if(data.absenceTimes==0){
            data.isFullAttendance="是";
        }else {
            data.isFullAttendance="否";
        }
        return data;
    }


    private int getDepartmentNum(int workerNum) {
        Workers worker = workersMapper.selectById(workerNum);
        int departmentNum = worker.getDepartmentNum();
        return departmentNum;
    }
    private String getWorkerName(int workerNum) {
        Workers worker = workersMapper.selectById(workerNum);
        String workerName = worker.getWorkerName();
        return workerName;

    }
    private String getWorkTime(int departmentNum) {
        Departments department = departmentsMapper.selectById(departmentNum);
        String workTime = department.getWorkTime();
        return workTime;
    }

    private String getLeaveTime(int departmentNum) {
        Departments department = departmentsMapper.selectById(departmentNum);
        String leaveTime = department.getClosingTime();
        return leaveTime;
    }
    private static boolean isWeekend(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }
}
