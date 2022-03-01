package com.cmj.park.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 停车记录
 */
public class RecordMsg {
    private Integer id;
    private String carNumber;//车牌
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy-MM-dd'T'hh:mm")
    private Date carStart;//停车开始时间
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy-MM-dd'T'hh:mm")
    private Date carEnd;//结束时间
    private double hourMoney;//每小时收费
    private double time;//停车时长
    private double money;//收费金额
    private String owner;//记录用户

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public Date getCarStart() {
        return carStart;
    }

    public void setCarStart(Date carStart) {
        this.carStart = carStart;
    }

    public Date getCarEnd() {
        return carEnd;
    }

    public void setCarEnd(Date carEnd) {
        this.carEnd = carEnd;
    }

    public double getHourMoney() {
        return hourMoney;
    }

    public void setHourMoney(double hourMoney) {
        this.hourMoney = hourMoney;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
