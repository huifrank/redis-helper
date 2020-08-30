package com.huifrank.demo.dto;

import com.huifrank.annotation.BufferEntity;
import com.huifrank.annotation.index.ClusterIndex;
import com.huifrank.annotation.index.Indexed;
import lombok.Data;

@Data
public class BankCardDTO {

    /**
     * bankCard:id
     */
    private Long id;

    private String indexCardId;

    /**
     * bankCard:cardNo
     */
    private String cardNo;

    private String bankName;

    private String cardType;

    private String name;


    private String mobile;
}
