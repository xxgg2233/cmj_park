package com.cmj.park.service.admin.impl;

import com.cmj.park.dao.ParkMapper;
import com.cmj.park.dao.RecordMapper;
import com.cmj.park.dao.admin.PayFeeMapper;
import com.cmj.park.domain.entity.CardMoney;
import com.cmj.park.domain.entity.ParkMsg;
import com.cmj.park.domain.entity.RecordMsg;
import com.cmj.park.service.admin.IPayFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 付费服务实现类
 */
@Service
public class PayFeeServiceImpl implements IPayFeeService {
    @Autowired
    private ParkMapper parkMapper;
    @Autowired
    private RecordMapper recordMapper;
    @Autowired
    private PayFeeMapper payFeeMapper;

    /**
     * 停车付费结算实现
     * @param recordMsg 停车记录
     * @return 变化条目数
     */
    @Transactional
    @Override
    public int payFee(RecordMsg recordMsg) {
        int row = 0;

        //扣除缴费卡中的余额
        row = payFeeMapper.updateCardMoney(recordMsg);

        //把预约的车位设为空
        ParkMsg parkMsg = new ParkMsg();
        parkMsg.setId(recordMsg.getId());
        row = row + parkMapper.update(parkMsg);

        //写入停车记录中
        row = row + recordMapper.addRecord(recordMsg);

        return row;
    }

    /**
     * 根据车牌号查询缴费卡
     * @param carNumber 车牌号
     * @return 缴费卡信息
     */
    @Override
    public CardMoney selCard(String carNumber) {
        return payFeeMapper.selCard(carNumber);
    }
}
