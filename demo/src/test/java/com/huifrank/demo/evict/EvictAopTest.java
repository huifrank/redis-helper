package com.huifrank.demo.evict;

import com.huifrank.core.executor.DeleteExe4Test;
import com.huifrank.demo.RedisHelperDemoRunner;
import com.huifrank.demo.dal.BankCardDal;
import com.huifrank.demo.entity.BankCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = RedisHelperDemoRunner.class)
public class EvictAopTest {

    @Autowired
    BankCardDal bankCardDal;


    DeleteExe4Test exe4Test = DeleteExe4Test.getInstance();


    @Test
    public void testEvict(){

        bankCardDal.delByCardNoAndType("cardNo_13123","cardType_D");
        Assertions.assertAll(()-> Assertions.assertTrue(exe4Test.containsExp("->bankCard:id:(->bankCard:cardNo:[0])")),
                ()->Assertions.assertTrue(exe4Test.containsExp("->bankCard:indexCardId:(->bankCard:id:(->bankCard:cardNo:[0])).indexCardId")),
                ()->Assertions.assertTrue(exe4Test.containsExp("->bankCard:mobile:(->bankCard:id:(->bankCard:cardNo:[0])).mobile"))
        );
        Assertions.assertEquals(3,exe4Test.expSize());
    }
    @Test
    public void testEvictMobile(){

        bankCardDal.delByCardNoAndMobile("cardNo_13123","mobile_13223");
        Assertions.assertAll(()-> Assertions.assertTrue(exe4Test.containsExp("->bankCard:id:(->bankCard:cardNo:[0])")),
                ()->Assertions.assertTrue(exe4Test.containsExp("->bankCard:indexCardId:(->bankCard:mobile:[1])"))
        );
        Assertions.assertEquals(2,exe4Test.expSize());
    }

    @Test
    public void testEvictId(){

        bankCardDal.delById("1","13223422312");
        Assertions.assertAll(()-> Assertions.assertTrue(exe4Test.containsExp("->bankCard:id:[0]")),
                ()->Assertions.assertTrue(exe4Test.containsExp("->bankCard:indexCardId:(->bankCard:mobile:[1])")),
                ()->Assertions.assertTrue(exe4Test.containsExp("->bankCard:cardNo:(->bankCard:id:[0]).cardNo"))
        );
        Assertions.assertEquals(3,exe4Test.expSize());
    }
    @Test
    public void testEvictIdOnly(){

        bankCardDal.delById("id_1");
        Assertions.assertAll(()-> Assertions.assertTrue(exe4Test.containsExp("->bankCard:id:[0]")),
                ()->Assertions.assertTrue(exe4Test.containsExp("->bankCard:indexCardId:(->bankCard:id:[0]).indexCardId")),
                ()->Assertions.assertTrue(exe4Test.containsExp("->bankCard:mobile:(->bankCard:id:[0]).mobile")),
                ()->Assertions.assertTrue(exe4Test.containsExp("->bankCard:cardNo:(->bankCard:id:[0]).cardNo"))
        );
        Assertions.assertEquals(4,exe4Test.expSize());
    }

    @Test
    public void testEvictIdAndName(){

        bankCardDal.delByIdAndName("id_123456","name_123456");
        Assertions.assertAll(()-> Assertions.assertTrue(exe4Test.containsExp("->bankCard:id:[0]")),
                ()->Assertions.assertTrue(exe4Test.containsExp("->bankCard:indexCardId:(->bankCard:id:[0]).indexCardId")),
                ()->Assertions.assertTrue(exe4Test.containsExp("->bankCard:mobile:(->bankCard:id:[0]).mobile")),
                ()->Assertions.assertTrue(exe4Test.containsExp("->bankCard:cardNo:(->bankCard:id:[0]).cardNo"))
        );
        Assertions.assertEquals(4,exe4Test.expSize());
    }

    @Test
    public void testEvictMobileAndCardNo(){

        bankCardDal.delByMobileAndCardNo("cardNo_123456","mobile_123456");
        Assertions.assertAll(()-> Assertions.assertTrue(exe4Test.containsExp("->bankCard:id:(->bankCard:cardNo:[0])")),
                ()->Assertions.assertTrue(exe4Test.containsExp("->bankCard:indexCardId:(->bankCard:mobile:[1])"))
        );
        Assertions.assertEquals(2,exe4Test.expSize());
    }
    @Test
    public void testEvictByMobileOnly(){

        bankCardDal.delByMobile("mobile_123456");
        Assertions.assertAll(()-> Assertions.assertTrue(exe4Test.containsExp("->bankCard:indexCardId:(->bankCard:mobile:[0])")),
                ()->Assertions.assertTrue(exe4Test.containsExp("->bankCard:id:(->bankCard:indexCardId:(->bankCard:mobile:[0])).id")),
                ()->Assertions.assertTrue(exe4Test.containsExp("->bankCard:cardNo:(->bankCard:indexCardId:(->bankCard:mobile:[0])).cardNo"))
        );
        Assertions.assertEquals(3,exe4Test.expSize());
    }

    @Test
    public void testEvictObj(){
        BankCard bankCard = new BankCard();
        bankCard.setId(1L);
        bankCardDal.delObjById(bankCard);

        Assertions.assertAll(()-> Assertions.assertTrue(exe4Test.containsExp("->bankCard:id:[0.id]")),
                ()->Assertions.assertTrue(exe4Test.containsExp("->bankCard:indexCardId:(->bankCard:id:[0.id]).indexCardId")),
                ()->Assertions.assertTrue(exe4Test.containsExp("->bankCard:mobile:(->bankCard:id:[0.id]).mobile")),
                ()->Assertions.assertTrue(exe4Test.containsExp("->bankCard:cardNo:(->bankCard:id:[0.id]).cardNo"))
        );
        Assertions.assertEquals(4,exe4Test.expSize());
    }


    @Test
    public void testDelObjByCardNoAndMobile(){
        BankCard bankCard = new BankCard();
        bankCard.setId(1L);
        bankCard.setMobile("mobile_123123");
        bankCard.setCardNo("123123");
        bankCardDal.delObjByCardNoAndMobile(bankCard);
        Assertions.assertAll(()-> Assertions.assertTrue(exe4Test.containsExp("->bankCard:id:(->bankCard:cardNo:[0.cardNo])")),
                ()->Assertions.assertTrue(exe4Test.containsExp("->bankCard:indexCardId:(->bankCard:mobile:[0.mobile])"))
        );
        Assertions.assertEquals(2,exe4Test.expSize());

    }

    @Test
    public void testDelObjByCardNoAndMobile_2(){
        BankCard bankCard = new BankCard();
        bankCard.setId(1L);
        bankCard.setCardNo("123123");
        bankCardDal.delObjByCardNoAndMobile(bankCard,"mobile_123123");
        Assertions.assertAll(()-> Assertions.assertTrue(exe4Test.containsExp("->bankCard:id:(->bankCard:cardNo:[0.cardNo])")),
                ()->Assertions.assertTrue(exe4Test.containsExp("->bankCard:indexCardId:(->bankCard:mobile:[1])"))
        );
        Assertions.assertEquals(2,exe4Test.expSize());

    }

    @Test
    public void testSimpleUpdate(){
        BankCard bankCard = new BankCard();
        bankCard.setId(1L);
        bankCard.setCardNo("123123");
        bankCardDal.update(bankCard);
    }


}
