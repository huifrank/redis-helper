package com.huifrank.demo.dal;

import com.huifrank.annotation.CacheFor;
import com.huifrank.annotation.action.Put;
import com.huifrank.demo.entity.BankCard;
import com.huifrank.demo.entity.Student;

import java.util.List;

public interface StudentDal {
    List<Student> queryByClassId(long classId);

    void insert(Student student);
}
