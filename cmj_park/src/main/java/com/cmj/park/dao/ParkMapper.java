package com.cmj.park.dao;

import com.cmj.park.domain.entity.ParkMsg;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * 停车位信息mapper
 *
 */
@Mapper
public interface ParkMapper {

    /**
     * 根据关键字查询车位
     * @param parkMsg 车位表单
     * @return 车位信息
     */
    public List<ParkMsg> selectNew(ParkMsg parkMsg);

    /**
     * 添加车位
     * @param parkMsg 车位表单
     * @return 变化条目数
     */
    public int insert(ParkMsg parkMsg);

    /**
     * 通过id删除车位
     * @param id 车位id
     * @return 变化条目数
     */
    @Delete("DELETE FROM t_park_msg WHERE id = #{id}")
    public int delete(Integer id);

    /**
     * 更新预约车位
     * @param parkMsg 车位表单
     * @return 变化条目数
     */
    public int update(ParkMsg parkMsg);

    /**
     * 更新车位信息
     * @param parkMsg 车位表单
     * @return 变化条目数
     */
    public int updatePark(ParkMsg parkMsg);

    /**
     * 查询是否预约过车位
     * @param carNumber 车牌号码
     * @return true or false
     */
    @Select("SELECT park_number FROM t_park_msg where park_car_number = #{carNumber}")
    public String selCarNIsOnly(String carNumber);

    /**
     * 根据字段名查询数据库是否存在该值
     * @param columnName 字段名
     * @param parkNumber 车位号
     * @param id 车位id
     * @return 查询到的条目数
     */
    @Select("<script> select count(*) from t_park_msg where ${columnName} = #{parkNumber} <if test=\" id != null\"> and id not in (#{id})</if> </script> ")
    public int selOnly(String columnName, String parkNumber,Integer id);
}
