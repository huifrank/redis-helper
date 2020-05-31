package com.huifrank.demo.query;

import com.huifrank.core.executor.PutExe4Test;
import com.huifrank.demo.RedisHelperDemoRunner;
import com.huifrank.demo.dal.BankCardDal;
import com.huifrank.demo.entity.BankCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = RedisHelperDemoRunner.class)
public class QueryAopTest {

    @Autowired
    BankCardDal bankCardDal;


    PutExe4Test exe4Test = PutExe4Test.getInstance();

    @Test
    public void testSimpleQuery(){

        BankCard bankCard = bankCardDal.queryById("1");

    }

    @Test
    public void testSimpleQueryNormal(){

        String id = bankCardDal.queryIdByCardNo("cardNo_123123");

    }
    @Test
    public void testSimpleQueryAll(){

        bankCardDal.queryByCardNo("cardNo_123123");

    }
}
