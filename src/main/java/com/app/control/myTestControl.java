package com.app.control;

import com.app.dataBase.Evection_infos;
import com.app.mapper.*;
import com.app.service.AttendanceAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class myTestControl {
    @Autowired
    private WorkersMapper workersMapper;
    @Autowired
    private  DepartmentsMapper departmentsMapper;
    @Autowired
    private  AttendancesMapper attendancesMapper;
    @Autowired
    private LeaveInfosMapper leaveInfosMapper;
    @Autowired
    private EvectionInfosMapper evection_infosMapper;


    @GetMapping("/test/analyze")
    public String analyzeTest(){
        System.out.println("考勤统计测试");
        AttendanceAnalyzer attendanceAnalyzer=new AttendanceAnalyzer(workersMapper,departmentsMapper,attendancesMapper,leaveInfosMapper,evection_infosMapper);
        attendanceAnalyzer.analyze(1,2023,10);

        return "success";
    }
    @GetMapping("/test/process")
    public List<Evection_infos> processTest(){
        List<Evection_infos> res=evection_infosMapper.selectMonthEvectionInfosByProcedure(1,2023,10);

        return res;
    }
}
