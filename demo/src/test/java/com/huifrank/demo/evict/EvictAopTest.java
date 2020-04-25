package com.huifrank.demo.evict;

import com.huifrank.core.executor.DeleteExe4Test;
import com.huifrank.demo.RedisHelperDemoRunner;
import com.huifrank.demo.dal.BankCardDal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(classes = RedisHelperDemoRunner.class)
public class EvictAopTest {

    @Autowired
    BankCardDal bankCardDal;


    DeleteExe4Test exe4Test = DeleteExe4Test.getInstance();


    @Test
    public void testEvict(){

        bankCardDal.updateByCardNoAndType("cardNo_13123","cardType_D");
        Assertions.assertAll(()-> Assertions.assertTrue(exe4Test.containsExp("->bankCard:id:(->bankCard:cardNo:[0])")),
                ()->Assertions.assertTrue(exe4Test.containsExp("->bankCard:indexCardId:(->bankCard:id:(->bankCard:cardNo:[0])).indexCardId")),
                ()->Assertions.assertTrue(exe4Test.containsExp("->bankCard:mobile:(->bankCard:id:(->bankCard:cardNo:[0])).mobile"))
        );
        Assertions.assertEquals(3,exe4Test.expSize());
    }
    @Test
    public void testEvictMobile(){

        bankCardDal.updateByCardNoAndMobile("cardNo_13123","mobile_13223");
        Assertions.assertAll(()-> Assertions.assertTrue(exe4Test.containsExp("->bankCard:id:(->bankCard:cardNo:[0])")),
                ()->Assertions.assertTrue(exe4Test.containsExp("->bankCard:indexCardId:(->bankCard:mobile:[1])"))
        );
        Assertions.assertEquals(2,exe4Test.expSize());
    }

    @Test
    public void testEvictId(){

        bankCardDal.updateById("1","13223422312");
        Assertions.assertAll(()-> Assertions.assertTrue(exe4Test.containsExp("->bankCard:id:[0]")),
                ()->Assertions.assertTrue(exe4Test.containsExp("->bankCard:indexCardId:(->bankCard:mobile:[1])")),
                ()->Assertions.assertTrue(exe4Test.containsExp("->bankCard:cardNo:(->bankCard:id:[0]).cardNo"))
        );
        Assertions.assertEquals(3,exe4Test.expSize());
    }
    @Test
    public void testEvictIdOnly(){

        bankCardDal.updateById("id_1");
        Assertions.assertAll(()-> Assertions.assertTrue(exe4Test.containsExp("->bankCard:id:[0]")),
                ()->Assertions.assertTrue(exe4Test.containsExp("->bankCard:indexCardId:(->bankCard:id:[0]).indexCardId")),
                ()->Assertions.assertTrue(exe4Test.containsExp("->bankCard:mobile:(->bankCard:id:[0]).mobile")),
                ()->Assertions.assertTrue(exe4Test.containsExp("->bankCard:cardNo:(->bankCard:id:[0]).cardNo"))
        );
        Assertions.assertEquals(4,exe4Test.expSize());
    }

    @Test
    public void testEvictIdAndName(){

        bankCardDal.updateByIdAndName("id_123456","name_123456");
        Assertions.assertAll(()-> Assertions.assertTrue(exe4Test.containsExp("->bankCard:id:[0]")),
                ()->Assertions.assertTrue(exe4Test.containsExp("->bankCard:indexCardId:(->bankCard:id:[0]).indexCardId")),
                ()->Assertions.assertTrue(exe4Test.containsExp("->bankCard:mobile:(->bankCard:id:[0]).mobile")),
                ()->Assertions.assertTrue(exe4Test.containsExp("->bankCard:cardNo:(->bankCard:id:[0]).cardNo"))
        );
        Assertions.assertEquals(4,exe4Test.expSize());
    }

    @Test
    public void testEvictMobileAndCardNo(){

        bankCardDal.updateByMobileAndCardNo("cardNo_123456","mobile_123456");
        Assertions.assertAll(()-> Assertions.assertTrue(exe4Test.containsExp("->bankCard:id:(->bankCard:cardNo:[0])")),
                ()->Assertions.assertTrue(exe4Test.containsExp("->bankCard:indexCardId:(->bankCard:mobile:[1])"))
        );
        Assertions.assertEquals(2,exe4Test.expSize());
    }
    @Test
    public void testEvictByMobileOnly(){

        bankCardDal.updateByMobile("mobile_123456");
        Assertions.assertAll(()-> Assertions.assertTrue(exe4Test.containsExp("->bankCard:indexCardId:(->bankCard:mobile:[0])")),
                ()->Assertions.assertTrue(exe4Test.containsExp("->bankCard:id:(->bankCard:indexCardId:(->bankCard:mobile:[0])).id")),
                ()->Assertions.assertTrue(exe4Test.containsExp("->bankCard:cardNo:(->bankCard:indexCardId:(->bankCard:mobile:[0])).cardNo"))
        );
        Assertions.assertEquals(3,exe4Test.expSize());
    }


}
