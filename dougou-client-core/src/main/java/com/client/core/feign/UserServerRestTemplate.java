package com.client.core.feign;


import com.client.core.Bo.BaseResponse;
import com.client.core.Bo.GetUserInfoBo;
import com.client.core.ibo.AddUserIbo;
import com.client.core.ibo.UsernameIbo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("DOUGOU-WEB-SERVER-PROVIDER")
public interface UserServerRestTemplate {
    @PostMapping(value = "/user/retrive")
    BaseResponse<GetUserInfoBo> retriveUser(UsernameIbo usernameIbo);

    @PostMapping(value = "/user/register")
    BaseResponse<Boolean> register(AddUserIbo addUserIbo);
}
