package com.huifrank.demo.config;


import com.huifrank.common.util.invoker.SPIInvoked;
import com.huifrank.analyser.CacheConfig;
import com.huifrank.common.util.invoker.SPIService;
import com.huifrank.demo.entity.BankCard;


@SPIService(SPIInvoked.class)
public class BankCardConfig implements SPIInvoked {


    @Override
    public void doInvoke() {

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
