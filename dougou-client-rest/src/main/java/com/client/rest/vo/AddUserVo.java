package com.client.rest.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;


@Data
public class AddUserVo {
    @Length(min = 4, max = 20, message = "用户名长度不正确")
    private String username;
    @Length(min = 8,max = 22,message = "密码长度需大于8位小于22位")
    private String password;
    @Length(min = 3, max = 20, message = "昵称长度不正确")
    private String nickName;
    private Integer roleId;
}
