package com.example.uxplore.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserProfile  implements Serializable {

    @SerializedName("ID")
    @Expose
    private String ID;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getUserTypeID() {
        return UserTypeID;
    }

    public void setUserTypeID(String userTypeID) {
        UserTypeID = userTypeID;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getDateAdded() {
        return DateAdded;
    }

    public void setDateAdded(String dateAdded) {
        DateAdded = dateAdded;
    }

    public String getLastLogin() {
        return LastLogin;
    }

    public void setLastLogin(String lastLogin) {
        LastLogin = lastLogin;
    }

    public String getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(String isOnline) {
        this.isOnline = isOnline;
    }

    public String getExtra1() {
        return Extra1;
    }

    public void setExtra1(String extra1) {
        Extra1 = extra1;
    }

    public String getExtra2() {
        return Extra2;
    }

    public void setExtra2(String extra2) {
        Extra2 = extra2;
    }

    public String getExtra3() {
        return Extra3;
    }

    public void setExtra3(String extra3) {
        Extra3 = extra3;
    }

    public String getExtra4() {
        return Extra4;
    }

    public void setExtra4(String extra4) {
        Extra4 = extra4;
    }

    public String getNotes1() {
        return Notes1;
    }

    public void setNotes1(String notes1) {
        Notes1 = notes1;
    }

    public String getNotes2() {
        return Notes2;
    }

    public void setNotes2(String notes2) {
        Notes2 = notes2;
    }

    @SerializedName("UserID")
    @Expose
    private String UserID;

    @SerializedName("UserTypeID")
    @Expose
    private String UserTypeID;

    @SerializedName("EmailAddress")
    @Expose
    private String EmailAddress;

    @SerializedName("MobileNumber")
    @Expose
    private String MobileNumber;

    @SerializedName("IPAddress")
    @Expose
    private String IPAddress;

    @SerializedName("Status")
    @Expose
    private String Status;

    @SerializedName("DateAdded")
    @Expose
    private String DateAdded;

    @SerializedName("LastLogin")
    @Expose
    private String LastLogin;

    @SerializedName("isOnline")
    @Expose
    private String isOnline;

    @SerializedName("Extra1")
    @Expose
    private String Extra1;

    @SerializedName("Extra2")
    @Expose
    private String Extra2;

    @SerializedName("Extra3")
    @Expose
    private String Extra3;

    @SerializedName("Extra4")
    @Expose
    private String Extra4;

    @SerializedName("Notes1")
    @Expose
    private String Notes1;

    @SerializedName("Notes2")
    @Expose
    private String Notes2;


}
