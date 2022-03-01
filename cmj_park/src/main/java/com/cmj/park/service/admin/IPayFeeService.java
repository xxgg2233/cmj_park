package com.cmj.park.service.admin;

import com.cmj.park.domain.entity.CardMoney;
import com.cmj.park.domain.entity.RecordMsg;

/**
 * 付费服务类接口
 *
 */
public interface IPayFeeService {
    /**
     * 缴费
     * @param recordMsg 停车记录
     * @return 变化条目数
     */
    int payFee(RecordMsg recordMsg);

    /**
     * 根据车牌号查询卡号
     * @param carNumber 车牌号
     * @return 卡号信息
     */
    CardMoney selCard(String carNumber);
}
