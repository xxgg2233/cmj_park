package com.cmj.park.domain.entity;

import org.hibernate.validator.constraints.Length;

/**
 * 缴费卡实体类
 */
public class CardMoney {
    private Integer id;
    @Length(min = 1, message = "卡号不能为空")
    private String cardNumber;
    @Length(min = 1, message = "余额不能为空")
    private double cardMoney;
    @Length(min = 1, message = "卡主不能为空")
    private String cardOwner;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public double getCardMoney() {
        return cardMoney;
    }

    public void setCardMoney(double cardMoney) {
        this.cardMoney = cardMoney;
    }

    public String getCardOwner() {
        return cardOwner;
    }

    public void setCardOwner(String cardOwner) {
        this.cardOwner = cardOwner;
    }
}
