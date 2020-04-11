package com.huifrank.demo.entity;

import com.huifrank.annotation.BufferEntity;
import com.huifrank.annotation.index.ClusterIndex;
import com.huifrank.annotation.index.Indexed;
import lombok.Data;

@BufferEntity(keyPrefix = "bankCard")
@Data
public class BankCard {

    /**
     * bankCard:id
     */
    @ClusterIndex
    private Long id;

    @ClusterIndex
    private String indexCardId;

    /**
     * bankCard:cardNo
     */
    @Indexed(ref = "id")
    private String cardNo;

    private String bankName;

    private String cardType;

    private String name;


    @Indexed(ref ="indexCardId")
    private String mobile;
}
