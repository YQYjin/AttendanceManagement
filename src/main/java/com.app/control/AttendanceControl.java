package com.app.control;

import com.app.dataBase.Attendances;
import com.app.dataBase.Workers;
import com.app.mapper.AttendancesMapper;
import com.app.postData.AttendanceData;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class AttendanceControl {
    @Autowired
    private AttendancesMapper attendancesMapper;

    @GetMapping ("/test")
    public String attendancePage(Model model) {
        System.out.println("进入测试页面");
        // 在这里根据用户ID进行相关逻辑处理，例如从数据库获取用户信息
        model.addAttribute("userID", "userId");
        // 返回对应的 HTML 模板视图
        return "testpage";
    }
//    @PostMapping
//    public String signIn(@RequestBody AttendanceData data){
//        String name=data.getWorkerName();
//        //获取当前时间
//        Date date=new Date();
//
//        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
//        // 创建 QueryWrapper 对象 用于条件查询
//        QueryWrapper<Attendances> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("workerName",data.getWorkerName())
//                .eq("dayTime",dateFormat.format(date));
//        Attendances attendances=attendancesMapper.selectOne(queryWrapper);
//        //如果当日未签到
////        if(attendances==null){
////            Attendances info=new Attendances();
////            info.setDayTime(date);
////            attendancesMapper.insert()
////        }
//        return "success";
//    }
}
