package com.huifrank.demo.update;

import com.huifrank.executor.impl.DeleteExe4Test;
import com.huifrank.demo.RedisHelperDemoRunner;
import com.huifrank.demo.dal.BankCardDal;
import com.huifrank.demo.entity.BankCard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = RedisHelperDemoRunner.class)
public class UpdateAopTest {
    @Autowired
    BankCardDal bankCardDal;


    DeleteExe4Test exe4Test = DeleteExe4Test.getInstance();

    @Test
    public void testSimpleUpdate(){
        BankCard bankCard = new BankCard();
        bankCard.setId(1L);
        bankCard.setCardNo("123123");
        bankCardDal.update(bankCard);
    }
}
