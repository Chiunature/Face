package com.example.intelligentcommunity.form;

import lombok.Data;

@Data
public class UpdatePasswordForm {
    private String password;
    private String newPassword;
    private  String confirmPassword;
}
