package com.app.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


public class Test {
    public static void main(String[] args) {
        String arriveTime = "08:30:00";
        String workTime = "20:55:50";
        System.out.println(arriveTime.compareTo(workTime));
    }

}
