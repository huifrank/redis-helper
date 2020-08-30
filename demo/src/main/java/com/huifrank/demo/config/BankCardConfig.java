package com.huifrank.demo.config;


import com.google.auto.service.AutoService;
import com.huifrank.analyser.ByInvoked;
import com.huifrank.analyser.CacheConfig;
import com.huifrank.demo.entity.BankCard;


@AutoService(ByInvoked.class)
public class BankCardConfig implements ByInvoked {


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
