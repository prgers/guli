package com.prgers.common.Exception;

import com.prgers.common.result.Result;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ApiModel("自定义异常类")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EduException extends RuntimeException{

    @ApiModelProperty("状态码")
    private Integer code;
    @ApiModelProperty("异常消息")
    private String message;

    @Override
    public String toString() {
        return "EduException{" +
                "message=" + this.getMessage() +
                ", code=" + code +
                '}';
    }

}
