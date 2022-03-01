package com.cmj.park.web.controller;

import com.cmj.park.domain.entity.ParkMsg;
import com.cmj.park.domain.vo.AjaxResult;
import com.cmj.park.service.IParkService;
import com.cmj.park.validation.ValidGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 停车位
 *
 * 普通用户操作权限
 */
@RestController
@RequestMapping("/park/member/park")
@Validated
@PreAuthorize("hasAnyRole('ROLE_USER_COMMON')")
public class ParkController {
    @Autowired
    private IParkService iParkService;

    /**
     * 停车位信息
     * @param parkMsg 停车位表单
     * @return 停车位列表
     */
    @GetMapping("/listPage")
    public AjaxResult listPage(ParkMsg parkMsg) {
        return AjaxResult.pageResult(iParkService.listPark(parkMsg));
    }

    /**
     * 更新预约状态
     * @param parkMsg
     * @return 预约结果
     */
    @PutMapping
    public AjaxResult update(@Validated(ValidGroup.Crud.Update.class) @RequestBody ParkMsg parkMsg) {
        if (parkMsg.getNote().equals("0")) {
            return AjaxResult.updateResult(iParkService.updateParkCar(parkMsg), "取消预约车位");
        } else if (iParkService.selCarNIsOnly(parkMsg.getCarNumber()) == null) {
            return AjaxResult.updateResult(iParkService.updateParkCar(parkMsg), "预约车位");
        } else {
            return AjaxResult.error("该车已经预约过" + iParkService.selCarNIsOnly(parkMsg.getCarNumber()) + "号车位");
        }
    }
}
