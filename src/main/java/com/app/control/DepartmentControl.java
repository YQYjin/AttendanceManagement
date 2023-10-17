package com.app.control;

import com.app.dataBase.Departments;
import com.app.dataBase.Workers;
import com.app.mapper.DepartmentsMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class DepartmentControl {

    @Autowired
    private DepartmentsMapper departmentsMapper;

    @PostMapping("/department/register")
    public String homePage(@RequestBody Map<String,String> data){
        String name=data.get("departmentName");
        String startTime=data.get("start_time");
        String endTime=data.get("end_time");

        System.out.println("部门名称:"+name+"开始时间:"+startTime+"结束时间:"+endTime);

        //根据部门名称查询
        QueryWrapper<Departments> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("department_name",name);
        Departments check=departmentsMapper.selectOne(queryWrapper);
        if(check!=null) {
            return "该部门已存在";
        }
        //插入数据
        Departments department=new Departments();
        department.setDepartmentName(name);
        department.setWorkTime(startTime);
        department.setClosingTime(endTime);
        departmentsMapper.insert(department);
        return "success";
    }

}
