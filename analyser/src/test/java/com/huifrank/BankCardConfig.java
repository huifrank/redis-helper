package com.huifrank;


import com.huifrank.analyser.CacheConfig;
import com.huifrank.common.util.invoker.InvokeInStatic;


public class BankCardConfig  {


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
