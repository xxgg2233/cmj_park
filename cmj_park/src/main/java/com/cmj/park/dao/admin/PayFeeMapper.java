package com.cmj.park.dao.admin;

import com.cmj.park.domain.entity.CardMoney;
import com.cmj.park.domain.entity.RecordMsg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


/**
 * 付费 Mapper
 *
 */
@Mapper
public interface PayFeeMapper {
    //扣除对应用户的余额
    @Update("update t_card_money set card_money=ROUND(card_money - #{money},2) where card_owner = #{owner}")
    public int updateCardMoney(RecordMsg recordMsg);

    //根据车牌查询缴费卡
    @Select("select * from t_card_money where card_owner in(select car_owner from t_car_msg where car_number = #{carNumber})")
    public CardMoney selCard(String carNumber);
}
