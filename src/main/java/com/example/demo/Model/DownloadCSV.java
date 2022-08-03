package com.example.demo.Model;

import java.util.List;

public class DownloadCSV {
    private  String search;



    private List orgCodes;
    private String startDAte;
    private String endDate;

    public List getOrgCodes() {
        return orgCodes;
    }

    public void setOrgCodes(List orgCodes) {
        this.orgCodes = orgCodes;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }



    public String getStartDAte() {
        return startDAte;
    }

    public void setStartDAte(String startDAte) {
        this.startDAte = startDAte;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "DownloadCSV{" +
                "search='" + search + '\'' +
                ", orgCodes=" + orgCodes +
                ", startDAte='" + startDAte + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
