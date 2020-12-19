package com.client.core.Bo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class GetUserInfoBo {
    private String Username;
    private String password;
    private String nickName;
    private List<Integer> roleIds;
}
