package com.cmj.park.web.controller.admin;

import com.cmj.park.domain.entity.CardMoney;
import com.cmj.park.domain.entity.RecordMsg;
import com.cmj.park.domain.vo.AjaxResult;
import com.cmj.park.service.admin.IPayFeeService;
import com.cmj.park.validation.ValidGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 付费控制
 *
 * 权限管理员
 */
@RestController
@RequestMapping("/park/admin/payFee")
@Validated
@PreAuthorize("hasAnyRole('ROLE_ADMIN_SUPER')")
public class PayFeeController {
    @Autowired
    private IPayFeeService iPayFeeService;

    /**
     * 查询余额
     * @param carNum 车牌号
     * @return 操作结果
     */
    @GetMapping("/getMoney/{carNum}")
    public AjaxResult getMoney(@PathVariable String carNum) {
        CardMoney cardMsg = iPayFeeService.selCard(carNum);
        return AjaxResult.success(cardMsg.getCardMoney());
    }

    /**
     * 停车付费
     * @param recordMsg 停车记录表单
     * @return 操作结果
     */
    @PutMapping("/pay")
    public AjaxResult payFee(@Validated(ValidGroup.Crud.Update.class) @RequestBody RecordMsg recordMsg) {
        CardMoney cardMsg = iPayFeeService.selCard(recordMsg.getCarNumber());
        if (cardMsg == null) {
            return AjaxResult.error("请先添加缴费卡");
        } else if (cardMsg.getCardMoney() < recordMsg.getMoney()) {
            return AjaxResult.error("持卡人：" + cardMsg.getCardOwner() + "  卡号：" + cardMsg.getCardNumber() + "余额不足,请先充值");
        } else if (recordMsg.getMoney() < 0) {
            return AjaxResult.error("输入的金额为负数，无法扣除");
        } else {
            //记录停车用户
            recordMsg.setOwner(cardMsg.getCardOwner());
            return AjaxResult.updateResult(iPayFeeService.payFee(recordMsg), "停车缴费");
        }
    }
}
