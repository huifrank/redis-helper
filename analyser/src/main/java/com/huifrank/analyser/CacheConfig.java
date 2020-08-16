package com.huifrank.analyser;

import com.huifrank.common.exception.CacheConfigException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public abstract class CacheConfig {


    private static Map<Class,CacheConfig> configMap = new HashMap<>();

    Class clazz;

    Map<String,ClusterIndex> clusterIndexMap = new HashMap<>();

    Map<String,Indexed> indexedMap = new HashMap<>();


    public CacheConfig(Class clazz){
        this.clazz = clazz;
    }

    public void build(){
        Optional<Indexed> noRef = indexedMap.values().stream().filter(r -> Objects.isNull(r.ref)).findFirst();
        if(noRef.isPresent()){
            throw new CacheConfigException("index "+noRef.get()+"do not has ref cluster");
        }
        configMap.put(this.clazz,this);
    }






    public class ClusterIndex {

        public ClusterIndex(String attribute){
            clusterIndexMap.put(attribute,this);
        }

    }

    public class Indexed {

        String attribute;
        ClusterIndex ref;
        public Indexed(String attribute){
            this.attribute = attribute;
            indexedMap.put(attribute,this);
        }
        public void refWith(ClusterIndex clusterIndex){
            this.ref = clusterIndex;
        }

    }
}
