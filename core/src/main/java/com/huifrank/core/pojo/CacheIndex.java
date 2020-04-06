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

    String name;


}
