package com.app.control;

import com.app.dataBase.Workers;
import com.app.mapper.WorkersMapper;
import com.app.postData.LoginData;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
public class WorkerControl {
    @Autowired
    private WorkersMapper workerMapper;

    @GetMapping("/workers")
    public String getUsers() {
        Gson gson=new Gson();
        List<Workers> users=workerMapper.selectList(null);
        System.out.println(users.get(0).getWorkerNum());
        String json=gson.toJson(users);
        return json;
    }
    //用户登录的后端检查
    @GetMapping("/login")
    public String checkLogin(@RequestBody LoginData loginData) {
        String userName=loginData.getUserName();
        String password=loginData.getPassword();
        String check="";
        //TODO
        //check从数据库中找出用户名对应的密码
        if(check.equals(password)){
            return "success";
        }
        return "fail";
    }
    //用户注册
    @PostMapping("/register")
    public String register(@RequestBody Workers worker){
        // 创建 QueryWrapper 对象 用于条件查询
        QueryWrapper<Workers> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("workerName",worker.getWorkerName());
        Workers res=workerMapper.selectOne(queryWrapper);
        //如果该用户已存在
        if(res!=null) {
            return "fail";
        }
        //添加用户
        //worker数据完整性和合法性由前端检验
        workerMapper.insert(worker);
        return "success";
    }
}
