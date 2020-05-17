package com.huifrank.demo.put;

import com.huifrank.core.executor.DeleteExe4Test;
import com.huifrank.demo.RedisHelperDemoRunner;
import com.huifrank.demo.dal.BankCardDal;
import com.huifrank.demo.entity.BankCard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = RedisHelperDemoRunner.class)
public class PutAopTest {

    @Autowired
    BankCardDal bankCardDal;


    DeleteExe4Test exe4Test = DeleteExe4Test.getInstance();

    @Test
    public void testSimplePut(){
        BankCard bankCard = new BankCard();
        bankCard.setId(1L);
        bankCard.setCardNo("123123");
        bankCardDal.put(bankCard);
    }
}
