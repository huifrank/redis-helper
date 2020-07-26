package com.huifrank.demo.dal.impl;

import com.huifrank.annotation.CacheFor;
import com.huifrank.annotation.action.Query;
import com.huifrank.annotation.action.Update;
import com.huifrank.demo.dal.FacultyClassDal;
import com.huifrank.demo.entity.BankCard;
import com.huifrank.demo.entity.FacultyClass;
import org.springframework.stereotype.Service;

@Service
public class FacultyClassDalImpl implements FacultyClassDal {

    @Override
    @CacheFor(bufferEntity = FacultyClass.class)
    @Query(result = "FacultyClass")
    public FacultyClass queryById( Long id  ){
        FacultyClass facultyClass = new FacultyClass();

        return facultyClass;
    }
}
