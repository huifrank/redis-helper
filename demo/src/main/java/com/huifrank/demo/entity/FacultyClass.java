package com.huifrank.demo.entity;

import com.huifrank.annotation.BufferEntity;
import com.huifrank.annotation.index.HashesIndex;
import lombok.Data;

/**
 * 班级
 */
@BufferEntity(keyPrefix = "facultyClass")
@Data
public class FacultyClass {

    @HashesIndex
    private Long id;

    private String name;





}
