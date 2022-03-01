package com.cmj.park.service;

import com.cmj.park.domain.entity.ParkMsg;

import java.util.List;

/**
 * 停车位信息实现接口
 */
public interface IParkService {
    /**
     * 根据关键字查询车位
     * @param parkMsg 车位信息
     * @return 车位信息
     */
    List<ParkMsg> listPark(ParkMsg parkMsg);

    /**
     * 删除车位
     * @param ids 车位id
     * @return 变化条目数
     */
    int delete(Integer[] ids);

    /**
     * 添加车位
     * @param parkMsg 车位表单
     * @return 变化条目数
     */
    int add(ParkMsg parkMsg);

    /**
     * 更新车位信息
     * @param parkMsg 车位表单
     * @return 变化条目数
     */
    int update(ParkMsg parkMsg);

    /**
     * 更改车位预约状态
     * @param parkMsg 车位表单
     * @return 变化条目数
     */
    int updateParkCar(ParkMsg parkMsg);

    /**
     * 查询车牌号是否预约过
     * @param carNumber 车牌号
     * @return notnull or null
     */
    String selCarNIsOnly(String carNumber);

    /**
     * 查询车位是否存在
     * @param columnName 字段名
     * @param parkNumber 车牌号
     * @param id 车位id
     * @return true or false
     */
    public boolean selIsOnly(String columnName,String parkNumber,Integer id);
}
