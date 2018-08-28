package com.browser.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.browser.service.StatisService;
import com.browser.service.TransactionService;


/**
 * Created by chengmaotao on 2018/3/16.
 */
@Component
public class StatisTask {

    private static Logger logger = LoggerFactory.getLogger(StatisTask.class);

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private StatisService statisService;

    @Scheduled(cron="0/5 * * * * ? ")
    public void hourStatic(){
        try {
            transactionService.updateSelect();
        }catch(Exception e){
            logger.error("统计任务出错",e);
        }

    }

    @Scheduled(cron="0/5 * * * * ? ")
    public void newBlockStatic(){
        try {
            statisService.newBlockStatic();
        }catch(Exception e){
            logger.error("统计任务出错",e);
        }
    }

    @Scheduled(cron="0/5 * * * * ? ")
    public void newTransactionStatic(){
        try {
            statisService.newTransactionStatic();
        }catch(Exception e){
            logger.error("统计任务出错",e);
        }
    }


}
