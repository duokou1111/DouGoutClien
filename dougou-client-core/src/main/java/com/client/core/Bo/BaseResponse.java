package com.client.core.Bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BaseResponse<T> {
    T data;
    private Boolean success;
    private Integer code;
    private String message;

}
