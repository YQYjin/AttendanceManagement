package com.app;

import com.app.service.AttendanceAnalyzer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.app.test.Test;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com/app/mapper")
@EnableScheduling

public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);



    }
}
