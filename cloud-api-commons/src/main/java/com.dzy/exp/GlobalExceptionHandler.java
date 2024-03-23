package com.dzy.exp;



import com.dzy.resp.ResultData;
import com.dzy.resp.ReturnCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
//全局异常处理注解
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultData<String> exception(Exception e){
        System.out.println("######### come in GlobalException");
        log.error("全局异常信息：{}",e.getMessage(),e);
        return  ResultData.fail(ReturnCodeEnum.RC500.getCode(), e.getMessage());
    }
}
