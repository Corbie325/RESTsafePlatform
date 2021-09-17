package com.example.restsafeplatform.utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ServiceConfiguration {

    @Value("${server.thread-task-send-messages}")
    private Integer numberOfThreads;

    @Bean
    public ExecutorService getExecutorService() {
        return Executors.newFixedThreadPool(numberOfThreads);
    }
}
