package com.app.control;


import com.app.dataBase.Workers;
import com.app.mapper.*;
import com.app.service.AttendanceAnalyzer;
import com.app.service.CustomTypes.MonthAttendance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/analyze/all")
    public List<MonthAttendance> analyzeAll(){
        AttendanceAnalyzer attendanceAnalyzer=new AttendanceAnalyzer(workersMapper,departmentsMapper,attendancesMapper,leaveInfosMapper,evection_infosMapper);
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        int currentMonth = currentDate.getMonthValue();

        List<Workers> allWorkers=workersMapper.selectList(null);
        List<MonthAttendance> result=new ArrayList<MonthAttendance>();
        //遍历所有员工,统计考勤信息
        for(Workers worker:allWorkers){
            MonthAttendance monthAttendance=attendanceAnalyzer.analyze(worker.getWorkerNum(),currentYear,currentMonth);
            result.add(monthAttendance);
        }


        return result;
    }
    @PostMapping("/analyze/all")
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
        for(Workers worker:allWorkers){
            MonthAttendance monthAttendance=attendanceAnalyzer.analyze(worker.getWorkerNum(),year,month);
            result.add(monthAttendance);
        }


        return result;
    }
    @PostMapping("/analyze/one")
    public List<MonthAttendance> analyzeOne(@RequestBody Map<String, String> request){
        AttendanceAnalyzer attendanceAnalyzer=new AttendanceAnalyzer(workersMapper,departmentsMapper,attendancesMapper,leaveInfosMapper,evection_infosMapper);
        String strMonth=request.get("month");
        String strYear=request.get("year");
        String strWorkerNum=request.get("workerNum");
        int month=Integer.parseInt(strMonth);
        int year=Integer.parseInt(strYear);
        int workerNum=Integer.parseInt(strWorkerNum);

        List<MonthAttendance> result=new ArrayList<MonthAttendance>();
        MonthAttendance monthAttendance=attendanceAnalyzer.analyze(workerNum,year,month);
        result.add(monthAttendance);
        return result;

    }
}
