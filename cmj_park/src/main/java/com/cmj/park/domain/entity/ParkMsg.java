package com.cmj.park.domain.entity;

/**
 * 车位实体类
 */
public class ParkMsg {
    private Integer id;
    private String parkNumber;//车位号
    private String parkArea;//停车区
    private double fee;//每小时收费
    private String note;//备注
    private String carNumber;//预约车牌号

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getParkNumber() {
        return parkNumber;
    }

    public void setParkNumber(String parkNumber) {
        this.parkNumber = parkNumber;
    }

    public String getParkArea() {
        return parkArea;
    }

    public void setParkArea(String parkArea) {
        this.parkArea = parkArea;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    @Override
    public String toString() {
        return "ParkMsg{" +
                "id=" + id +
                ", parkNumber='" + parkNumber + '\'' +
                ", parkArea='" + parkArea + '\'' +
                ", fee=" + fee +
                ", note='" + note + '\'' +
                ", carNumber='" + carNumber + '\'' +
                '}';
    }
}
