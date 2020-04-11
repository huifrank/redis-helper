package com.huifrank.core.executor;

import com.huifrank.core.executor.ops.DelOps;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class DeleteOpsExe {


    public void execute(List<DelOps> opsList){


        log.info("-----Do Delete------");
        opsList.forEach(o-> log.info(o.getExpression()));
        log.info("-------Delete Finish-------");


    }
}
