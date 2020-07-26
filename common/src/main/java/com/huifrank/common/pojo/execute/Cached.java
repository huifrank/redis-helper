package com.huifrank.common.pojo.execute;

import com.huifrank.common.CacheStructure;
import lombok.Data;


@Data
public class Cached {

    Object data;

    CacheStructure cacheStructure = CacheStructure.Strings;

    TypeReference typeReference;



}
