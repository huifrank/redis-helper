package com.huifrank.demo.put;

import com.huifrank.core.executor.impl.PutExe4Test;
import com.huifrank.demo.RedisHelperDemoRunner;
import com.huifrank.demo.dal.BankCardDal;
import com.huifrank.demo.dal.StudentDal;
import com.huifrank.demo.entity.BankCard;
import com.huifrank.demo.entity.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = RedisHelperDemoRunner.class)
public class PutAopTest {

    @Autowired
    BankCardDal bankCardDal;

    @Autowired
    StudentDal studentDal;

    PutExe4Test exe4Test = PutExe4Test.getInstance();

    @Test
    public void testSimplePut(){
        BankCard bankCard = new BankCard();
        bankCard.setId(1L);
        bankCard.setCardNo("123123");
        bankCardDal.put(bankCard);


        Assertions.assertAll(()-> Assertions.assertTrue(exe4Test.containsExp("->bankCard:id:(->[0]).id # [0]")),
                ()->Assertions.assertTrue(exe4Test.containsExp("->bankCard:indexCardId:(->[0]).indexCardId # [0]")),
                ()->Assertions.assertTrue(exe4Test.containsExp("->bankCard:cardNo:(->[0]).cardNo # [0].cardNo")),
                ()->Assertions.assertTrue(exe4Test.containsExp("->bankCard:mobile:(->[0]).mobile # [0].mobile"))
        );
        Assertions.assertEquals(4,exe4Test.expSize());
    }

    @Test
    public void testStudentClassPut(){
        Student student = new Student();
        student.setName("name_aaa");
        student.setClassId(12321L);
        studentDal.insert(student);
    }
}
