package com.auto.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {
    private String url;

    private String iframe;

    private String userNameLoc;

    private String passWordLoc;

    private String verifyCodeLoc;

    private String loginButtonLoc;
}
