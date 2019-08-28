package com.example.uxplore.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenericResponse {
    public void setData(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    @SerializedName("data")
    @Expose
    private Object data;
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("message")
    @Expose
    private String message;


    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
