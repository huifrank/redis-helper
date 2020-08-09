package com.huifrank;


import com.huifrank.analyser.CacheConfig;

public class BankCardConfig  {


    public void config() {

        new CacheConfig(BankCard.class){

            {
                ClusterIndex id = new ClusterIndex("id");
                ClusterIndex indexCardId = new ClusterIndex("indexCardId");

                clusterIndexConfig(id);
                clusterIndexConfig(indexCardId);

                IndexedConfig(new Indexed("cardNo",id));
                IndexedConfig(new Indexed("mobile",indexCardId));

            }

        };



    }
}
