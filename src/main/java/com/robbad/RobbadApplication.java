package com.robbad;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication(scanBasePackages={"com.robbad"})
@MapperScan("com.robbad.dao")
public class RobbadApplication {

    public static void main(String[] args) {
        SpringApplication.run(RobbadApplication.class, args);
    }

}
