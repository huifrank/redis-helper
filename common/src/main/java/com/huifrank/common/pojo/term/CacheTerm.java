package com.huifrank.common.pojo.term;

import com.huifrank.common.pojo.expression.SoloExpression;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class CacheTerm {

    private String indexName;

    private String valueIndex;

    private String refBeforeName;

    private SoloExpression before;


    public CacheTerm(String indexName){
        this.indexName = indexName;
    }

    @Override
    public String toString(){
        if(valueIndex != null && indexName != null) {
            return indexName + "[" + valueIndex + "]";
        }
        if(valueIndex != null){
            return "["+valueIndex+"]";
        }
        if(indexName != null){
            return indexName;
        }
        return null;
    }
}
