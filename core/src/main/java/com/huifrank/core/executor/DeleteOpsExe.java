package com.huifrank.core.executor;

import com.huifrank.core.executor.ops.DelOps;
import com.huifrank.core.executor.ops.Values;

import java.util.List;

public interface DeleteOpsExe {

  void execute(List<DelOps> opsList, Values values);


}
