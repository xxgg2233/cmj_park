package com.cmj.park.domain.entity;

import org.hibernate.validator.constraints.Length;

/**
 * 车辆实体类
 */
public class Car {
    private Integer id;
    @Length(min = 1, message = "车牌号不能为空")
    private String carNumber;//车牌号
    @Length(min = 1, message = "车品牌不能为空")
    private String carName;//车品牌
    @Length(min = 1, message = "车辆照片不能为空")
    private String carPhoto;//车照
    private String carOwner;//车主
    private String note;//备注

    public Car() {
        super();
    }

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

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarPhoto() {
        return carPhoto;
    }

    public void setCarPhoto(String carPhoto) {
        this.carPhoto = carPhoto;
    }

    public String getCarOwner() {
        return carOwner;
    }

    public void setCarOwner(String carOwner) {
        this.carOwner = carOwner;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", carNumber='" + carNumber + '\'' +
                ", carName='" + carName + '\'' +
                ", carPhoto='" + carPhoto + '\'' +
                ", carOwner='" + carOwner + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
