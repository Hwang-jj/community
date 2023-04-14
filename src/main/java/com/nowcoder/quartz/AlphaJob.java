package com.nowcoder.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
  * @ClassName AlphaJob
  * @description: TODO
  * @author Hwangjj
  * @date 2023/4/14 15:42
  * @version: 1.0
  */ 

public class AlphaJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println(Thread.currentThread().getName() + ": execute a quartz job.");
    }
}
