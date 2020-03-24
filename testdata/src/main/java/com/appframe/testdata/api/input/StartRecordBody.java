package com.appframe.testdata.api.input;

import java.io.Serializable;

/**
 *  开始记录
 */

public class StartRecordBody implements Serializable {
    public String phoneNum;     //手机型号
    public String phoneSystem;     //系统型号
    public String version;     //app版本
    public String appBundleId;     //app id
    public String platform;     //Android / iOS
}
