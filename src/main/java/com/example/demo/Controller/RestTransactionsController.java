package com.example.demo.Controller;

import com.example.demo.Model.*;
import com.example.demo.Service.CsvService;
import com.example.demo.Service.TransactionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static com.example.demo.Constants.EndPoints.*;
import static com.example.demo.Constants.StringConstants.*;

@CrossOrigin
@RestController
public class RestTransactionsController {

    Logger logger = LoggerFactory.getLogger(RestTransactionsController.class);

    @Autowired
    private TransactionsService transactionsService;

    @Autowired
    private CsvService csvService;

    @PostMapping(SEARCH)
    public  ResponseEntity<Page<Transactions>> getTransactionsWithSearch(@RequestBody Search search){
        logger.trace("Entering to SearchTransactions Controller with Request Body {}",search.toString());
        String searchedText=search.getSearchVariable();
        Integer pageNo = search.getPageNo();
        Page<Transactions> searchedTransactions = transactionsService.getPaginatedTransactionsWithSearch(searchedText,pageNo);
        return new ResponseEntity<>(searchedTransactions, HttpStatus.OK);
    }

    @PostMapping(UNSUCCESSFULTRANSACTIONS)
    public ResponseEntity<Page<Transactions>> getPage(@RequestParam String page,@RequestBody HomePage homePage){
        Integer customPageSize=homePage.getTransactionsRange();
        logger.trace("Entering to AllUnsuccessful Transactions Handler {}",page);
        Integer pageInteger=Integer.parseInt(page);
        Page<Transactions> pages = transactionsService.getPaginatedTransactions(pageInteger,customPageSize);
        return new ResponseEntity<>(pages,HttpStatus.OK);
    }
    @PostMapping(UNSUCCESSFUL_TRANSACTIONS_WITH_FILTER)
    public ResponseEntity<Page<Transactions>>getFilteredPage(@RequestBody Filter filter,@RequestParam String page){
        logger.trace("Entering to Transaction with filters Handler with Request Body {}",filter.toString());
        Integer pageInteger=Integer.parseInt(page);
        List organizationCodes = filter.getOrgCodes();
        Date startDate = filter.getStartDate();
        Date endDate = filter.getEndDate();
        Page<Transactions> pages= transactionsService.getPaginatedFilteredTransactions(organizationCodes,startDate,endDate,pageInteger);
        return new ResponseEntity<>(pages,HttpStatus.OK);
    }

    @PostMapping(ORG_CODES)
    public ResponseEntity<List<String>> getUniqueOrgCodes(){
        logger.trace("Entering to GetAllUnique OrgCodes handler");
        List<String> uniqueOrgCodes = transactionsService.getUniqueOrgCodes();
        return new ResponseEntity<>(uniqueOrgCodes,HttpStatus.OK);
    }

    @PostMapping(DOWNLOAD_TRANSACTIONS)
    public void downloadCsvFile(HttpServletResponse response,@RequestParam("searchVar") String searchVariable,@RequestParam("orgCodes1") List<String> orgCodesList,
                                @RequestParam("startDate") String startDate, @RequestParam String endDate) throws IOException {
        logger.trace("Entering DownloadCSV handler");

        response.setContentType(TEXT_CSV);
        response.setHeader(CONTENT_DISPOSITION, ATTACHMENT_FILE_TRANSACTIONS_CSV);
        if(searchVariable == ""){
            csvService.downloadCsvFile(response.getWriter(),csvService.findFilteredTransactions1(startDate,endDate,orgCodesList));

        }else{
            csvService.downloadCsvFile(response.getWriter(),csvService.findSearchedTransactions(searchVariable));

        }
    }


    @PostMapping(REPOSTING)
    public ResponseEntity<RepostingResponse> reposting(@RequestBody Posting posting){
        logger.trace("Entering to Reposting  Controller with Request Body {}",posting.toString());
        List<String> txnList = posting.getTransactionIds();
        RepostingResponse repostingResponse = transactionsService.reposting(txnList);
        return ResponseEntity.ok(repostingResponse);
    }


    @PostMapping(DOWNLOAD_CSVFILE)
    public ResponseEntity<List<Transactions>> downloadTransactionsCsvFile(@RequestBody DownloadCSV downloadCSV){
        logger.trace("entering download transactions,{}",downloadCSV.toString());
        List<Transactions> transactions = transactionsService.downloadTransactions(downloadCSV);
        return new ResponseEntity<>(transactions,HttpStatus.OK);
    }
}
