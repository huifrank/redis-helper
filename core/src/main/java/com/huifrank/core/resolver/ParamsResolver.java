package com.huifrank.core.resolver;

import com.huifrank.annotation.Field;
import com.huifrank.core.pojo.CacheIndex;
import com.huifrank.core.pojo.ParamMap;

import java.lang.reflect.Parameter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParamsResolver {


    public List<ParamMap> resolverParameters(Parameter[] parameters) {

        return IntStream.range(0,parameters.length).mapToObj(index ->{
            Parameter p = parameters[index];
            Field annotation = p.getAnnotation(Field.class);
            if(annotation == null){
                return new ParamMap(p.getName(),index,p.getType().getTypeName());
            }
            return new ParamMap(annotation.value(),index,p.getType().getTypeName());

        } ).collect(Collectors.toList());
    }




}
