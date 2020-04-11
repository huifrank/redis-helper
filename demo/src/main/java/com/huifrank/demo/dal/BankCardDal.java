package com.huifrank.demo.dal;

import com.huifrank.annotation.CacheFor;
import com.huifrank.annotation.Field;
import com.huifrank.annotation.action.Evict;
import com.huifrank.demo.entity.BankCard;

public interface BankCardDal {
    @Evict
    @CacheFor(bufferEntity = BankCard.class)
    int updateById(Long id);

    @Evict
    @CacheFor(bufferEntity = BankCard.class)
    int updateByCardNoAndType(@Field("cardNo") String cardNo, @Field("cardType") String type);

    @Evict
    @CacheFor(bufferEntity = BankCard.class)
    int updateByCardNoAndMobile(@Field("cardNo") String cardNo, String mobile);
}
