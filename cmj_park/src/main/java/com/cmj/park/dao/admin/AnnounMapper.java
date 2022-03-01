package com.cmj.park.dao.admin;

import com.cmj.park.domain.entity.Announ;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


/**
 * 公告 Mapper
 *
 */
@Mapper
public interface AnnounMapper {

    // 查询公告
    @Select("select * from t_announ")
    public Announ selAnnoun();

    // 更新公告
    @Update("update t_announ set title=#{title}, content=#{content}, updateTime=#{updateTime} where id = 1")
    public int setAnnoun(Announ announ);
}
