package com.english.rockGod.user.service.util;

import java.util.Date;

/**
 * Created by bingkun on 2017/7/2.
 */
public class BaseEntity {
    private int id;
    private Date createTime;
    private Date updateTime;
    private boolean isDeleted;

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void check(BaseEntity baseEntity){

    }
}
