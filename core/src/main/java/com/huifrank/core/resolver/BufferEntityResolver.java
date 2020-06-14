package com.huifrank.core.resolver;

import com.huifrank.annotation.CacheStructure;
import com.huifrank.annotation.Mapping;
import com.huifrank.annotation.index.ClusterIndex;
import com.huifrank.annotation.index.Indexed;
import com.huifrank.core.CacheIndexType;
import com.huifrank.core.pojo.CacheIndex;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BufferEntityResolver {


    public List<CacheIndex> resolverEntity(Class entityClass){


        return Stream.of(entityClass.getDeclaredFields()).map(this::resolverEntityField)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

    }

    private List<CacheIndex> resolverEntityField(Field field){
        List<CacheIndex> fieldCache = new ArrayList<>();
        ClusterIndex clusterIndex = field.getAnnotation(ClusterIndex.class);
        Indexed indexed = field.getAnnotation(Indexed.class);

        if(clusterIndex != null) {
            fieldCache.add(new CacheIndex(CacheIndexType.ClusterIndex ,field.getName(),null, CacheStructure.Strings,Mapping.one));
        } else if(indexed != null) {
            fieldCache.add(new CacheIndex(CacheIndexType.NormalIndex ,field.getName(),indexed.ref(),indexed.structure(),indexed.mapping()));
        }
        return fieldCache;
    }



}
