package com.cmj.park.service.admin;

import com.cmj.park.domain.entity.Announ;

/**
 * 公告实现接口
 */
public interface IAnnounService {
    /**
     * 查询公告接口
     * @return 公告信息
     */
    public Announ getAnnoun();

    /**
     * 更新公告接口
     * @param announ 公告表单
     * @return 变化条目数
     */
    int setAnnoun(Announ announ);
}
