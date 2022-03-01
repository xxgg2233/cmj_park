package com.cmj.park.web.controller;

import com.cmj.park.domain.entity.Car;
import com.cmj.park.domain.vo.AjaxResult;
import com.cmj.park.service.ICarService;
import com.cmj.park.service.IParkService;
import com.cmj.park.util.IsOnlyUtil;
import com.cmj.park.util.SecurityUtil;
import com.cmj.park.validation.ValidGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
/**
 * 车辆信息
 *
 * 普通用户操作权限
 */
@RestController
@RequestMapping("/park/member/car")
@Validated
@PreAuthorize("hasAnyRole('ROLE_USER_COMMON')")
public class CarController {
    @Autowired
    private ICarService iCarService;
    @Autowired
    private IParkService iParkService;

    /**
     * 获取车辆信息
     * @param car
     * @return 车辆信息列表
     */
    @GetMapping("/listPage")
    public AjaxResult listPage(Car car) {
        return AjaxResult.pageResult(iCarService.listPage(car));
    }

    /**
     * 根据车辆id查询车辆
     * @param carId
     * @return 车辆信息列表
     */
    @GetMapping("/{carId}")
    public AjaxResult get(@NotNull @PathVariable Integer carId) {
        Car car = iCarService.getById(carId);
        return AjaxResult.success(car);
    }

    /**
     * 根据车主查询车辆信息
     * @param car
     * @return 车辆信息列表
     */
    @GetMapping("/listMemberPage")
    public AjaxResult listMemberPage(Car car) {
        // 设置车主查询条件
        car.setCarOwner(SecurityUtil.getUsername());
        return AjaxResult.pageResult(iCarService.listPage(car));
    }

    /**
     * 根据id删除车辆信息
     * @param ids
     * @return 操作结果
     */
    @DeleteMapping("/{ids}")// /park/article/1,2,3
    public AjaxResult delete(@NotEmpty(message = "车辆信息id数组不能为空") @PathVariable Integer[] ids) {
        return AjaxResult.updateResult(iCarService.delete(ids), "删除车辆信息");
    }

    /**
     * 添加车辆信息
     * @param car 车辆信息
     * @param file 图片
     * @return 操作结果
     */
    @PostMapping("/add")
    public AjaxResult add(@Validated(ValidGroup.Crud.Create.class) @RequestPart(value = "car") Car car, @RequestParam(value = "file") MultipartFile file) {
        if (IsOnlyUtil.justOne(iCarService, car) != null) {//判断车牌是否唯一值
            return IsOnlyUtil.justOne(iCarService, car);
        } else if (iCarService.uploadCheck(file) != null) {//检查图片是否有问题
            return iCarService.uploadCheck(file);
        } else {//设置图片名并更新数据
            car.setCarPhoto(iCarService.upload(file).get("data").toString());
            return AjaxResult.updateResult(iCarService.add(car), "添加车辆信息");
        }
    }

    /**
     * 更新车辆信息
     * @param car 车辆信息
     * @param file 图片
     * @return 操作结果
     */
    @PutMapping("/update")
    public AjaxResult update(@Validated(ValidGroup.Crud.Update.class) @RequestPart(value = "car") Car car, @RequestParam(value = "file") MultipartFile file) {
        if (iParkService.selCarNIsOnly(iCarService.getById(car.getId()).getCarNumber()) != null) {//判断原车牌是否已经预约过
            return AjaxResult.error("请先取消预约之后再修改车牌号");
        } else if (IsOnlyUtil.justOne(iCarService, car) != null) {//判断车牌是否唯一值
            return IsOnlyUtil.justOne(iCarService, car);
        } else if (iCarService.uploadCheck(file) != null) {//检查图片是否有问题
            return iCarService.uploadCheck(file);
        } else {//设置图片名并更新数据
            car.setCarPhoto(iCarService.upload(file).get("data").toString());
            return AjaxResult.updateResult(iCarService.update(car), "修改车辆信息");
        }
    }
}
