package com.app.control;


import com.app.dataBase.Monthly_attendances;
import com.app.dataBase.Workers;
import com.app.mapper.*;
import com.app.service.AttendanceAnalyzer;
import com.app.service.CustomTypes.MonthAttendance;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class AnalyzerControl {
    @Autowired
    private WorkersMapper workersMapper;
    @Autowired
    private DepartmentsMapper departmentsMapper;
    @Autowired
    private AttendancesMapper attendancesMapper;
    @Autowired
    private LeaveInfosMapper leaveInfosMapper;
    @Autowired
    private EvectionInfosMapper evection_infosMapper;
    @Autowired
    private MonthlyAttendancesMapper monthlyAttendancesMapper;

    //统计某月的考勤结果
    @PostMapping("/analyze/all")
    public String analyzeAll(@RequestBody Map<String, String> request){
        AttendanceAnalyzer attendanceAnalyzer=new AttendanceAnalyzer(workersMapper,departmentsMapper,attendancesMapper,leaveInfosMapper,evection_infosMapper);

        String strDate=request.get("date");
        String[] date=strDate.split("-");
        String strMonth=date[1];
        String strYear=date[0];
        int intMonth=Integer.parseInt(strMonth);
        int intYear=Integer.parseInt(strYear);

        List<Workers> allWorkers=workersMapper.selectList(null);
        List<MonthAttendance> result=new ArrayList<MonthAttendance>();
        //遍历所有员工,统计考勤信息
        for(Workers worker:allWorkers){
            MonthAttendance monthAttendance=attendanceAnalyzer.analyze(worker.getWorkerNum(),intYear,intMonth);
            result.add(monthAttendance);
        }
        //遍历,将结果更新到数据库
        for(MonthAttendance monthAttendance:result){
            Monthly_attendances monthly_attendances=new Monthly_attendances();

            monthly_attendances.setWorkerNum(monthAttendance.workerNum);

            int year=monthAttendance.year;
            int month=monthAttendance.month;
            String yearMonthStr = convertIntsToYearMonthString(year, month);
            monthly_attendances.setMonthTime(yearMonthStr);

            byte isFullAttendance= (byte) (monthAttendance.isFullAttendance.equals("是")?1:0);
            monthly_attendances.setFullAttendance(isFullAttendance);

            monthly_attendances.setSickLeave(monthAttendance.sickTimes);

            monthly_attendances.setGeneralLeave(monthAttendance.personalTimes);

            monthly_attendances.setEvectionLeave(monthAttendance.evectionTimes);

            monthly_attendances.setOvertime(monthAttendance.overtimeHours);

            monthly_attendances.setAbsenceTimes(monthAttendance.absenceTimes);

            //根据员工编号和月份查询数据库,如果有记录,则更新,否则插入
            QueryWrapper<Monthly_attendances> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("worker_num",monthly_attendances.getWorkerNum());
            queryWrapper.eq("month_time",monthly_attendances.getMonthTime());
            Monthly_attendances monthly_attendances1=monthlyAttendancesMapper.selectOne(queryWrapper);
            if(monthly_attendances1==null){
                monthlyAttendancesMapper.insert(monthly_attendances);
            }else{
                monthly_attendances.setAttendanceNum(monthly_attendances1.getAttendanceNum());
                monthlyAttendancesMapper.updateById(monthly_attendances);
            }

        }

        return "success";
    }
    /*@PostMapping("/analyze/all")
    public List<MonthAttendance> analyzeAll2(@RequestBody Map<String, String> request){
        AttendanceAnalyzer attendanceAnalyzer=new AttendanceAnalyzer(workersMapper,departmentsMapper,attendancesMapper,leaveInfosMapper,evection_infosMapper);
        String strDate=request.get("date");
        String[] date=strDate.split("-");
        String strMonth=date[1];
        String strYear=date[0];
        int month=Integer.parseInt(strMonth);
        int year=Integer.parseInt(strYear);


        List<Workers> allWorkers=workersMapper.selectList(null);
        List<MonthAttendance> result=new ArrayList<MonthAttendance>();
        if(allWorkers.size()==0){
            return result;
        }
        for(Workers worker:allWorkers){
            MonthAttendance monthAttendance=attendanceAnalyzer.analyze(worker.getWorkerNum(),year,month);
            result.add(monthAttendance);
        }


        return result;
    }*/

    /*@PostMapping("/analyze/one")
    public List<MonthAttendance> analyzeOne(@RequestBody Map<String, String> request){
        AttendanceAnalyzer attendanceAnalyzer=new AttendanceAnalyzer(workersMapper,departmentsMapper,attendancesMapper,leaveInfosMapper,evection_infosMapper);
        String strDate=request.get("date");
        String[] date=strDate.split("-");
        String strMonth=date[1];
        String strYear=date[0];

        String workerName=request.get("workerName");
        //根据姓名查询员工编号
        QueryWrapper<Workers> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("worker_name",workerName);
        List<Workers> workers=workersMapper.selectList(queryWrapper);

        int month=Integer.parseInt(strMonth);
        int year=Integer.parseInt(strYear);


        List<MonthAttendance> result=new ArrayList<MonthAttendance>();
        if(workers.size()==0){
            return result;
        }
        for(Workers worker:workers){
            MonthAttendance monthAttendance=attendanceAnalyzer.analyze(worker.getWorkerNum(),year,month);
            result.add(monthAttendance);
        }

        return result;

    }*/
    //查询某月的考勤结果
    @PostMapping("/query/all")
    public List<MonthAttendance> queryAll(@RequestBody Map<String, String> request){

        List<Workers> allWorkers=workersMapper.selectList(null);
        List<Monthly_attendances> queryResult=new ArrayList<Monthly_attendances>();

        String strDate=request.get("date");
        String[] date=strDate.split("-");
        String strMonth=date[1];
        String strYear=date[0];
        int month=Integer.parseInt(strMonth);
        int year=Integer.parseInt(strYear);
        String yearMonthStr = convertIntsToYearMonthString(year, month);

        //遍历所有员工,统计考勤信息
        for(Workers worker:allWorkers){
            QueryWrapper<Monthly_attendances> queryWrapper2=new QueryWrapper<>();
            queryWrapper2.eq("worker_num",worker.getWorkerNum());
            queryWrapper2.eq("month_time",yearMonthStr);
            List<Monthly_attendances> oneResult=monthlyAttendancesMapper.selectList(queryWrapper2);

            queryResult.addAll(oneResult);
        }
        List<MonthAttendance> result = new ArrayList<MonthAttendance>();
        for(Monthly_attendances monthly_attendances:queryResult){
            MonthAttendance monthAttendance=new MonthAttendance();
            monthAttendance.workerNum=monthly_attendances.getWorkerNum();
            monthAttendance.year=Integer.parseInt(monthly_attendances.getMonthTime().substring(0,4));
            monthAttendance.month=Integer.parseInt(monthly_attendances.getMonthTime().substring(5,7));
            monthAttendance.isFullAttendance=monthly_attendances.getFullAttendance()==1?"是":"否";
            monthAttendance.sickTimes=monthly_attendances.getSickLeave();
            monthAttendance.personalTimes=monthly_attendances.getGeneralLeave();
            monthAttendance.evectionTimes=monthly_attendances.getEvectionLeave();
            monthAttendance.overtimeHours=monthly_attendances.getOvertime();
            monthAttendance.absenceTimes=monthly_attendances.getAbsenceTimes();
            //根据员工编号查询员工姓名
            QueryWrapper<Workers> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("worker_num",monthly_attendances.getWorkerNum());
            Workers workers=workersMapper.selectOne(queryWrapper);
            monthAttendance.workerName=workers.getWorkerName();

            result.add(monthAttendance);
        }
        return result;
    }
    @PostMapping("/query/one")
    public List<MonthAttendance> queryOne(@RequestBody Map<String, String> request){

        String strDate=request.get("date");
        String[] date=strDate.split("-");
        String strMonth=date[1];
        String strYear=date[0];

        String workerName=request.get("workerName");
        //根据姓名查询员工编号
        QueryWrapper<Workers> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("worker_name",workerName);
        Workers workers=workersMapper.selectOne(queryWrapper);
        if(workers==null){
            return null;
        }

        int month=Integer.parseInt(strMonth);
        int year=Integer.parseInt(strYear);

        String yearMonthStr = convertIntsToYearMonthString(year, month);

        //根据员工编号和月份查询数据库
        QueryWrapper<Monthly_attendances> queryWrapper2=new QueryWrapper<>();
        queryWrapper2.eq("worker_num",workers.getWorkerNum());
        queryWrapper2.eq("month_time",yearMonthStr);
        List<Monthly_attendances> queryResult=monthlyAttendancesMapper.selectList(queryWrapper2);
        List<MonthAttendance> result = new ArrayList<MonthAttendance>();
        for(Monthly_attendances monthly_attendances:queryResult){
            MonthAttendance monthAttendance=new MonthAttendance();
            monthAttendance.workerNum=monthly_attendances.getWorkerNum();
            monthAttendance.year=Integer.parseInt(monthly_attendances.getMonthTime().substring(0,4));
            monthAttendance.month=Integer.parseInt(monthly_attendances.getMonthTime().substring(5,7));
            monthAttendance.isFullAttendance=monthly_attendances.getFullAttendance()==1?"是":"否";
            monthAttendance.sickTimes=monthly_attendances.getSickLeave();
            monthAttendance.personalTimes=monthly_attendances.getGeneralLeave();
            monthAttendance.evectionTimes=monthly_attendances.getEvectionLeave();
            monthAttendance.overtimeHours=monthly_attendances.getOvertime();
            monthAttendance.absenceTimes=monthly_attendances.getAbsenceTimes();

            monthAttendance.workerName=workerName;

            result.add(monthAttendance);
        }

        return result;
    }

    public static String convertIntsToYearMonthString(int year, int month) {
        // 创建一个Date对象，以便使用SimpleDateFormat格式化日期
        Date date = new Date(year - 1900, month - 1, 1);

        // 创建SimpleDateFormat对象，定义输出格式为YYYY-MM-DD
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // 使用SimpleDateFormat格式化日期并返回结果字符串
        String formattedDate = sdf.format(date);

        return formattedDate;
    }
}
