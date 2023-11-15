package com.app.control;

import com.app.security.MyUserDetail;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

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
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        System.out.println("用户登录");
        if(authentication!=null){
            MyUserDetail userDetails = (MyUserDetail) authentication.getPrincipal();

            // 获取用户的userID
            int userID = userDetails.getUserID();
            System.out.println("用户已登录成功，跳转到"+"/user/"+userID);
        }

        /*if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            MyUserDetail userDetails = (MyUserDetail) authentication.getPrincipal();
            // 获取用户的userID
            int userID = userDetails.getUserID();
            System.out.println("用户登录成功，跳转到"+"/user/"+userID);
            return "redirect:/user/"+userID;

        }*/
        return "login-page";
    }
    @GetMapping("/adminloginpage")
    public String adminLoginControl(){
        return "admin-login-page";
    }

}
