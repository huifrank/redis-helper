package com.huifrank.common.pojo;

import com.huifrank.annotation.CacheStructure;
import com.huifrank.annotation.Mapping;
import com.huifrank.common.CacheIndexType;
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

    public CacheIndex(CacheIndexType indexType,String name,String refIndex){
        this.indexType = indexType;
        this.name = name;
        this.refIndex = refIndex;
    }

    CacheStructure structure;

    Mapping mapping;




}
