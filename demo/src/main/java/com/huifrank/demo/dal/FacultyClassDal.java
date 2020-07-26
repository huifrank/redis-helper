package com.huifrank.demo.dal;

import com.huifrank.annotation.CacheFor;
import com.huifrank.annotation.action.Query;
import com.huifrank.demo.entity.FacultyClass;

public interface FacultyClassDal {
    @CacheFor(bufferEntity = FacultyClass.class)
    @Query(result = "FacultyClass")
    FacultyClass queryById(Long id);
}
