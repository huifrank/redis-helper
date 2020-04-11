package com.huifrank.demo.dal.impl;

import com.huifrank.annotation.CacheFor;
import com.huifrank.annotation.Field;
import com.huifrank.annotation.action.Evict;
import com.huifrank.demo.dal.BankCardDal;
import com.huifrank.demo.entity.BankCard;
import org.springframework.stereotype.Service;

@Service
public class BankCardDalImpl implements BankCardDal {



    @Evict
    @CacheFor(bufferEntity = BankCard.class)
    @Override
    public int updateById(Long id){

        return 0;
    }

    @Evict
    @CacheFor(bufferEntity = BankCard.class)
    @Override
    public int updateByCardNoAndType(@Field("cardNo") String cardNo,@Field("cardType") String type){
        //索引列表 ,入参属性 group

        return 0;
    }

    @Evict
    @CacheFor(bufferEntity = BankCard.class)
    @Override
    public int updateByCardNoAndMobile(@Field("cardNo") String cardNo, String mobile){
        //索引列表 ,入参属性 group

        return 0;
    }

    @Evict
    @CacheFor(bufferEntity = BankCard.class)
    @Override
    public int updateById(String id, String mobile) {
        return 0;
    }

    @Evict
    @CacheFor(bufferEntity = BankCard.class)
    @Override
    public int updateById(String id) {
        return 0;
    }

    @Evict
    @CacheFor(bufferEntity = BankCard.class)
    @Override
    public int updateByIdAndName(String id, String name) {
        return 0;
    }
    @Evict
    @CacheFor(bufferEntity = BankCard.class)
    @Override
    public int updateByMobileAndCardNo(String cardNo,String mobile) {
        return 0;
    }

}
