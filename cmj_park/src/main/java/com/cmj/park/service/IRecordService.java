package com.cmj.park.service;

import com.cmj.park.domain.entity.RecordMsg;

import java.util.List;

/**
 * 停车记录实现接口
 */
public interface IRecordService {
    /**
     * 关键字查询停车记录
     * @param recordMsg
     * @return
     */
    List<RecordMsg> listPage(RecordMsg recordMsg);
}
