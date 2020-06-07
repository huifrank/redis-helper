package com.huifrank.core.executor;

import com.huifrank.core.executor.ops.DelOps;
import com.huifrank.core.executor.ops.Values;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 供单元测试用的实现
 */
@Slf4j
public class DeleteExe4Test implements DeleteOpsExe{

    List<String> exp = Collections.emptyList();

    private static DeleteExe4Test instance = new DeleteExe4Test();

    public static DeleteExe4Test getInstance() {
        return instance;
    }

    private DeleteExe4Test(){}

    @Override
    public void execute(List<DelOps> opsList) {
        exp = opsList.stream().map(f-> f.getGetExpression().toString()).collect(Collectors.toList());

        exp.forEach(log::info);
    }

    @Override
    public void execute(List<DelOps> opsList, Values values) {
        exp = opsList.stream().map(f-> f.getGetExpression().toString()).collect(Collectors.toList());

        exp.forEach(log::info);
    }


    public boolean containsExp(String exp){
        return this.exp.contains(exp);
    }

    public int expSize(){
        return exp.size();
    }
}
