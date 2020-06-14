package com.huifrank.demo.dal;

import com.huifrank.annotation.CacheFor;
import com.huifrank.annotation.Field;
import com.huifrank.annotation.action.Evict;
import com.huifrank.annotation.action.Query;
import com.huifrank.annotation.action.Update;
import com.huifrank.demo.entity.BankCard;

public interface BankCardDal {

    int delByCardNoAndType(@Field("cardNo") String cardNo, @Field("cardType") String type);

    int delByCardNoAndMobile(@Field("cardNo") String cardNo, String mobile);

    int delById(@Field("id") String id, String mobile);

    int delById(@Field("id") String id );

    int delByIdAndName(String id, String name);

    int delByMobileAndCardNo(String mobile, String cardNo);

    int delByMobile(String mobile );

    int delObjById(BankCard bankCard);

    int delObjByCardNoAndMobile(BankCard bankCard);

    int delObjByCardNoAndMobile(BankCard bankCard, String mobile);

    int update(BankCard card);

    int put(BankCard card);

    BankCard queryById(String id);


    String queryMobileById(String id);

    String queryIdByCardNo(String cardNo);

    BankCard queryByCardNo(String cardNo);

    int updateNameById(BankCard card, String id);

    BankCard queryByCardNoAndType(String cardNo, String cardType);

    int updateNameById(String name, String id);
}
