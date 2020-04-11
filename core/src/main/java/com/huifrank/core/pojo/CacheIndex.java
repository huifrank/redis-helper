package com.huifrank.core.pojo;

import com.huifrank.core.CacheIndexType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CacheIndex {

    CacheIndexType indexType;

    /** 属性名 */
    String name;

    /** 仅普通索引有值 表示其关联的聚簇索引*/
    String refIndex;



}
