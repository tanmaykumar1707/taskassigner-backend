package com.assinger.taskservice.aspects;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggerAspects {

    @Before("execution(* com.assinger.taskservice.controller.*.*(..))" )
    public void log(JoinPoint joinPoint){
        log.info("logging for the trigger of "+joinPoint.getSignature().toString());
    }


}



