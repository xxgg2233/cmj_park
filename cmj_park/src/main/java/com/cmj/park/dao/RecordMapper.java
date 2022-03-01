package com.cmj.park.dao;

import com.cmj.park.domain.entity.RecordMsg;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 *  停车记录Mapper
 *
 */
@Mapper
public interface RecordMapper {

    /**
     * 根据车主查询记录
     * @param recordMsg 车主
     * @return 停车记录
     */
    public List<RecordMsg> selectNew(RecordMsg recordMsg);

    /**
     * 添加记录
     * @param recordMsg 停车记录表单
     * @return 变化条目数
     */
    public int addRecord(RecordMsg recordMsg);

}
