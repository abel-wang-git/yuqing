package com.zkyf.config.globalExceptionHandler;

import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/3/8.
 * 全局异常处理
 */
@ControllerAdvice
public class GlobalDefaultExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = {UnauthorizedException.class})
    public String defaultErrorHandler(HttpServletRequest req, Exception e) {
        logger.error(e.getMessage());
        return "/403";
    }
}
