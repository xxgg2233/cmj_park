package com.cmj.park.web.controller;

import com.cmj.park.domain.entity.RecordMsg;
import com.cmj.park.domain.vo.AjaxResult;
import com.cmj.park.service.IRecordService;
import com.cmj.park.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 停车记录
 *
 * 普通用户操作权限
 */
@RestController
@Validated
public class RecordController {
    @Autowired
    private IRecordService iRecordService;

    /**
     * 根据车主查询停车记录
     * @param recordMsg
     * @return 停车就记录
     */
    @GetMapping("/park/member/record/listMemberPage")
    @PreAuthorize("hasAnyRole('ROLE_USER_COMMON')")
    public AjaxResult listMemberPage(RecordMsg recordMsg) {
        // 设置作者查询条件
        recordMsg.setOwner(SecurityUtil.getUsername());
        return AjaxResult.pageResult(iRecordService.listPage(recordMsg));
    }

    /**
     * 获取所有停车记录
     * @param recordMsg
     * @return 所有停车记录
     */
    @GetMapping("/park/admin/AllRec")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_SUPER')")
    public AjaxResult AllRec(RecordMsg recordMsg) {
        return AjaxResult.pageResult(iRecordService.listPage(recordMsg));
    }
}
