package com.huifrank.analyser;

public abstract class CacheConfig {




    public CacheConfig(Class clazz){

    }

    public  CacheConfig clusterIndexConfig(ClusterIndex... clusterIndex){ return this;}
    public  CacheConfig IndexedConfig(Indexed... indexed){return this;}





    public class ClusterIndex {

        public ClusterIndex(String attribute){

        }

    }

    public class Indexed {

        public Indexed(String attribute,ClusterIndex ref){
        }

    }
}
