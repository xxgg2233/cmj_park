package com.cmj.park.dao;

import com.cmj.park.domain.entity.Car;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * 车辆信息mapper
 *
 */
@Mapper
public interface CarMapper {
    // 根据id查询车辆
    @Select("SELECT * FROM cmj_park.t_car_msg WHERE id = #{id}")
    public Car selectById(Integer id);

    // 根据分类，关键词，作者查询最新文章
    public List<Car> selectNew(Car car);

    // 统计文章数量
    @Select("SELECT COUNT(*) FROM cmj_park.t_car_msg")
    public int count();

    // 添加文章
    public int insert(Car car);

    // 通过id删除文章
    @Delete("DELETE FROM cmj_park.t_car_msg WHERE id = #{id}")
    public int delete(Integer id);

    // 更新文章
    public int update(Car car);

    //查询对应值是否存在
    @Select("<script> select count(*) from cmj_park.t_car_msg where ${columnName} = #{carNumber} <if test=\" id != null\"> and id not in (#{id})</if> </script> ")
    public int selOnly(String columnName,String carNumber,Integer id);
}
