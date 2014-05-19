package com.taskstrategy.commons.domain;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: brian
 * Date: 10/14/13
 * Time: 7:22 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseDomain {

    private Date createDate;
    private Date lastModifiedDate;


    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
