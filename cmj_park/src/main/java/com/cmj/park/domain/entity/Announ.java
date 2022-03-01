package com.cmj.park.domain.entity;

import com.cmj.park.validation.ValidGroup;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 公告实体类
 *
 */
public class Announ {
    @NotNull(groups = ValidGroup.Crud.Update.class, message = "id不能为空")
    private Integer id;          //id
    @Length(min = 6, message = "标题最少6个字符")
    private String title;       //公告标题
    @Length(min = 10, message = "内容最少10个字符")
    private String content;     //公告内容
    private Date updateTime;       //修改时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}