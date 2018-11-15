package com.stylefeng.guns.rest.config.aop;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author sh00859
 * @description face aop
 * @date 2018/5/25
 */
@Aspect
@Component
public class WebLogAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * 切面，匹配com.zkn.learnspringboot.web.controller包及其子包下的所有类的所有方法
     */
    @Pointcut("execution(public * com.stylefeng.guns.rest.modular.football.controller..*.*(..))")
    public void webLog() {

    }


    /**
     * 方法耗时统计
     */
    @Around("webLog()")
    public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        String url = request.getRequestURL().toString();
        try {
            long begin = System.nanoTime();
            Object obj = proceedingJoinPoint.proceed();//调用执行目标方法
            long end = System.nanoTime();
            if (url.contains("attach")) {
                logger.info("url:[{}]time:[{}s]", url, (Math.round(end - begin) / 1000000000.0));
            } else {
                logger.info("url:[{}]param:{}time:[{}s]", url, JSONObject.toJSONString(proceedingJoinPoint.getArgs()), (Math.round(end - begin) / 1000000000.0));
            }

            return obj;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }


}