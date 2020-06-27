package com.huifrank.demo.dal.impl;

import com.huifrank.annotation.CacheFor;
import com.huifrank.annotation.action.Evict;
import com.huifrank.annotation.action.Put;
import com.huifrank.annotation.action.Query;
import com.huifrank.demo.dal.StudentDal;
import com.huifrank.demo.entity.BankCard;
import com.huifrank.demo.entity.Student;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class StudentDalImpl implements StudentDal {


    @Override
    @Query(where = "classId",result = "Student")
    @CacheFor(bufferEntity = Student.class)
    public List<Student> queryByClassId(long classId){
        return Collections.emptyList();
    }

    @Override
    @CacheFor(bufferEntity = Student.class)
    @Put
    public void insert(Student student){

    }

    @Override
    @Evict
    @CacheFor(bufferEntity = Student.class)
    public void delByClassId(long classId) {

    }

}
