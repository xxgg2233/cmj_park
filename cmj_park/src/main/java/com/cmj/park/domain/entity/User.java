package com.cmj.park.domain.entity;

import javax.validation.constraints.Email;

/**
 * 用户实体类
 */
public class User {
    private Integer id;
    private String username;//用户名
    private String realname;//真实姓名
    private String password;//密码
    private String phone;//手机号码
    @Email(message = "邮箱格式不正确")
    private String email;//邮箱

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
