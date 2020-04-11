package com.huifrank.demo.evict;

import com.huifrank.core.action.EvictAction;
import com.huifrank.demo.RedisHelperDemoRunner;
import com.huifrank.demo.dal.BankCardDal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = RedisHelperDemoRunner.class)
public class EvictAopTest {

    @Autowired
    BankCardDal bankCardDal;




    @Test
    public void testEvict(){

        bankCardDal.updateByCardNoAndType("a","D");
    }
    @Test
    public void testEvictMobile(){

        bankCardDal.updateByCardNoAndMobile("a","13223422312");
    }



}
