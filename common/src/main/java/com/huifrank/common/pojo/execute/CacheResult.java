package com.huifrank.common.pojo.execute;

import com.huifrank.common.CacheStructure;
import lombok.Data;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * 如果缓存中反序列化出来直接就是一个java集合，不做特殊处理
 * 仅根据expression中的映射关系或redis原生数据结构做封装。
 */
@Data
public class CacheResult {

    List<Object> data;

    CacheStructure cacheStructure = CacheStructure.Strings;



}
