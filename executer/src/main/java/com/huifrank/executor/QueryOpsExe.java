package com.huifrank.executor;

import com.huifrank.executor.ops.QueryOps;
import com.huifrank.executor.ops.Values;

import java.util.List;

public interface QueryOpsExe {


  List<Object> execute(List<QueryOps> opsList, Values values);


}
