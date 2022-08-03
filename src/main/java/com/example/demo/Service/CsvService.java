package com.example.demo.Service;

import com.example.demo.Model.Transactions;

import java.io.PrintWriter;
import java.util.List;

public interface CsvService {
    public void downloadCsvFile(PrintWriter printWriter, List<Transactions> transactions);

    List<Transactions> findAllTransactions();


    List<Transactions> findSearchedTransactions(String searchVariable);





    List<Transactions> findFilteredTransactions1(String startDate, String endDate, List<String> orgCodesList);
}
