package com.example.rgt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.converter.json.GsonBuilderUtils;

@SpringBootApplication
public class RgtApplication {


    public static void main(String[] args) {

        SpringApplication.run(RgtApplication.class, args);
        System.out.println("실행");
    }


}
