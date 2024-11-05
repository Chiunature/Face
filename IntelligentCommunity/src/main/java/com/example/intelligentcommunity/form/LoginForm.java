package com.example.intelligentcommunity.form;

import lombok.Data;

@Data
public class LoginForm {
    private String captcha;
    private String password;
    private String username;
    private String uuid;
}
