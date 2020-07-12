package com.huifrank.demo.query;

import com.huifrank.executor.impl.PutExe4Test;
import com.huifrank.executor.impl.QueryExe4Test;
import com.huifrank.demo.RedisHelperDemoRunner;
import com.huifrank.demo.dal.BankCardDal;
import com.huifrank.demo.dal.StudentDal;
import com.huifrank.demo.entity.BankCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = RedisHelperDemoRunner.class)
public class QueryAopTest {

    @Autowired
    BankCardDal bankCardDal;

    @Autowired
    StudentDal studentDal;

    QueryExe4Test exe4Test_query = QueryExe4Test.getInstance();

    PutExe4Test exe4Test_put = PutExe4Test.getInstance();


    @Test
    public void testSimpleQuery(){

        BankCard bankCard = bankCardDal.queryById("1");

        Assertions.assertAll(()-> Assertions.assertTrue(exe4Test_query.containsExp("one:->bankCard:id:[0]")));
        Assertions.assertEquals(1,exe4Test_query.expSize());
    }

    @Test
    public void testSimpleQueryNormal(){

        String id = bankCardDal.queryIdByCardNo("cardNo_123123");

        Assertions.assertAll(()-> Assertions.assertTrue(exe4Test_query.containsExp("one:->bankCard:cardNo:[0]")));
        Assertions.assertEquals(1,exe4Test_query.expSize());
    }
    @Test
    public void testSimpleQueryAll(){
        bankCardDal.queryByCardNo("cardNo_123123");


        Assertions.assertAll(()-> Assertions.assertTrue(exe4Test_query.containsExp("one:->bankCard:id:(one:->bankCard:cardNo:[0])")));
        Assertions.assertEquals(1,exe4Test_query.expSize());

    }

    @Test
    public void testQueryMulParam(){

        bankCardDal.queryByCardNoAndType("cardNo_123123","D");
    }
    @Test
    public void testQueryMulGet(){

        studentDal.queryByClassId(123123);
        Assertions.assertAll(()-> Assertions.assertTrue(exe4Test_query.containsExp("one:->student:id:(many:->student:classId:[0])")),

                ()->Assertions.assertTrue(exe4Test_put.containsExp("Strings[->student:id:(one:->[0]).id # [0]")),
                ()->Assertions.assertTrue(exe4Test_put.containsExp("Strings[->student:classId:(one:->[0]).classId # [0].classId"))
        );
        Assertions.assertEquals(1,exe4Test_query.expSize());
        Assertions.assertEquals(2,exe4Test_put.expSize());
    }
}
