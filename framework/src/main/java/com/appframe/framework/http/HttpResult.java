package com.appframe.framework.http;

import java.io.Serializable;

/**
 * 网络接口返回值基类
 * @param <T>
 */
public class HttpResult<T> implements Serializable {
    private String status;
    private String message;
    private String timestamp;
    private T data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
