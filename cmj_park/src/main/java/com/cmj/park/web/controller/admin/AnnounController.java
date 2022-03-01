package com.cmj.park.web.controller.admin;

import com.cmj.park.domain.entity.Announ;
import com.cmj.park.domain.vo.AjaxResult;
import com.cmj.park.service.admin.IAnnounService;
import com.cmj.park.validation.ValidGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 公告的控制类
 *
 * 权限管理员
*/
@RestController
@RequestMapping("/park")
@Validated
public class AnnounController {
    @Autowired
    private IAnnounService iAnnounService;

    /**
     * 获取公告信息（管理员查看）
     * @return 公告信息
     */
    @GetMapping("/admin/getAnnoun")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_SUPER')")
    public AjaxResult getAnnounAdmin() {
        return AjaxResult.success(iAnnounService.getAnnoun());
    }

    /**
     * 公告信息（用户查看）
     * @return 公告信息
     */
    @GetMapping("/member/getAnnoun")
    public AjaxResult getAnnoun() {
        return AjaxResult.success(iAnnounService.getAnnoun());
    }

    /**
     * 设置公告信息
     * @param announ 公告表单
     * @return 操作结果
     */
    @PutMapping("/admin/setAnnoun")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_SUPER')")
    public AjaxResult setAnnoun(@Validated(ValidGroup.Crud.Update.class) @RequestBody Announ announ) {
        return AjaxResult.updateResult(iAnnounService.setAnnoun(announ), "更新公告");
    }
}
