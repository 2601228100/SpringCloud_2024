package com.dzy.service;


import com.dzy.mapper.AccountMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class AccountServiceImpl implements  AccountService {


    @Resource
    private AccountMapper accountMapper;

    /**
     * 扣减账户余额
     *
     * @param userId 用户id
     * @param money  本次消费金额
     */
    @Override
    public void decrease(Long userId, Long money) {
        log.info("------->account-service中扣减账户余额开始");
        accountMapper.decrease(userId,money);
        myTimeOut();
//        int age = 10/0;
        log.info("------->account-service中扣减账户余额结束");
    }

    /**
     * 模拟超时异常，全局事务回滚
     */
    private static void myTimeOut()
    {
        try { TimeUnit.SECONDS.sleep(65); } catch (InterruptedException e) { e.printStackTrace(); }
    }
}
