package com.example.demo.Service;

import com.example.demo.Controller.RestTransactionsController;
import com.example.demo.Model.Transactions;
import com.example.demo.Repository.TransactionsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class CsvServiceImplementation implements CsvService {

    Logger logger = LoggerFactory.getLogger(CsvServiceImplementation.class);
    @Autowired
    TransactionsRepository transactionsRepository;

    @Override
    public void downloadCsvFile(PrintWriter printWriter, List<Transactions> transactions) {
        printWriter.write("Id, Txn Id, External Ref Number, Posting Status, Posting Count, Txn, Posting Request, Posting Response, Org Code, Created Date, Updated Date, Status, Payment Mode\n");
        for(Transactions transaction : transactions){
            printWriter.write(transaction.getId() + "," + transaction.getTxnId() + "," + transaction.getExternalRefNumber()
                    + "," + transaction.getPostingStatus() + "," + transaction.getPostingCount() + ","
                    + transaction.getTxn() + "," + transaction.getPostingRequest() + "," + transaction.getPostingResponse()
                    + "," + transaction.getOrgCode() + "," + transaction.getCreatedDate() + "," + transaction.getUpdatedDate()
                    + "," + transaction.getStatus() + "," +transaction.getPaymentMode() + "\n");
        }
    }

    @Override
    public List<Transactions> findAllTransactions() {
        logger.trace("Entering find all transactions Service Implementation");
        return transactionsRepository.findAll();
    }

    @Override
    public List<Transactions> findSearchedTransactions(String searchVariable) {
        logger.trace("Entering findSearchedTransactions Service Implementation with Searched Text {}",searchVariable);
        return transactionsRepository.findByTxnIdEqualsOrOrgCodeEquals(searchVariable,searchVariable);
    }

    @Override
    public List<Transactions> findFilteredTransactions1(String startDate, String endDate, List<String> orgCodesList) {
        logger.trace("Entering Filtered Transactions Service Implementation with start date {}, end date{}, OrgCodes{}",startDate,endDate,orgCodesList);
        String startDateIdIndex = "";
        String endDateIdIndex = "";
        if(startDate != ""){
            String date =startDate;
            String year = date.substring(2,4);
            String dateSliced= date.substring(8);
            String  month= date.substring(5,7);
            startDateIdIndex = (year+month+dateSliced);
        }
        if(endDate != ""){
            String date = endDate;
            String year = date.substring(2,4);
            String dateSliced = date.substring(8);
            String month= date.substring(5,7);
            endDateIdIndex = (year+month+dateSliced);

        }

        List<Transactions> filteredPages = null;
        if(orgCodesList.isEmpty() && startDateIdIndex != "" && endDateIdIndex != "" ){
            filteredPages = transactionsRepository.findByTxnIdBetween(startDateIdIndex,endDateIdIndex);

        }
        if( startDateIdIndex=="" && endDateIdIndex ==""  &&  !orgCodesList.isEmpty()){

            filteredPages = transactionsRepository.findByOrgCodeIn(orgCodesList);
        }
        if(startDateIdIndex !="" && endDateIdIndex != ""  && !orgCodesList.isEmpty()){

            filteredPages = transactionsRepository.findByOrgCodeInAndTxnIdBetween(orgCodesList,startDateIdIndex,endDateIdIndex);
        }
        return filteredPages;

    }




}
