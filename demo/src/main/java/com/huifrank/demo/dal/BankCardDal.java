package com.huifrank.demo.dal;

import com.huifrank.annotation.CacheFor;
import com.huifrank.annotation.Field;
import com.huifrank.annotation.action.Evict;
import com.huifrank.demo.entity.BankCard;

public interface BankCardDal {
    int updateById(Long id);

    int updateByCardNoAndType(@Field("cardNo") String cardNo, @Field("cardType") String type);

    int updateByCardNoAndMobile(@Field("cardNo") String cardNo, String mobile);

    int updateById(@Field("id") String id, String mobile);

    int updateById(@Field("id") String id );

    int updateByIdAndName(String id, String name);

    int updateByMobileAndCardNo(String mobile, String cardNo);
}
