package com.app.control;

import com.app.dataBase.Admins;
import com.app.dataBase.Attendances;
import com.app.dataBase.Workers;
import com.app.mapper.AdminMapper;
import com.app.mapper.AttendancesMapper;
import com.app.mapper.WorkersMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class homeControl {
    @Autowired
    private WorkersMapper workersMapper;
    @Autowired
    private AdminMapper adminMapper;
    @GetMapping("/user/{userID}")
    public String homePage(@PathVariable String userID, Model model){
        // 根据ID查询用户名
        QueryWrapper<Workers> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("worker_num",userID);
        Workers worker=workersMapper.selectOne(queryWrapper);
        String userName=worker.getWorkerName();
        model.addAttribute("userName",userName);
        return "home-page";
    }
    @GetMapping("/admin/{userID}")
    public String adminHomePage(@PathVariable String userID, Model model){
        // 根据ID查询用户名
        QueryWrapper<Admins> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("admin_num",userID);
        Admins admin=adminMapper.selectOne(queryWrapper);
        String userName=admin.getAdminName();
        model.addAttribute("userName",userName);
        return "admin-home-page";
    }
    @GetMapping("/test/{userID}")
    public String testHomePage(@PathVariable String userID, Model model){
        model.addAttribute("userName",userID);
        return "layuitest";
    }
    @GetMapping("/userinfo/{userID}")
    public String userInfoPage(@PathVariable String userID, Model model){
        //model.addAttribute("userName",userID);
        return "user-info-page";
    }
}
