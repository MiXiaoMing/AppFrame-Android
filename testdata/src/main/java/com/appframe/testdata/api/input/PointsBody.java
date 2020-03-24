package com.appframe.testdata.api.input;

import com.appframe.testdata.entity.PointData;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *  点 ：页面响应 网络请求
 */

public class PointsBody implements Serializable {
    public String recordId;     //记录ID
    public ArrayList<PointData> context;
}
