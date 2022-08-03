package com.example.demo.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.example.demo.Constants.EndPoints.BULK_POSTING;
import static com.example.demo.Constants.EndPoints.HOME;
import static com.example.demo.Constants.Templates.BULK_POSTING_PAGE;
import static com.example.demo.Constants.Templates.HOMEPAGE;

@Controller
public class TransactionsController {
    Logger logger = LoggerFactory.getLogger(TransactionsController.class);

    @RequestMapping(HOME)
    public String getHomePage(){
        logger.trace("Entered Home Page");
        return HOMEPAGE;
    }

    @RequestMapping(BULK_POSTING)
    public String bulkPosting(){
        return BULK_POSTING_PAGE;
    }
}
