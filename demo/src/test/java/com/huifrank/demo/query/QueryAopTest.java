package com.huifrank.demo.query;

import com.huifrank.core.executor.PutExe4Test;
import com.huifrank.core.executor.QueryExe4Test;
import com.huifrank.core.executor.QueryOpsExe;
import com.huifrank.core.executor.ops.QueryOps;
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


    QueryExe4Test exe4Test = QueryExe4Test.getInstance();

    @Test
    public void testSimpleQuery(){

        BankCard bankCard = bankCardDal.queryById("1");

        Assertions.assertAll(()-> Assertions.assertTrue(exe4Test.containsExp("->bankCard:id:[0]")));
        Assertions.assertEquals(1,exe4Test.expSize());
    }

    @Test
    public void testSimpleQueryNormal(){

        String id = bankCardDal.queryIdByCardNo("cardNo_123123");

        Assertions.assertAll(()-> Assertions.assertTrue(exe4Test.containsExp("->bankCard:cardNo:[0]")));
        Assertions.assertEquals(1,exe4Test.expSize());
    }
    @Test
    public void testSimpleQueryAll(){
        bankCardDal.queryByCardNo("cardNo_123123");


        Assertions.assertAll(()-> Assertions.assertTrue(exe4Test.containsExp("->bankCard:id:(->bankCard:cardNo:[0])")));
        Assertions.assertEquals(1,exe4Test.expSize());


    }
}
