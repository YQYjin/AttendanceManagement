package com.app.control;

import com.app.dataBase.Admins;
import com.app.dataBase.Departments;
import com.app.dataBase.WorkerWIthDepartment;
import com.app.dataBase.Workers;
import com.app.mapper.AdminMapper;
import com.app.mapper.DepartmentsMapper;
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
    @Autowired
    private DepartmentsMapper departmentsMapper;

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
        String workerName=data.get("userID");
        String password=data.get("password");
        System.out.println("用户名为:"+workerName+"用户密码为:"+password);

        //根据用户名查询用户id
        QueryWrapper<Workers> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("worker_name",workerName);
        Workers res=workerMapper.selectOne(queryWrapper);


        if(res==null) {
            return "fail";
        }
        int userID=res.getWorkerNum();

        //设置查询条件
        QueryWrapper<Workers> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("worker_num",userID);
        String check=workerMapper.selectOne(queryWrapper2).getPassword();
        System.out.println("用户的密码为:"+check+"用户输入的密码为:"+password);
        if(check.equals(password)){
            return String.valueOf(userID);
        }
        return "fail";
    }

    //用户注册
    @PostMapping("/admin/register")
    public String register(@RequestBody Map<String,String> data){
        System.out.println("用户注册");
        //获取用户信息
        String userName=data.get("username");
        String gender=data.get("gender");
        String phone=data.get("phone");
        int salary=Integer.parseInt(data.get("salary"));
        String departmentName=data.get("department");
        //根据部门名称查询ID
        QueryWrapper<Departments> depQueryWrapper = new QueryWrapper<>();
        depQueryWrapper.eq("department_name",departmentName);
        Departments departments=departmentsMapper.selectOne(depQueryWrapper);
        if(departments==null){
            return "noinfo";
        }
        int department=departments.getDepartmentNum();


        //输出用户所有信息
        System.out.println("用户名为:"+userName+"gender"+gender+"phone"+phone+"salary"+salary+"department"+department);

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
        worker.setPhoneNumber(phone);
        worker.setDepartmentNum(department);
        workerMapper.insert(worker);
        return "success";
    }
    //管理员登录
    @PostMapping("/adminlogin")
    public String adminLogin(@RequestBody Map<String,String> data){
        String adminName=data.get("userID");
        String password=data.get("password");
        System.out.println("管理员用户为:"+adminName+"用户密码为:"+password);
        //根据用户名查询用户id
        QueryWrapper<Admins> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("admin_name",adminName);
        Admins res=adminMapper.selectOne(queryWrapper);
        if(res==null) {
            return "fail";
        }
        int adminID=res.getAdminNum();
        //设置查询条件
        QueryWrapper<Admins> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("admin_num",adminID);
        String check=adminMapper.selectOne(queryWrapper2).getAdminPassword();
        System.out.println("管理员的密码为:"+check+"用户输入的密码为:"+password);
        if(check.equals(password)){
            //返回管理员的ID
            return String.valueOf(adminID);
        }
        return "fail";
    }
    //获取用户信息
    @GetMapping("/getuserinfo/{userID}")
    public WorkerWIthDepartment getUserInfo(@PathVariable String userID){
        //return myWorkerMapper.selectWorkerWithDepartmentByID(Integer.parseInt(userID));
        return myWorkerMapper.selectWorkerWithDepartmentByIDProduce(Integer.parseInt(userID));
    }
    @PostMapping("/admin/worker/query")
    public WorkerWIthDepartment queryWorker(@RequestBody Map<String,String> data){
        String workerName=data.get("workerName");
        System.out.println("用户查询的用户名为:"+workerName);
        //设置查询条件
        QueryWrapper<Workers> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("worker_name",workerName);

        Workers worker=workerMapper.selectOne(queryWrapper);
        if(worker==null) {
            return null;
        }
        //WorkerWIthDepartment res=myWorkerMapper.selectWorkerWithDepartmentByID(worker.getWorkerNum());
        WorkerWIthDepartment res=myWorkerMapper.selectWorkerWithDepartmentByIDProduce(worker.getWorkerNum());
        return res;
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
    @PostMapping("/admin/worker/modify")
    public String modify(@RequestBody Map<String,String> data){
        String id=data.get("workerID");
        String type=data.get("type");
        if(type.equals("name")){
            try{
                String newData=data.get("newData");
                System.out.println("修改员工ID:"+id+"姓名:"+newData);
                //根据id修改数据
                Workers worker=workerMapper.selectById(id);
                worker.setWorkerName(newData);
                workerMapper.updateById(worker);

                return "success";
            }catch (Exception e) {
                e.printStackTrace();

                return "error";
            }
        }else if(type.equals("gender")){
            try{
                String newData=data.get("newData");
                System.out.println("修改员工ID:"+id+"性别:"+newData);
                //根据id修改数据
                Workers worker=workerMapper.selectById(id);
                worker.setGender(newData);
                workerMapper.updateById(worker);
                return "success";
            }catch (Exception e) {
                e.printStackTrace();
                return "error";
            }
        }else if(type.equals("phone")){
            try{
                String newData=data.get("newData");
                System.out.println("修改员工ID:"+id+"电话:"+newData);
                //根据id修改数据
                Workers worker=workerMapper.selectById(id);
                worker.setPhoneNumber(newData);
                workerMapper.updateById(worker);

                return "success";
            }catch (Exception e) {
                e.printStackTrace();
                return "error";
            }
        }else if(type.equals("salary")){
            try{
                String newData=data.get("newData");
                System.out.println("修改员工ID:"+id+"薪水:"+newData);
                //根据id修改数据
                Workers worker=workerMapper.selectById(id);
                worker.setSalary(Integer.parseInt(newData));
                workerMapper.updateById(worker);


                return "success";
            }catch (Exception e) {
                e.printStackTrace();
                return "error";
            }
        }else if(type.equals("department")){
            try{
                String newData=data.get("newData");
                System.out.println("修改员工ID:"+id+"部门:"+newData);
                //根据部门名称查询部门ID
                QueryWrapper<Departments> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("department_name",newData);
                Departments departments=departmentsMapper.selectOne(queryWrapper);
                if(departments==null){
                    return "noinfo";
                }
                int departmentID=departmentsMapper.selectOne(queryWrapper).getDepartmentNum();
                //根据id修改数据
                Workers worker=workerMapper.selectById(id);
                worker.setDepartmentNum(departmentID);
                workerMapper.updateById(worker);
                return "success";
            }catch (Exception e) {
                e.printStackTrace();
                return "error";
            }
        } else if(type.equals("delete")){
            try{
                System.out.println("删除员工ID:"+id);
                //根据id删除数据
                workerMapper.deleteById(Integer.parseInt(id));
                return "success";
            }catch (Exception e) {
                e.printStackTrace();
                return "error";
            }
        }
        return "error";
    }
}
