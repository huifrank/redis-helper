package com.huifrank.executor;


import com.huifrank.executor.ops.PutOps;
import com.huifrank.executor.ops.Values;

import java.util.List;

public interface PutOpsExe {


  void execute(List<PutOps> opsList, Values values);


}
