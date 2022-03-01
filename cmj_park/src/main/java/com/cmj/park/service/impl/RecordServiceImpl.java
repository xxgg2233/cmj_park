package com.cmj.park.service.impl;

import com.cmj.park.dao.RecordMapper;
import com.cmj.park.domain.entity.RecordMsg;
import com.cmj.park.service.IRecordService;
import com.cmj.park.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 停车记录实现类
 *
 */
@Service
public class RecordServiceImpl implements IRecordService {
    @Autowired
    private RecordMapper recordMapper;

    /**
     * 根据关键字查询停车记录
     * @param recordMsg 停车记录表单
     * @return 停车记录
     */
    @Override
    public List<RecordMsg> listPage(RecordMsg recordMsg) {
        PageUtil.start();
        List<RecordMsg> recordMsgList = recordMapper.selectNew(recordMsg);
        return recordMsgList;
    }
}
