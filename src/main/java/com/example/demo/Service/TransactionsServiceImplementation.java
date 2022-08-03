package com.example.demo.Service;

import com.example.demo.Model.DownloadCSV;
import com.example.demo.Model.RepostingRequest;
import com.example.demo.Model.RepostingResponse;
import com.example.demo.Model.Transactions;
import com.example.demo.Repository.TransactionsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.demo.Constants.EndPoints.REPOSTING_POST_URL;
import static com.example.demo.Constants.StringConstants.*;

@Service
public class TransactionsServiceImplementation implements TransactionsService {

    Logger logger = LoggerFactory.getLogger(TransactionsServiceImplementation.class);
    @Value("${reposting.userName}")
    private String userName;

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Override
    public Page<Transactions> getPaginatedTransactionsWithSearch(String search, Integer page){
        logger.trace("Entering getTransactions with search Implementation method");
        Integer pageSize = 25;
        Pageable pageable = PageRequest.of(page,pageSize);
        Page<Transactions>transactionsWithSearch = transactionsRepository.findByTxnIdEqualsOrOrgCodeEquals(pageable,search,search);
        return transactionsWithSearch;
    }

    @Override
    public Page<Transactions> getPaginatedTransactions(Integer page, Integer customPageSize){
        logger.trace("Entering  get Paginated unsuccessful Transactions");
        Integer pageSize=customPageSize;
        Pageable pageable= PageRequest.of(page,pageSize);
        Page<Transactions> unsuccessfulTransactions= transactionsRepository.findByStatusIsNot(SUCCESS,pageable);
        return unsuccessfulTransactions;
    }

    @Override
    public Page<Transactions> getPaginatedFilteredTransactions(List organizationCodes, Date startDate, Date endDate, Integer pageInteger){
        logger.trace("Entering getPaginated Filtered Transactions service Implementation method");
        String startDateIdIndex = "";
        String endDateIdIndex = "";
        if(startDate != null){

            String pattern = "MM-dd-yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String date = simpleDateFormat.format(startDate);
            String year = date.substring(8);
            String dateSliced= date.substring(3,5);
            String  month= date.substring(0,2);
            startDateIdIndex = (year+month+dateSliced);
        }
        if(endDate != null){
            String pattern = "MM-dd-yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String date = simpleDateFormat.format(endDate);
            String year = date.substring(8);
            String dateSliced = date.substring(3,5);
            String month= date.substring(0,2);
            endDateIdIndex = (year+month+dateSliced);

        }


        Integer pageSize=25;
        Integer page=pageInteger;
        Pageable pageable = PageRequest.of(page,pageSize);
        Page<Transactions> filteredPages = null;
        if(organizationCodes.isEmpty() && startDateIdIndex != "" && endDateIdIndex != "" ){
            filteredPages = transactionsRepository.findByTxnIdBetween(pageable,startDateIdIndex,endDateIdIndex);
        }
        else if( startDateIdIndex=="" && endDateIdIndex ==""  &&  !organizationCodes.isEmpty()){
            filteredPages = transactionsRepository.findByOrgCodeIn(pageable,organizationCodes);
        }
        else {
            filteredPages = transactionsRepository.findByOrgCodeInAndTxnIdBetween(pageable,organizationCodes,startDateIdIndex,endDateIdIndex);
        }
        return filteredPages;
    }

    @Override
    public List<String> getUniqueOrgCodes() {
        logger.trace("Entering getUniqueOrgCodes service implementation method");

        List<Transactions> uniqueOrgCodeTransactions=transactionsRepository.findDistinctByOrgCodeIsNot(NULL);
        List<String> uniqueOrgCode=new ArrayList<>();
        for(Transactions transaction: uniqueOrgCodeTransactions){
            if(!uniqueOrgCode.contains(transaction.getOrgCode())){
                uniqueOrgCode.add(transaction.getOrgCode());
            }
        }
        return uniqueOrgCode;
    }

    @Override
    public RepostingResponse reposting(List<String> txnList) {
        logger.trace("Entering reposting service Implementation method");
        RepostingRequest repostingRequest = new RepostingRequest();
        repostingRequest.setTxnList(txnList);
        repostingRequest.setUsername(userName);
        repostingRequest.setAppKey(APP_KEY);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(BEARER_AUTH);

        HttpEntity<RepostingRequest> entity = new HttpEntity<>(repostingRequest,httpHeaders);
        ResponseEntity<RepostingResponse> responseEntity = restTemplate.postForEntity(REPOSTING_POST_URL, entity, RepostingResponse.class);
        RepostingResponse repostingResponse = new RepostingResponse();
        repostingResponse.setSuccess(responseEntity.getBody().isSuccess());
        repostingResponse.setMessage(responseEntity.getBody().getMessage());

        return repostingResponse;
    }

    @Override
    public List<Transactions> downloadTransactions(DownloadCSV downloadCSV) {
        logger.trace("Entering downloadTransactions service implementation");
        String searchText = downloadCSV.getSearch();
        String startDate = downloadCSV.getStartDAte();
        String endDate = downloadCSV.getEndDate();
        List orgCodes=downloadCSV.getOrgCodes();

        List<Transactions> transactions = null;

        if(searchText != ""){
            transactions = transactionsRepository.findByTxnIdEqualsOrOrgCodeEquals(searchText,searchText);
        }
        else if (orgCodes.isEmpty() && (startDate == "" || endDate == "" )){
            transactions=transactionsRepository.findAll();
        }
        else {
            transactions = findFilteredTransactions1(startDate,endDate,orgCodes);
        }
        return transactions;
    }

    public List<Transactions> findFilteredTransactions1(String startDate, String endDate, List orgCodesList) {
        logger.trace("Entering Filtered Transactions Service Implementation ");
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

        List<Transactions> filteredTransactions = null;
        if(orgCodesList.isEmpty() && startDateIdIndex != "" && endDateIdIndex != "" ){
            filteredTransactions = transactionsRepository.findByTxnIdBetween(startDateIdIndex,endDateIdIndex);

        }
        else if( startDateIdIndex=="" && endDateIdIndex ==""  &&  !orgCodesList.isEmpty()){

            filteredTransactions = transactionsRepository.findByOrgCodeIn(orgCodesList);
        }
        else {

            filteredTransactions = transactionsRepository.findByOrgCodeInAndTxnIdBetween(orgCodesList,startDateIdIndex,endDateIdIndex);
        }
        return filteredTransactions;
    }

}
