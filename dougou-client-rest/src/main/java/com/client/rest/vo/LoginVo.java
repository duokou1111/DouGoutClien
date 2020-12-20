package com.client.rest.vo;

import lombok.Data;
import lombok.ToString;

/**
 * @program: DouGoutClient
 * @description: 用户名和密码
 * @author: zihan.wu
 * @create: 2020-12-20 21:22
 **/
@Data
@ToString
public class LoginVo {
    private String username;
    private String password;
}
