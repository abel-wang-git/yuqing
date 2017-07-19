package com.zkyf.entity.base;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by wanghuiwen on 17-7-18.
 */
@MappedSuperclass
public class BaseEntity implements Serializable {
    protected Date createDate;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
