package com.app.control;

import com.app.dataBase.Admins;
import com.app.dataBase.WorkerWIthDepartment;
import com.app.dataBase.Workers;
import com.app.mapper.AdminMapper;
import com.app.mapper.MyWorkerMapper;
import com.app.mapper.WorkersMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
public class WorkerControl {
    @Autowired
    private WorkersMapper workerMapper;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private MyWorkerMapper myWorkerMapper;

    @GetMapping("/workers")
    public String getUsers() {
        Gson gson=new Gson();
        List<Workers> users=workerMapper.selectList(null);
        System.out.println(users.get(0).getWorkerNum());
        String json=gson.toJson(users);
        return json;
    }
    //用户登录的后端检查
    @PostMapping("/login")
    public String checkLogin(@RequestBody Map<String,String> data) {
        String userID=data.get("userID");
        String password=data.get("password");
        System.out.println("用户ID为:"+userID+"用户密码为:"+password);
        //将userID转为相应int类型
        int iUserID=Integer.parseInt(userID);
        //设置查询条件
        QueryWrapper<Workers> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("worker_num",iUserID);
        String check=workerMapper.selectOne(queryWrapper).getPassword();
        System.out.println("用户的密码为:"+check+"用户输入的密码为:"+password);
        if(check.equals(password)){
            return "success";
        }
        return "fail";
    }
    //用户注册
    @PostMapping("/register")
    public String register(@RequestBody Map<String,String> data){
        System.out.println("用户注册");
        //获取用户信息
        String userName=data.get("username");
        String gender=data.get("gender");
        String phone=data.get("phone");
        int salary=Integer.parseInt(data.get("salary"));
        int department=Integer.parseInt(data.get("department"));
        String password=data.get("password");
        //输出用户所有信息
        System.out.println("用户名为:"+userName+"gender"+gender+"phone"+phone+"salary"+salary+"department"+department+"password"+password);

        // 创建 QueryWrapper 对象 用于条件查询
        QueryWrapper<Workers> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("worker_name",userName);
        Workers res=workerMapper.selectOne(queryWrapper);
        //如果该用户已存在
        if(res!=null) {
            return "fail";
        }
        //添加用户
        //worker数据完整性和合法性由前端检验
        Workers worker=new Workers();
        worker.setWorkerName(userName);
        worker.setGender(gender);
        worker.setSalary(salary);
        worker.setPassword(password);
        worker.setPhoneNumber(phone);
        worker.setDepartmentNum(department);
        workerMapper.insert(worker);
        return "success";
    }
    //管理员登录
    @PostMapping("/adminlogin")
    public String adminLogin(@RequestBody Map<String,String> data){
        String userID=data.get("userID");
        String password=data.get("password");
        System.out.println("管理员ID为:"+userID+"用户密码为:"+password);
        //将userID转为相应int类型
        int iUserID=Integer.parseInt(userID);
        //设置查询条件
        QueryWrapper<Admins> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("admin_num",iUserID);
        String check=adminMapper.selectOne(queryWrapper).getAdminPassword();
        System.out.println("管理员的密码为:"+check+"用户输入的密码为:"+password);
        if(check.equals(password)){
            return "success";
        }
        return "fail";
    }
    //获取用户信息
    @GetMapping("/getuserinfo/{userID}")
    public WorkerWIthDepartment getUserInfo(@PathVariable String userID){
        return myWorkerMapper.selectWorkerWithDepartmentByID(Integer.parseInt(userID));
    }
    @PostMapping("/modifyname/{userID}")
    public String modifyName(@PathVariable String userID,@RequestBody Map<String,String> data){
        String name=data.get("Name");
        System.out.println("用户ID为:"+userID+"用户修改的用户名为:"+name);
        //将userID转为相应int类型
        int iUserID=Integer.parseInt(userID);
        //设置查询条件
        QueryWrapper<Workers> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("worker_num",iUserID);
        Workers worker=workerMapper.selectOne(queryWrapper);
        worker.setWorkerName(name);
        workerMapper.update(worker,queryWrapper);

        return "success";
    }
    @PostMapping("/modifygender/{userID}")
    public String modifyGender(@PathVariable String userID,@RequestBody Map<String,String> data){
        String gender=data.get("gender");
        System.out.println("用户ID为:"+userID+"用户修改的性别为:"+gender);
        //将userID转为相应int类型
        int iUserID=Integer.parseInt(userID);
        //设置查询条件
        QueryWrapper<Workers> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("worker_num",iUserID);
        Workers worker=workerMapper.selectOne(queryWrapper);
        worker.setGender(gender);
        workerMapper.update(worker,queryWrapper);

        return "success";

    }
    @PostMapping("/modifyphone/{userID}")
    public String modifyPhone(@PathVariable String userID,@RequestBody Map<String,String> data){
        String phone=data.get("phoneNumber");
        System.out.println("用户ID为:"+userID+"用户修改的性别为:"+phone);
        //将userID转为相应int类型
        int iUserID=Integer.parseInt(userID);
        //设置查询条件
        QueryWrapper<Workers> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("worker_num",iUserID);
        Workers worker=workerMapper.selectOne(queryWrapper);
        worker.setPhoneNumber(phone);
        workerMapper.update(worker,queryWrapper);

        return "success";

    }
    @PostMapping("/modifypassword/{userID}")
    public String modifyPassword(@PathVariable String userID,@RequestBody Map<String,String> data){
        String oldPassword=data.get("oldPassword");
        String newPassword=data.get("newPassword");
        //根据userID查询密码
        //将userID转为相应int类型
        int iUserID=Integer.parseInt(userID);
        //设置查询条件
        QueryWrapper<Workers> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("worker_num",iUserID);
        String check=workerMapper.selectOne(queryWrapper).getPassword();
        System.out.println("用户的密码为:"+check+"用户输入的密码为:"+oldPassword);
        if(check.equals(oldPassword)){
            if(oldPassword.equals(newPassword)){
                return "same password";
            }
            Workers worker=workerMapper.selectOne(queryWrapper);
            worker.setPassword(newPassword);
            workerMapper.update(worker,queryWrapper);
            return "success";
        }
        return "wrong password";
    }
}
