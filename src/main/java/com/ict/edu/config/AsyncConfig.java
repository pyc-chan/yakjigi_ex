package com.ict.edu.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {
    
    // 비동기 작업용 스레드 풀 설정
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);  // 기본 스레드 수
        executor.setMaxPoolSize(10); // 최대 스레드 수
        executor.setQueueCapacity(25); // 큐 크기
        executor.setThreadNamePrefix("AsyncThread-"); // 스레드 이름 접두사
        executor.initialize();
        return executor;
    }
}
