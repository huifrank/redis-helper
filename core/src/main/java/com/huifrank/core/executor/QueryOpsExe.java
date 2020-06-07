package com.huifrank.core.executor;

import com.huifrank.core.executor.ops.QueryOps;
import com.huifrank.core.executor.ops.Values;

import java.util.List;

public interface QueryOpsExe {

  List<Object> execute(List<QueryOps> opsList);

  List<Object> execute(List<QueryOps> opsList, Values values);


}
