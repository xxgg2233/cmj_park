package com.cmj.park.service.impl;

import com.cmj.park.constant.RespCode;
import com.cmj.park.dao.CarMapper;
import com.cmj.park.dao.ParkMapper;
import com.cmj.park.domain.entity.Car;
import com.cmj.park.domain.entity.ParkMsg;
import com.cmj.park.exception.CustomException;
import com.cmj.park.service.IParkService;
import com.cmj.park.util.ContentUtil;
import com.cmj.park.util.PageUtil;
import com.cmj.park.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 停车位信息实现类
 */
@Service
public class ParkServiceImpl implements IParkService {
    @Autowired
    private ParkMapper parkMapper;
    @Autowired
    private CarMapper carMapper;

    /**
     * 根据关键字查询停车位信息
     * @param parkMsg 车位表单
     * @return 车位信息
     */
    @Override
    public List<ParkMsg> listPark(ParkMsg parkMsg) {
        PageUtil.start();
        //去掉空格
        if (parkMsg.getParkNumber() != null)
            parkMsg.setParkNumber(parkMsg.getParkNumber().trim());
        List<ParkMsg> carList = parkMapper.selectNew(parkMsg);
        return carList;
    }

    /**
     * 根据id删除车位
     * @param ids 车位id
     * @return 变化条目数
     */
    @Transactional
    @Override
    public int delete(Integer[] ids) {
        int result = 0;
        for (Integer id : ids) {
            result += parkMapper.delete(id);
        }
        return result;
    }

    /**
     * 添加车位信息
     * @param parkMsg 车位表单
     * @return 变化条目数
     */
    @Transactional
    @Override
    public int add(ParkMsg parkMsg) {
        // 将表情字符转为表情code
        if (parkMsg.getNote() != null) {
            parkMsg.setNote(ContentUtil.emoji2code(parkMsg.getNote()));
        }
        // 插入车位
        int rows = parkMapper.insert(parkMsg);

        return rows;
    }

    /**
     * 更新车位信息
     * @param parkMsg
     * @return
     */
    @Transactional
    @Override
    public int update(ParkMsg parkMsg) {
        if (parkMsg.getNote() != null) {
            // 转换表情
            parkMsg.setNote(ContentUtil.emoji2code(parkMsg.getNote()));
        }
        int rows = parkMapper.updatePark(parkMsg);
        return rows;
    }

    /**
     * 更新车位预约状态
     * @param parkMsg 车位表单
     * @return 变化条目数
     */
    @Override
    @Transactional
    public int updateParkCar(ParkMsg parkMsg) {
        String username = SecurityUtil.getUsername();
        Car car = new Car();
        car.setCarNumber(parkMsg.getCarNumber());
        List<Car> carList = carMapper.selectNew(car);
        if (!username.equals(carList.get(0).getCarOwner()))
            throw new CustomException("不能修改他人预约状态信息", RespCode.FORBIDDEN);
        if (parkMsg.getNote().equals("0")) {
            parkMsg.setCarNumber(null);
        }
        return parkMapper.update(parkMsg);
    }

    /**
     * 查询车牌号是否唯一
     * @param carNumber 车牌号
     * @return notnull or null
     */
    @Override
    public String selCarNIsOnly(String carNumber) {
        if (parkMapper.selCarNIsOnly(carNumber) != null) {
            return parkMapper.selCarNIsOnly(carNumber);
        } else {
            return null;
        }
    }

    /**
     * 查询是否是唯一值
     * @param columnName 字段名
     * @param parkNumber 车位号
     * @param id 车位id
     * @return true or false
     */
    @Override
    public boolean selIsOnly(String columnName, String parkNumber, Integer id) {
        if (parkMapper.selOnly(columnName, parkNumber, id) != 0) {
            return true;
        } else {
            return false;
        }
    }

}
