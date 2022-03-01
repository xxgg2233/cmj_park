package com.cmj.park.domain.entity;

/**
 * 角色类
 *
 */
public class Role {
    private Integer id;//id
    private String name;//权限名
    private String code;//权限代号

    public Role() {
    }

    public Role(Integer id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
