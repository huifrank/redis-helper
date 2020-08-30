package com.huifrank.demo.Filter;

import com.huifrank.annotation.MemoryFilter;
import com.huifrank.demo.entity.Student;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StudentFilter implements MemoryFilter<Student> {

    @Override
    public List<Student> doFilter(List<Student> list, List<Param> params) {

        Predicate<Student> predicate = Objects::nonNull;

//        params.stream().forEach(param -> {
//            if("id".equals(param.getAttributeName())){
//                predicate.and( (Student st) -> param.getValue().equals(st.getId()));
//            }
//            if("classId".equals(param.getAttributeName())){
//                predicate.and( (Student st) -> param.getValue().equals(st.getClassId()));
//
//            }
//            if("name".equals(param.getAttributeName())){
//                predicate.and( (Student st) -> param.getValue().equals(st.getName()));
//            }
//
//        });

        return list.stream().filter(predicate).collect(Collectors.toList());
    }
}
