package com.zkyf.core.controller;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;

/**
 * Created by Administrator on 2017/4/17.
 * web 日志切面
 */
@Aspect
@Component
@Order(-5)
public class WebLogs{


    private Logger logger =  LoggerFactory.getLogger(this.getClass());

    ThreadLocal<Long> startTime = new ThreadLocal<Long>();
    /**
     * 定义一个切入点.
     * 解释下：
     *
     * ~ 第一个 * 代表任意修饰符及任意返回值.
     * ~ 第二个 * 任意包名
     * ~ 第三个 * 代表任意方法.
     * ~ 第四个 * 定义在web包或者子包
     * ~ 第五个 * 任意方法
     * ~ .. 匹配任意数量的参数.
     */
    @Pointcut("execution(public * com.zkyf..*(..))")
    public void webLog(){}
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint){
        startTime.set(System.currentTimeMillis());

    }
    @AfterReturning("webLog()")
    public void  doAfterReturning(JoinPoint joinPoint){
        // 处理完请求，返回内容
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(attributes!=null) {
            HttpServletRequest request = attributes.getRequest ();
            //获取所有参数方法一：
            Enumeration<String> enu = request.getParameterNames ();
            StringBuilder para = new StringBuilder ();
            while (enu.hasMoreElements ()) {
                String paraName = enu.nextElement ();
                para.append (","+paraName).append (": " + request.getParameter (paraName));
            }
            // 记录下请求内容
            logger.info (
                    "---------------------------------------------------------------------------------\n"+
                    "[webLogger] URL : " + request.getRequestURL ().toString ()+"\n"+
                    "[webLogger] HTTP_METHOD : " + request.getMethod ()+"\n"+
                    "[webLogger] IP : " + request.getRemoteAddr ()+"\n"+
                    "[webLogger] CLASS_METHOD : " + joinPoint.getSignature ().getDeclaringTypeName () + "." +"\n"+
                            joinPoint.getSignature ().getName ()+"\n"+
                    "[webLogger] para:"+para.toString ()+"\n"+
                    "[webLogger] ARGS : " + Arrays.toString (joinPoint.getArgs ())+"\n"+
                    "耗时（毫秒） : " + (System.currentTimeMillis() - startTime.get())+"\n"+
                    "----------------------------------------------------------------------------------"
            );
        }else{
           
        }
    }
}
