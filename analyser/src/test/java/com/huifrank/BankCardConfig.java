package com.huifrank;


import com.huifrank.analyser.CacheConfig;

public class BankCardConfig  {


    public void config() {

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
