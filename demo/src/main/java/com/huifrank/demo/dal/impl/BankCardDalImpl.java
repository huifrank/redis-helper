package com.huifrank.demo.dal.impl;

import com.huifrank.annotation.CacheFor;
import com.huifrank.annotation.Field;
import com.huifrank.annotation.action.Evict;
import com.huifrank.annotation.action.Update;
import com.huifrank.demo.dal.BankCardDal;
import com.huifrank.demo.entity.BankCard;
import org.springframework.stereotype.Service;

@Service
public class BankCardDalImpl implements BankCardDal {



    @Evict
    @CacheFor(bufferEntity = BankCard.class)
    @Override
    public int delByCardNoAndType(@Field("cardNo") String cardNo,@Field("cardType") String type){
        //索引列表 ,入参属性 group

        return 0;
    }

    @Evict
    @CacheFor(bufferEntity = BankCard.class)
    @Override
    public int delByCardNoAndMobile(@Field("cardNo") String cardNo, String mobile){
        //索引列表 ,入参属性 group

        return 0;
    }

    @Evict
    @CacheFor(bufferEntity = BankCard.class)
    @Override
    public int delById(String id, String mobile) {
        return 0;
    }

    @Evict
    @CacheFor(bufferEntity = BankCard.class)
    @Override
    public int delById(String id) {
        return 0;
    }

    @Evict
    @CacheFor(bufferEntity = BankCard.class)
    @Override
    public int delByIdAndName(String id, String name) {
        return 0;
    }
    @Evict
    @CacheFor(bufferEntity = BankCard.class)
    @Override
    public int delByMobileAndCardNo(String cardNo, String mobile) {
        return 0;
    }

    @Override
    @Evict
    @CacheFor(bufferEntity = BankCard.class)
    public int delByMobile(String mobile) {
        return 0;
    }

    @Override
    @CacheFor(bufferEntity = BankCard.class)
    @Update(where = {"card.id"})
    public int update(BankCard card){
        return 0;
    }

    @Override
    @CacheFor(bufferEntity = BankCard.class)
    @Update(where = {"id"})
    public int updateNameById(String name, String id){

        return 0;
    }

}
