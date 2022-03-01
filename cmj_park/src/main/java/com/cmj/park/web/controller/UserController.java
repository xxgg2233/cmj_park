package com.cmj.park.web.controller;

import com.cmj.park.domain.entity.CardMoney;
import com.cmj.park.domain.entity.User;
import com.cmj.park.domain.vo.AjaxResult;
import com.cmj.park.domain.vo.Pwd;
import com.cmj.park.service.IUserService;
import com.cmj.park.util.IsOnlyUtil;
import com.cmj.park.util.SecurityUtil;
import com.cmj.park.validation.ValidGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * 用户信息
 *
 * 普通用户操作权限
 */
@RestController
@RequestMapping("/park/member/user")
@Validated
@PreAuthorize("hasAnyRole('ROLE_USER_COMMON')")
public class UserController {
    @Autowired
    private IUserService userService;

    /**
     * 获取用户信息
     * @param username 用户名
     * @return
     */
    @GetMapping("/{username}")
    public AjaxResult get(@NotNull @PathVariable String username) {
        User user = userService.getByUsername(username);
        return AjaxResult.success(user);
    }

    /**
     * 更新用户信息
     * @param user 用户表单
     * @return
     */
    @Transactional
    @PutMapping
    public AjaxResult update(@Validated(ValidGroup.Crud.Update.class) @RequestBody User user) {
        if (IsOnlyUtil.justOne(userService,user) == null){
            return AjaxResult.updateResult(userService.update(user), "修改个人信息");
        }else {
            return IsOnlyUtil.justOne(userService,user);
        }
    }

    /**
     * 更新用户密码
     * @param pwd
     * @return
     */
    @Transactional
    @PostMapping
    public AjaxResult updatePwd(@Validated(ValidGroup.Crud.Update.class) @RequestBody Pwd pwd) {
        if (SecurityUtil.matchesPassword(pwd.getOldPwd(), pwd.getEncodePwd())) {
            return AjaxResult.updateResult(userService.updatePwd(pwd.getId(), pwd.getNewPwd()), "修改密码");
        } else {
            return AjaxResult.error("原密码错误");
        }
    }

    /**
     * 获取用户缴费卡信息
     * @param username
     * @return
     */
    @GetMapping("/card/{username}")
    public AjaxResult getCardMsg(@NotNull @PathVariable String username) {
        CardMoney cardMsg = userService.selCMByName(username);
        return AjaxResult.success(cardMsg);
    }

}
