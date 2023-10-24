package com.app.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class templatesControl {
    @GetMapping("/workers-control")
    public String workerControl(){
        return "workers-control";
    }
    @GetMapping("/department-control")
    public String departmentControl(){
        return "department-control";
    }
}
