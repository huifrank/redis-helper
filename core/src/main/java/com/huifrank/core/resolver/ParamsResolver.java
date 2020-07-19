package com.huifrank.core.resolver;

import com.huifrank.annotation.Field;
import com.huifrank.common.pojo.CacheIndex;
import com.huifrank.common.pojo.ParamMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
public class ParamsResolver {


    public List<ParamMap> resolverParameters(Parameter[] parameters,String[] where) {

        return IntStream.range(0,parameters.length).mapToObj(index ->{
            Parameter p = parameters[index];
            Field annotation = p.getAnnotation(Field.class);
            //如果没有用@Field的话，就取变量名
            String name = annotation == null ? p.getName() : annotation.value();
            //过滤出符合当前name的where条件变量
            List<String> select = selectWhere(name, where);

            if(CollectionUtils.isEmpty(select)){
                return Collections.singletonList(new ParamMap(name,String.valueOf(index),p.getType().getTypeName()));
            }

            //只支持一级"."
            Stream<ParamMap> paramMapStream = select.stream()
                    .map(c -> {
                        if(c.indexOf(".") == -1){
                            return new ParamMap(name,String.valueOf(index),p.getType().getTypeName());
                        }
                        String subName = c.substring(c.indexOf(".")+1);
                        return new ParamMap(subName, index +"."+ subName, p.getType().getTypeName());
                    });
            return paramMapStream.collect(Collectors.toList());


        } ).flatMap(Collection::stream).collect(Collectors.toList());
    }


    private  List<String> selectWhere(String name,String[] where){
        if(where == null || where.length == 0){
            return Collections.emptyList();
        }
        List<String> collect = Arrays.stream(where).filter((c) -> {
            int i = c.indexOf(".");
            if(i != -1)
                return name.equals(c.substring(0,c.indexOf(".")));
            return name.equals(c);
        }).collect(Collectors.toList());
        return collect;
    }






}
