package com.huifrank.core.executor.ops;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class Values {

    List<Object> argsList;

}
