package com.eventmaster.event_services;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Configurable threshold for slow methods (in milliseconds)
    @Value("${logging.aspect.slow.method.threshold:1}")
    private long slowMethodThreshold;

    // Pointcut to target all methods in controller package
    @Pointcut("execution(* com.eventmaster.event_services.controller..*(..))")
    public void controllerMethods() {}

    // Log execution time and monitor performance
    @Around("controllerMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        Object result;
        try {
            result = joinPoint.proceed();

            long executionTime = System.currentTimeMillis() - startTime;

            // Log execution time
            logger.info("Method {}.{}() executed in {} ms",
                    className, methodName, executionTime);

            // Warn if method execution is too slow
            if (executionTime > slowMethodThreshold) {
                logger.warn("Performance warning: {}.{}() took {} ms (threshold: {} ms)",
                        className, methodName, executionTime, slowMethodThreshold);
            }

            return result;


        } catch (Throwable ex) {
            logger.error("Exception in {}.{}(): {} at {}",
                    className, methodName,
                    ex.getClass().getSimpleName(),
                    ex.getMessage());
            throw ex;
        }


    }
}
