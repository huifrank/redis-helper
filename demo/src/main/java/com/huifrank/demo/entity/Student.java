package com.huifrank.demo.entity;

import com.huifrank.annotation.BufferEntity;
import com.huifrank.common.CacheStructure;
import com.huifrank.common.Mapping;
import com.huifrank.annotation.index.ClusterIndex;
import com.huifrank.annotation.index.Indexed;
import com.huifrank.demo.Filter.StudentFilter;
import lombok.Data;

//1.5小时过期
@BufferEntity(keyPrefix = "student",expireIn = 5400000L,memoryFilter = StudentFilter.class)
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
