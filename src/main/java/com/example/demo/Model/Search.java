package com.example.demo.Model;

public class Search {
    private String searchVariable="";
    private Integer pageNo;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public String getSearchVariable() {
        return searchVariable;
    }

    public void setSearchVariable(String searchVariable) {
        this.searchVariable = searchVariable;
    }

    @Override
    public String toString() {
        return "Search{" +
                "searchVariable='" + searchVariable + '\'' +
                ", pageNo=" + pageNo +
                '}';
    }
}
