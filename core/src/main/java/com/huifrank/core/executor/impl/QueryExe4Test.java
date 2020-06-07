package com.huifrank.core.executor.impl;

import com.huifrank.core.executor.QueryOpsExe;
import com.huifrank.core.executor.ops.QueryOps;
import com.huifrank.core.executor.ops.Values;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 供单元测试用的实现
 */
@Slf4j
public class QueryExe4Test implements QueryOpsExe {

    List<String> exp = Collections.emptyList();

    private static QueryExe4Test instance = new QueryExe4Test();

    public static QueryExe4Test getInstance() {
        return instance;
    }

    private QueryExe4Test(){}



    @Override
    public List<Object> execute(List<QueryOps> opsList, Values values) {
        exp = opsList.stream().map(f-> f.getGetExpression().toString()).collect(Collectors.toList());

        exp.forEach(log::info);

        return Collections.EMPTY_LIST;    }


    public boolean containsExp(String exp){
        return this.exp.contains(exp);
    }

    public int expSize(){
        return exp.size();
    }
}
