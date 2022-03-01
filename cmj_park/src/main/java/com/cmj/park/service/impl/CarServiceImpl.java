package com.cmj.park.service.impl;

import com.cmj.park.constant.RespCode;
import com.cmj.park.dao.CarMapper;
import com.cmj.park.domain.entity.Car;
import com.cmj.park.domain.vo.AjaxResult;
import com.cmj.park.exception.CustomException;
import com.cmj.park.service.ICarService;
import com.cmj.park.util.ContentUtil;
import com.cmj.park.util.FileSaveUtil;
import com.cmj.park.util.PageUtil;
import com.cmj.park.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 车辆信息实现类
 */
@Service
public class CarServiceImpl implements ICarService {
    @Autowired
    private CarMapper carMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    // 车辆信息的 redis key
    private static final String ARTICLE_REDIS_KEY = "car:";

    /**
     * 根据关键字查询车辆信息
     * @param car 车辆信息
     * @return 车辆信息
     */
    @Override
    public List<Car> listPage(Car car) {
        PageUtil.start();
        List<Car> carList = carMapper.selectNew(car);
        return carList;
    }

    /**
     * 根据id查询车辆信息
     * @param id 车辆id
     * @return 变化条目数
     */
    @Override
    public Car getById(Integer id) {
        Car car = (Car) redisTemplate.opsForValue().get(ARTICLE_REDIS_KEY + id);
        if (car == null) {
            car = carMapper.selectById(id);
            // 将表情code还原为4字节字符
            // 笑脸 4字节 存到3个字节 :dlsjfioe:
            if (car.getNote() != null) {
                car.setNote(ContentUtil.code2emoji(car.getNote()));
            }
            redisTemplate.opsForValue().set(ARTICLE_REDIS_KEY + id, car);
        }
        return car;
    }

    /**
     * 根据id删除车辆
     * @param ids 车辆id
     * @return 变化条目数
     */
    @Transactional
    @Override
    public int delete(Integer[] ids) {
        String username = SecurityUtil.getUsername();
        int result = 0;
        for (Integer id : ids) {
            Car car = carMapper.selectById(id);
            if (!username.equals(car.getCarOwner()))
                throw new CustomException("不能删除他人车辆信息", RespCode.FORBIDDEN);
            result += carMapper.delete(id);
            // 删除缓存
            redisTemplate.delete("ARTICLE_REDIS_KEY" + id);
        }
        return result;
    }

    /**
     * 添加车辆信息
     * @param car 车辆信息表单
     * @return 变化条目数
     */
    @Transactional
    @Override
    public int add(Car car) {
        // 将表情字符转为表情code
        if (car.getNote() != null) {
            car.setNote(ContentUtil.emoji2code(car.getNote()));
        }
        //设置车主名字
        car.setCarOwner(SecurityUtil.getUsername());
        // 插入车辆信息
        int rows = carMapper.insert(car);

        return rows;
    }

    /**
     * 更新车辆信息
     * @param car 车辆信息
     * @return 变化条目数
     */
    @Transactional
    @Override
    public int update(Car car) {
        Car a = carMapper.selectById(car.getId());
        if (!SecurityUtil.getUsername().equals(a.getCarOwner()))
            throw new CustomException("不能修改他人车辆信息", RespCode.FORBIDDEN);
        // 转换表情
        if (car.getNote()!=null)
            car.setNote(ContentUtil.emoji2code(car.getNote()));
        int rows = carMapper.update(car);
        // 清除缓存
        redisTemplate.delete(ARTICLE_REDIS_KEY + car.getId());
        return rows;
    }

    /**
     * 图片格式校验
     * @param file 图片
     * @return 操作结果
     */
    @Override
    public AjaxResult uploadCheck(MultipartFile file) {
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1, file.getOriginalFilename().length());
        if (file == null) {
            return AjaxResult.error(0, "请选择要上传的图片");
        } else if (file.getSize() > 1024 * 1024 * 1) {
            return AjaxResult.error(0, "文件大小不能大于1M");
        } else if (!"jpg,jpeg,bmp,png".toUpperCase().contains(suffix.toUpperCase())) {//获取文件后缀
            return AjaxResult.error(0, "请选择jpg,jpeg,bmp,png格式的图片");
        } else { //返回文件名称
            return null;
        }
    }

    /**
     * 获取上传的图片，并且更改名字存储
     */
    @Value("${upload-path}")
    private String UPLOAD_FOLDER;

    /**
     * 上传图片
     * @param file 图片
     * @return 存储后文件名
     */
    @Override
    public AjaxResult upload(MultipartFile file) {
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1, file.getOriginalFilename().length());
        return AjaxResult.success("文件名", FileSaveUtil.SaveFile(file, UPLOAD_FOLDER, suffix));
    }

    /**
     * 查询是否是唯一值
     * @param columnName 字段名
     * @param carNumber 车牌号
     * @param id 车辆id
     * @return true or false
     */
    @Override
    public boolean selIsOnly(String columnName, String carNumber, Integer id) {
        if (carMapper.selOnly(columnName, carNumber, id) != 0) {
            return true;
        } else {
            return false;
        }
    }
}
