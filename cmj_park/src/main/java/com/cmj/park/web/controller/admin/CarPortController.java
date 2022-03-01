package com.cmj.park.web.controller.admin;

import com.cmj.park.domain.entity.ParkMsg;
import com.cmj.park.domain.vo.AjaxResult;
import com.cmj.park.service.IParkService;
import com.cmj.park.util.IsOnlyUtil;
import com.cmj.park.validation.ValidGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;

/**
 * 车位信息修改
 *
 * 权限管理员
 */
@RestController
@RequestMapping("/park/admin/carPortMsg")
@Validated
@PreAuthorize("hasAnyRole('ROLE_ADMIN_SUPER')")
public class CarPortController {
    @Autowired
    private IParkService iParkService;

    /**
     * 获取车位信息
     * @param parkMsg 车位表单
     * @return 车位信息
     */
    @GetMapping("/listPark")
    public AjaxResult listPark(ParkMsg parkMsg) {
        return AjaxResult.pageResult(iParkService.listPark(parkMsg));
    }

    /**
     * 删除车位信息
     * @param ids 车位id
     * @return 操作结果
     */
    @DeleteMapping("/delete/{ids}")
    public AjaxResult delete(@NotEmpty(message = "车位ids不能为空") @PathVariable Integer[] ids) {
        return AjaxResult.updateResult(iParkService.delete(ids), "删除车位");
    }

    /**
     * 添加车位
     * @param parkMsg 车位表单
     * @return 操作结果
     */
    @PostMapping("/add")
    public AjaxResult add(@Validated(ValidGroup.Crud.Create.class) @RequestBody ParkMsg parkMsg) {
        if (IsOnlyUtil.justOne(iParkService, parkMsg) != null) {//判断车位信息是否唯一
            return IsOnlyUtil.justOne(iParkService, parkMsg);
        } else {//添加数据
            return AjaxResult.updateResult(iParkService.add(parkMsg), "添加车位");
        }
    }

    /**
     * 更新车位信息
     * @param parkMsg 车位表单
     * @return 操作结果
     */
    @PutMapping("/update")
    public AjaxResult update(@Validated(ValidGroup.Crud.Update.class) @RequestBody ParkMsg parkMsg) {
        if (IsOnlyUtil.justOne(iParkService, parkMsg) != null) {//判断车位信息是否唯一
            return IsOnlyUtil.justOne(iParkService, parkMsg);
        } else {//添加数据
            return AjaxResult.updateResult(iParkService.update(parkMsg), "更新车位");
        }
    }
}
