package com.cmj.park.service.admin.impl;

import com.cmj.park.dao.admin.AnnounMapper;
import com.cmj.park.domain.entity.Announ;
import com.cmj.park.service.admin.IAnnounService;
import com.cmj.park.util.ContentUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 公告服务实现类
 */
@Service
public class AnnounServiceImpl implements IAnnounService {
    @Autowired
    private AnnounMapper announMapper;

    /**
     * 获取对应的公告
     * @return 公告信息
     */
    @Override
    public Announ getAnnoun() {
        return announMapper.selAnnoun();
    }

    /**
     * 设置公告信息
     * @param announ 公告表单
     * @return 变化条目数
     */
    @Override
    @Transactional
    public int setAnnoun(Announ announ) {
        // 将表情code还原为4字节字符
        // 笑脸 4字节 存到3个字节 :dlsjfioe:
        if (announ.getContent() != null) {
            announ.setContent(ContentUtil.code2emoji(announ.getContent()));
        }
        announ.setUpdateTime(new Date());
        return announMapper.setAnnoun(announ);
    }
}
