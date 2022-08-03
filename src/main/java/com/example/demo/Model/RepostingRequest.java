package com.example.demo.Model;

import java.util.List;

public class RepostingRequest {
    private List<String> txnList;
    private String username;
    private String appKey;

    public List<String> getTxnList() {
        return txnList;
    }

    public void setTxnList(List<String> txnList) {
        this.txnList = txnList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    @Override
    public String toString() {
        return "RepostingRequest{" +
                "txnList=" + txnList +
                ", username='" + username + '\'' +
                ", appKey='" + appKey + '\'' +
                '}';
    }
}
