package com.eventmaster.user_services;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Pointcut to target all methods in controller package
    @Pointcut("execution(* com.eventmaster.user_services.controller..*(..))")
    public void controllerMethods() {}

    // Log execution time
    @Around("controllerMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable ex) {
            logger.error("Exception in {}(): {} at {}", joinPoint.getSignature().getName(), ex.getClass().getSimpleName(), ex.getStackTrace()[0]);
            throw ex;
        }

        long endTime = System.currentTimeMillis();
        logger.info("Method {}() executed successfully in {} ms.", joinPoint.getSignature().getName(), (endTime - startTime));
        return result;
    }

    @AfterThrowing(pointcut = "controllerMethods()", throwing = "ex")
    public void logException(JoinPoint joinPoint, Throwable ex) {
        logger.error("Exception in {}(): {}", joinPoint.getSignature().getName(), ex.getMessage());
    }
}

