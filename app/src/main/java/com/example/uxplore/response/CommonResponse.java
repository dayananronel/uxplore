package com.example.uxplore.response;

import com.example.uxplore.model.UserProfile;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CommonResponse  {
    @SerializedName("data")
    @Expose
    private List<UserProfile> data;
    @SerializedName("status")
    @Expose
    private String status;


    @SerializedName("message")
    @Expose
    private String message;

    public void setData(List<UserProfile> data) {
        this.data = data;
    }
    public List<UserProfile> getData() {
        return data;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
