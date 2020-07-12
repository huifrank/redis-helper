package com.huifrank.executor;

import com.huifrank.executor.ops.DelOps;
import com.huifrank.executor.ops.Values;

import java.util.List;

public interface DeleteOpsExe {

  void execute(List<DelOps> opsList, Values values);


}
