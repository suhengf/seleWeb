package com.auto.entity;

/**
 * @Description:用户信息实体类
 * @Author:Su-Heng
 * @Date:2020/11/02 03:43
 * @Version 1.0
 **/
public class UserInfo {

    private String userId;

    private String password;

    @Override
    public String toString() {
        return "UserInfo{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
