package com.zt.base.entity;


import org.springframework.data.annotation.Transient;

public abstract class BasePageRequest {

    @Transient
    private Integer pageNumber;    //页码
    @Transient
    private Integer pageSize;    //每页显示条数

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }


}
