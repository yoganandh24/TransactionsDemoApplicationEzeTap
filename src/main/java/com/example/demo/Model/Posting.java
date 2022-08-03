package com.example.demo.Model;

import java.util.List;

public class Posting {

    private List<String> transactionIds;

    public List<String> getTransactionIds() {
        return transactionIds;
    }

    public void setTransactionIds(List<String> transactionIds) {
        this.transactionIds = transactionIds;
    }

    @Override
    public String toString() {
        return "Posting{" +
                "transactionIds=" + transactionIds +
                '}';
    }
}
