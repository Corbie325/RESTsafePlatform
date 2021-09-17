package com.example.restsafeplatform;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@Slf4j
@SpringBootApplication
@EnableAsync
public class ResTsafePlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResTsafePlatformApplication.class, args);
    }

}

