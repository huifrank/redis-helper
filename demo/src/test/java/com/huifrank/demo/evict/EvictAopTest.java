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

    @Test
    public void testEvictId(){

        bankCardDal.updateById("1","13223422312");
    }
    @Test
    public void testEvictIdOnly(){

        bankCardDal.updateById("a");
    }

    @Test
    public void testEvictIdAndName(){

        bankCardDal.updateByIdAndName("id_123456","name_123456");
    }

    @Test
    public void testEvictMobileAndCardNo(){

        bankCardDal.updateByMobileAndCardNo("cardNo_123456","mobile_123456");
    }
    @Test
    public void testEvictByMobileOnly(){

        bankCardDal.updateByMobile("mobile_123456");
    }


}
