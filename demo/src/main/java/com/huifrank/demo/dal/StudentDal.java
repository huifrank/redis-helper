package com.huifrank.demo.dal;

import com.huifrank.demo.entity.Student;

import java.util.List;

public interface StudentDal {
    List<Student> queryByTeacherId(long teacherId);
}
