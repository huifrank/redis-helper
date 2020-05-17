package com.huifrank.core.executor;

import com.huifrank.core.executor.ops.DelOps;
import com.huifrank.core.executor.ops.PutOps;

import java.util.List;

public interface PutOpsExe {

  void execute(List<PutOps> opsList);


}
