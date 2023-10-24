package com.app.control;


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
        if(allWorkers.size()==0){
            return result;
        }
        for(Workers worker:allWorkers){
            MonthAttendance monthAttendance=attendanceAnalyzer.analyze(worker.getWorkerNum(),year,month);
            result.add(monthAttendance);
        }


        return result;
    }
    @PostMapping("/analyze/one")
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

    }
}
