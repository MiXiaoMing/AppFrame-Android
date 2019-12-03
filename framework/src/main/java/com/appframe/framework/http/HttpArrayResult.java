package com.appframe.framework.http;

import java.io.Serializable;
import java.util.ArrayList;

public class HttpArrayResult<T> implements Serializable {
    public String status = "";
    public String message = "";
    public String timestamp = "";
    public ArrayList<T> data = new ArrayList<>();
}