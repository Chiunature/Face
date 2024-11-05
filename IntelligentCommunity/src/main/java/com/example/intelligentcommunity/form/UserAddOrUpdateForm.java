package com.example.intelligentcommunity.form;

import lombok.Data;

@Data
public class UserAddOrUpdateForm {
    private Integer userId;
    private String username;
    private String password;
    private String realName;
    private String mobile;
    private Integer status;
    private Integer roleId;
    private String contact;
}
