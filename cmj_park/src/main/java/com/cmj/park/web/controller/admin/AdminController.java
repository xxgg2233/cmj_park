package com.cmj.park.web.controller.admin;

import com.cmj.park.domain.entity.Admin;
import com.cmj.park.domain.vo.AjaxResult;
import com.cmj.park.service.admin.IAdminService;
import com.cmj.park.util.IsOnlyUtil;
import com.cmj.park.util.SecurityUtil;
import com.cmj.park.validation.ValidGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;

/**
 * 管理员信息修改
 *
 * 权限管理员
 */
@RestController
@RequestMapping("/park/admin/adminMsg")
@Validated
@PreAuthorize("hasAnyRole('ROLE_ADMIN_SUPER')")
public class AdminController {
    @Autowired
    private IAdminService iAdminService;

    /**
     * 获取管理员信息
     * @param admin
     * @return
     */
    @GetMapping("/listAdmin")
    public AjaxResult listAdmin(Admin admin) {
        return AjaxResult.pageResult(iAdminService.listAdmin(admin));
    }

    /**
     * 删除管理员
     * @param ids 管理员id
     * @return
     */
    @DeleteMapping("/delete/{ids}")
    public AjaxResult delete(@NotEmpty(message = "管理员ids不能为空") @PathVariable Integer[] ids) {
        return AjaxResult.updateResult(iAdminService.delete(ids), "删除管理员");
    }

    /**
     * 添加管理员
     * @param admin
     * @return
     */
    @PostMapping("/add")
    public AjaxResult add(@Validated(ValidGroup.Crud.Create.class) @RequestBody Admin admin) {
        if (IsOnlyUtil.justOne(iAdminService, admin) != null) {//判断管理员信息是否唯一
            return IsOnlyUtil.justOne(iAdminService, admin);
        } else {//添加数据
            admin.setPassword(SecurityUtil.encryptPassword(admin.getPassword()));
            return AjaxResult.updateResult(iAdminService.add(admin), "添加管理员");
        }
    }

    /**
     * 更新管理员信息
     * @param admin
     * @return
     */
    @PutMapping("/update")
    public AjaxResult update(@Validated(ValidGroup.Crud.Update.class) @RequestBody Admin admin) {
        if (IsOnlyUtil.justOne(iAdminService, admin) != null) {//判断管理员信息是否唯一
            return IsOnlyUtil.justOne(iAdminService, admin);
        } else {//添加数据
            admin.setPassword(SecurityUtil.encryptPassword(admin.getPassword()));
            return AjaxResult.updateResult(iAdminService.update(admin), "更新管理员");
        }
    }
}
