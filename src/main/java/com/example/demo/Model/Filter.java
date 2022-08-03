package com.example.demo.Model;

import java.util.Date;
import java.util.List;

public class Filter {
    private List  orgCodes;
    private Date  startDate;
    private Date  endDate;
    private Integer pageNo;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public List getOrgCodes() {
        return orgCodes;
    }

    public void setOrgCodes(List orgCodes) {
        this.orgCodes = orgCodes;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Filter{" +
                "orgCodes=" + orgCodes +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", pageNo=" + pageNo +
                '}';
    }
}
