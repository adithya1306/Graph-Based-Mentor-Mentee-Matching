package com.career.mentorship_matching_service.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;

@Aspect
@Component
public class SchedulerLogger {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(SchedulerLogger.class.getName());

    @Around("execution(* com.career.mentorship_matching_service.service.scheduler.SchedulerService.updateCompletedMeeting(..))")
    public Object logScheduler(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        logger.info("✅ Cron Job Started: " + joinPoint.getSignature().getName());

        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        logger.info("✅ Cron Job Completed: " + joinPoint.getSignature().getName() + " in " + duration + " ms");

        return result;
    }
}
