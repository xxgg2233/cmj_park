package com.cmj.park.web.controller;

import com.cmj.park.constant.Constants;
import com.cmj.park.domain.vo.AjaxResult;
import com.cmj.park.domain.vo.LoggedInUser;
import com.cmj.park.domain.vo.LoginForm;
import com.cmj.park.service.ILoginService;
import com.cmj.park.service.impl.TokenService;
import com.cmj.park.util.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录验证
 *
 */
@RestController
@Validated
public class LoginController {
    @Autowired
    private ILoginService loginService;

    @Autowired
    private TokenService tokenService;

    /**
     * 登录方法
     * 用户
     * @param loginForm 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@Validated @RequestBody LoginForm loginForm) {
        //重复登录则删除缓存
        LoggedInUser loggedInUser = tokenService.getLoggedInUser(ServletUtil.getRequest());
        if (loggedInUser != null) {
            // 删除用户缓存记录
            tokenService.delLoggedInUser(loggedInUser.getUuid());
        }
        // 生成令牌
        String token = loginService.login(loginForm.getUsername()+"u", loginForm.getPassword(), loginForm.getCode(),
                loginForm.getRememberMe());
        AjaxResult result = AjaxResult.success();
        result.put(Constants.TOKEN, token);
        return result;
    }

    /**
     * 登录方法
     * 管理员
     *
     * @param loginForm 登录信息
     * @return 结果
     */
    @PostMapping("/login/admins")
    public AjaxResult loginAdmin(@Validated @RequestBody LoginForm loginForm) {
        //重复登录则删除缓存
        LoggedInUser loggedInUser = tokenService.getLoggedInUser(ServletUtil.getRequest());
        if (loggedInUser != null) {
            // 删除管理员缓存记录
            tokenService.delLoggedInUser(loggedInUser.getUuid());
        }
        // 生成令牌
        String token = loginService.login(loginForm.getUsername()+"a", loginForm.getPassword(), loginForm.getCode(),
                0);
        AjaxResult result = AjaxResult.success();
        result.put(Constants.TOKEN, token);
        return result;
    }

    /**
     * 获取登录用户信息
     *
     * @return 登录用户信息
     */
    @GetMapping("/getLoggedInUserInfo")
    public AjaxResult getLoggedInUserInfo() {
        LoggedInUser loggedInUser = tokenService.getLoggedInUser(ServletUtil.getRequest());
        AjaxResult ajax = AjaxResult.success();
        ajax.put(Constants.USER_INFO, loggedInUser);
        return ajax;
    }

}
