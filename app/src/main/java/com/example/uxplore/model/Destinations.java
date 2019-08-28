package com.example.uxplore.model;

public class Destinations {

    private String SpotID;
    private String CityID;
    private String Name;
    private String Description;
    private String Rating;

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getSpotID() {
        return SpotID;
    }

    public void setSpotID(String spotID) {
        SpotID = spotID;
    }

    public String getCityID() {
        return CityID;
    }

    public void setCityID(String cityID) {
        CityID = cityID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public String getParticulars() {
        return Particulars;
    }

    public void setParticulars(String particulars) {
        Particulars = particulars;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    private String ImageURL;
    private String Particulars;
    private String Rate;
    private String Address;
    private String Longitude;
    private String Latitude;


}
