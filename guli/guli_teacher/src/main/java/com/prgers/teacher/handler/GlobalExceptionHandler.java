package com.prgers.teacher.handler;

import com.prgers.common.Exception.EduException;
import com.prgers.common.result.Result;
import com.prgers.common.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一处理异常类
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();
        log.error(ExceptionUtil.getMessage(e));
        return Result.error().message("操作出错了！");
    }

    //指定异常处理
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result error(ArithmeticException e) {
        e.printStackTrace();
        log.error(ExceptionUtil.getMessage(e));
        return Result.error().message("操作出错了，除数不能为0！");
    }

    //自定义异常处理
    @ExceptionHandler(EduException.class)
    @ResponseBody
    public Result error(EduException e) {
        e.printStackTrace();
        return Result.error().code(e.getCode()).message(e.getMessage());
    }
}
