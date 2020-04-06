package com.huifrank.demo.entity;

import com.huifrank.annotation.BufferEntity;
import com.huifrank.annotation.index.ClusterIndex;
import com.huifrank.annotation.index.Indexed;
import lombok.Data;

@BufferEntity(keyPrefix = "bankCard")
@Data
public class BankCard {

    @ClusterIndex
    private Long id;

    @Indexed
    private String cardNo;

    private String bankName;

    private String cardType;


    private String mobile;
}
