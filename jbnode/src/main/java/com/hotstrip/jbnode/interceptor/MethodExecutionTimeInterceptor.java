package com.hotstrip.jbnode.interceptor;

import com.hotstrip.jbnode.common.annotations.CalcExecTime;
import com.hotstrip.jbnode.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class MethodExecutionTimeInterceptor {

    @Around("@annotation(com.hotstrip.jbnode.common.annotations.CalcExecTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        CalcExecTime calcExecTime = method.getAnnotation(CalcExecTime.class);
        if (null == calcExecTime || !calcExecTime.value()) {
            return joinPoint.proceed();
        }

        long startTime = System.currentTimeMillis(); // 获取方法执行开始时间

        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();  // 获取方法执行结束时间
        long duration = endTime - startTime; // 计算耗时

        log.info("{} executed in {} ", signature.toLongString(),
                CommonUtil.formatTimeMillis(duration));

        return result;
    }
}
