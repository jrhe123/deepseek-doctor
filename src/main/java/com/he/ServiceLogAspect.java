package com.he;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Component
@Aspect
public class ServiceLogAspect {

    @Around("execution(* com.he.service.impl..*.*(..))")
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {

        StopWatch watch = new StopWatch();
        watch.start();

        Object result = joinPoint.proceed();
        String point = joinPoint.getTarget().getClass().getSimpleName() + "." + joinPoint.getSignature().getName();
        watch.stop();

        log.info(watch.prettyPrint());
        log.info(watch.shortSummary());
        log.info("time ellapsed: " + watch.getTotalTimeMillis() + "ms");
        log.info("number of tasks: " + watch.getTaskCount());

        return result;
    }
}
