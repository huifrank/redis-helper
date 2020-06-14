package com.huifrank.demo.entity;

import com.huifrank.annotation.BufferEntity;
import com.huifrank.annotation.CacheStructure;
import com.huifrank.annotation.Mapping;
import com.huifrank.annotation.index.ClusterIndex;
import com.huifrank.annotation.index.Indexed;
import lombok.Data;

@BufferEntity(keyPrefix = "student")
@Data
public class Student {

    @ClusterIndex
    private long id;

    /**
     * 映射为 one -> many
     * 一个班级下会对应多个学生
     */
    @Indexed(ref = "id",mapping = Mapping.many,structure = CacheStructure.Lists)
    private long classId;

    private String name;

}
