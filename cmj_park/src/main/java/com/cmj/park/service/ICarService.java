package com.cmj.park.service;

import com.cmj.park.domain.entity.Car;
import com.cmj.park.domain.vo.AjaxResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 车辆信息实现接口
 */
public interface ICarService {
    /**
     * 根据关键字查询车辆信息
     * @param car 关键字
     * @return 车辆信息
     */
    List<Car> listPage(Car car);

    /**
     * 根据id查询车辆
     * @param id 车辆id
     * @return 车辆信息
     */
    Car getById(Integer id);

    /**
     * 根据id删除车辆
     * @param ids 车辆id
     * @return 变化条目数
     */
    int delete(Integer[] ids);

    /**
     * 添加车辆
     * @param car 车辆信息表单
     * @return 变化条目数
     */
    int add(Car car);

    /**
     * 更新车辆信息
     * @param car 车辆信息
     * @return 变化条目数
     */
    int update(Car car);

    /**
     * 图片上传校验
     * @param file 图片
     * @return 校验结果
     */
    AjaxResult uploadCheck(MultipartFile file);

    /**
     * 上传图片到本地 c:/upload
     * @param file 图片
     * @return 操作结果
     */
    AjaxResult upload(MultipartFile file);

    /**
     * 查询是否存在字段数据
     * @param columnName 字段名
     * @param carNumber 车牌号
     * @param id 车辆id
     * @return true or false
     */
    public boolean selIsOnly(String columnName,String carNumber,Integer id);
}
