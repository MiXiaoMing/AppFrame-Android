package com.appframe.testdata.api.input;

import com.appframe.testdata.entity.Performance;
import com.appframe.testdata.entity.PointData;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *  性能 ：cpu占用率 内存大小
 */

public class PerformancesBody implements Serializable {
    public String recordId;     //记录ID
    public ArrayList<Performance> context;
}
