package com.huifrank.demo.dal.impl;

import com.huifrank.annotation.CacheFor;
import com.huifrank.annotation.action.Query;
import com.huifrank.demo.dal.StudentDal;
import com.huifrank.demo.entity.Student;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class StudentDalImpl implements StudentDal {


    @Override
    @Query(where = "teacherId",result = "Student")
    @CacheFor(bufferEntity = Student.class)
    public List<Student> queryByTeacherId(long teacherId){
        return Collections.emptyList();
    }

}
