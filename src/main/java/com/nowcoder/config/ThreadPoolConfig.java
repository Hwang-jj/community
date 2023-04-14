package com.nowcoder.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
  * @ClassName ThreadPoolConfig
  * @description: TODO
  * @author Hwangjj
  * @date 2023/4/14 15:20
  * @version: 1.0
  */ 
@Configuration
@EnableScheduling
@EnableAsync
public class ThreadPoolConfig {
}
