package com.chige;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.chige.mapper")
@EnableScheduling //开启一个定时任务
public class SpringBootYiqingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootYiqingApplication.class, args);
    }

}
