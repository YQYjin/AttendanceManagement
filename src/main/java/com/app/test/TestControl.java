package com.app.test;

import com.app.dataBase.Attendances;
import com.app.mapper.AttendancesMapper;
import com.google.gson.Gson;
import com.app.dataBase.Workers;
import com.app.mapper.WorkersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestControl {
    @Autowired
    private WorkersMapper workerMapper;
    @Autowired
    private AttendancesMapper attendancesMapper;

    @GetMapping("/workerstest")
    public String getUsers() {
        Gson gson=new Gson();
        List<Workers> users=workerMapper.selectList(null);
        System.out.println(users.get(0).getWorkerNum());
        String json=gson.toJson(users);
        return json;
    }
    @GetMapping("/attendancetest")
    public String getattendance() {
        Gson gson=new Gson();
        List<Attendances> res=attendancesMapper.selectList(null);
        String json=gson.toJson(res);
        return json;
    }
}
