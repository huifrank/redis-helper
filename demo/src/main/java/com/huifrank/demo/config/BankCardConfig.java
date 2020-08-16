package com.huifrank.demo.config;


import com.huifrank.analyser.CacheConfig;
import com.huifrank.common.util.invoker.InvokeInStatic;
import com.huifrank.demo.entity.BankCard;


public class BankCardConfig {


    @InvokeInStatic
    public void bankCardConfig() {

        new CacheConfig(BankCard.class){
            {
                ClusterIndex id = new ClusterIndex("id");
                ClusterIndex indexCardId = new ClusterIndex("indexCardId");

                new Indexed("cardNo").refWith(id);
                new Indexed("mobile").refWith(indexCardId);

            }
        }.build();


    }
}
