package com.app.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class templatesControl {
    @GetMapping("/admin/workers-control")
    public String workerControl(){
        return "workers-control";
    }
    @GetMapping("/admin/departments-control")
    public String departmentControl(){
        return "department-control";
    }
    @GetMapping("/loginpage")
    public String loginControl(){
        return "login-page";
    }
    @GetMapping("/adminloginpage")
    public String adminLoginControl(){
        return "admin-login-page";
    }

}
