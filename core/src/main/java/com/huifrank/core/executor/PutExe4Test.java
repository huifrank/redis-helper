package com.huifrank.core.executor;

import com.huifrank.core.executor.ops.DelOps;
import com.huifrank.core.executor.ops.PutOps;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 供单元测试用的实现
 */
@Slf4j
public class PutExe4Test implements PutOpsExe{

    List<String> exp = Collections.emptyList();

    private static PutExe4Test instance = new PutExe4Test();

    public static PutExe4Test getInstance() {
        return instance;
    }

    private PutExe4Test(){}

    @Override
    public void execute(List<PutOps> opsList) {
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
