package com.client.rest.controller;
import com.client.core.feign.UserServerRestTemplate;
import com.client.core.ibo.AddUserIbo;
import com.client.rest.dto.BaseResponseDto;
import com.client.rest.vo.AddUserVo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: DouGoutClient
 * @description: 用户控制器
 * @author: zihan.wu
 * @create: 2020-12-19 20:06
 **/
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserServerRestTemplate userServerRestTemplate;
    @Autowired
    ModelMapper modelMapper;

    /**
     * 普通用户注册
     * @param addUserVo
     * @return BaseResponseDto<Boolean>
     */
    @PostMapping("/register")
    public BaseResponseDto<Boolean> addUser(@RequestBody @Validated AddUserVo addUserVo){
        AddUserIbo addUserIbo = modelMapper.map(addUserVo,AddUserIbo.class);
        addUserIbo.setPassword(new BCryptPasswordEncoder().encode(addUserVo.getPassword()));
        return modelMapper.map(userServerRestTemplate.register(addUserIbo),BaseResponseDto.class);
    }
}
