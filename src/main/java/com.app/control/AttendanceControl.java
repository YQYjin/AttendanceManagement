package com.app.control;

import com.app.dataBase.Attendances;
import com.app.dataBase.Workers;
import com.app.mapper.AttendancesMapper;
import com.app.postData.AttendanceData;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@RestController
public class AttendanceControl {
    @Autowired
    private AttendancesMapper attendancesMapper;

    @PostMapping ("/attendance")
    public String attendance(@RequestBody Map<String, String> data) {
        String userID=data.get("userID");
        //获取当前时间
        Date date=new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        //获取当前时分秒
        SimpleDateFormat timeFormat=new SimpleDateFormat("HH:mm:ss");
        // 创建 QueryWrapper 对象 用于条件查询
        QueryWrapper<Attendances> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("worker_num",userID)
                .eq("day_time",dateFormat.format(date));
        Attendances attendances=attendancesMapper.selectOne(queryWrapper);
        //当然第一次签到时,应该没有当日数据,判断是否有查询结果来判断是否已经签到
        //若没有签到,则插入签到数据
        if(attendances==null){
            Attendances info=new Attendances();
            info.setWorkerNum(Integer.parseInt(userID));
            info.setDayTime(dateFormat.format(date));
            info.setArrivalTime(timeFormat.format(date));
            attendancesMapper.insert(info);
            System.out.println("签到的用户ID为："+data.get("userID"));
            return "success";
        }
        else{
            return "fail";
        }
    }
    @PostMapping("/checkout")
    public String cheackOut(@RequestBody Map<String, String> data){
        String userID=data.get("userID");
        //获取当前时间
        Date date=new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat=new SimpleDateFormat("HH:mm:ss");
        //根据id和日期查询
        QueryWrapper<Attendances> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("worker_num",userID)
                .eq("day_time",dateFormat.format(date));
        Attendances attendances=attendancesMapper.selectOne(queryWrapper);
        //没有签到就不能登记离岗
        if(attendances==null){
            return "noInfo";
        }
        //已经离岗不能重复操作
        if(attendances.getLeaveTime()!=null){
            return "fail";
        }
        //更新数据库,添加离岗时间
        attendances.setLeaveTime(timeFormat.format(date));
        attendancesMapper.updateById(attendances);
        return "success";
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
