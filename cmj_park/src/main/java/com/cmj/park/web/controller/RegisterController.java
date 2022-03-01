package com.cmj.park.web.controller;


import com.cmj.park.domain.entity.User;
import com.cmj.park.domain.vo.AjaxResult;
import com.cmj.park.service.IUserService;
import com.cmj.park.util.IsOnlyUtil;
import com.cmj.park.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 注册
 *
 */
@RestController
@RequestMapping("/park/register")
@Validated
public class RegisterController {
    @Autowired
    private IUserService userService;

    /**
     * 添加用户
     * @param user
     * @return
     */
    @GetMapping("/insert")
    public AjaxResult insert(User user) {
        if (IsOnlyUtil.justOne(userService,user) == null){
            //加密密码
            user.setPassword(SecurityUtil.encryptPassword(user.getPassword()));
            return AjaxResult.updateResult(userService.insert(user), "注册");
        }else {
            return IsOnlyUtil.justOne(userService,user);
        }
    }
}
