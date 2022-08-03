package com.example.demo.Service;

import com.example.demo.Model.DownloadCSV;
import com.example.demo.Model.RepostingResponse;
import com.example.demo.Model.Transactions;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface TransactionsService {
    Page<Transactions> getPaginatedTransactionsWithSearch(String search, Integer page);

    Page<Transactions> getPaginatedTransactions(Integer page, Integer customPageSize);


    Page<Transactions> getPaginatedFilteredTransactions(List organizationCodes, Date startDate, Date endDate, Integer pageInteger);

    List<String> getUniqueOrgCodes();

    RepostingResponse reposting(List<String> txnList);

    List<Transactions> downloadTransactions(DownloadCSV downloadCSV);
}
