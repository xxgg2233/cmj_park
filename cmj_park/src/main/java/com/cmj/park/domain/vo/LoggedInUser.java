package com.cmj.park.domain.vo;

import com.cmj.park.domain.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * 已登录用户信息类
 *
 */
public class LoggedInUser implements UserDetails {

    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;

    /**
     * 登录IP地址
     */
    private String ipAddress;

    /**
     * 登录地点
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    private String uuid;
    private Integer userId;
    private String username;
    private String password;
    private List<Role> roles;
    // 0: 普通用户，1: 管理用户
    private Integer loginType;

    public LoggedInUser() {
    }

    public LoggedInUser(String uuid, Integer userId, String username, String password, List<Role> roles, Integer loginType) {
        this.uuid = uuid;
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.loginType = loginType;
    }

    public Long getLoginTime() {
        return loginTime;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getLoginLocation() {
        return loginLocation;
    }

    public String getOs() {
        return os;
    }

    public String getUuid() {
        return uuid;
    }

    public Integer getUserId() {
        return userId;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public Integer getLoginType() {
        return loginType;
    }

    public String getBrowser() {
        return browser;
    }

    public void setLoginTime(Long loginTime) {
        this.loginTime = loginTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setLoginLocation(String loginLocation) {
        this.loginLocation = loginLocation;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    private List<SimpleGrantedAuthority> authorities;
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities != null){
            return authorities;
        }
        authorities = new ArrayList<>();
        for (Role role : roles) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getCode());
            authorities.add(authority);
        }
        //同上面的效果一致，流写法，较为高级暂时不会
//        List<SimpleGrantedAuthority> collect = list.stream()
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
