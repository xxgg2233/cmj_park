package com.cmj.park.domain.vo;

/**
 * 获取旧密码实体类
 */
public class Pwd {
    private int id;//id
    private String oldPwd;//旧密码
    private String encodePwd;//旧编码
    private String newPwd;//新密码

    public String getEncodePwd() {
        return encodePwd;
    }

    public void setEncodePwd(String encodePwd) {
        this.encodePwd = encodePwd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }
}
