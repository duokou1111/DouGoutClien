package com.client.rest.dto;

import com.client.core.Bo.BaseResponse;
import com.client.rest.enums.ResponseCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponseDto<T> {
    T data;
    private Boolean success;
    private Integer code;
    private String message;
    public static BaseResponseDto error(Integer code, String message){
        return new BaseResponseDto(null,false,code,message);
    }
    public BaseResponseDto(T data){
        this.success = true;
        this.data = data;
        this.code = ResponseCodeEnum.RESPONSE_CODE_SUCCESS.getCode();
    }
}
