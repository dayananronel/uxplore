package com.example.uxplore.model;

public class PackageDetails {

    private String PackageID;
    private String SpotName;

    public String getPackageID() {
        return PackageID;
    }

    public void setPackageID(String packageID) {
        PackageID = packageID;
    }

    public String getSpotName() {
        return SpotName;
    }

    public void setSpotName(String spotName) {
        SpotName = spotName;
    }

    public String getSpotDescription() {
        return SpotDescription;
    }

    public void setSpotDescription(String spotDescription) {
        SpotDescription = spotDescription;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public String getSpotRate() {
        return SpotRate;
    }

    public void setSpotRate(String spotRate) {
        SpotRate = spotRate;
    }

    public String getSpotAddress() {
        return SpotAddress;
    }

    public void setSpotAddress(String spotAddress) {
        SpotAddress = spotAddress;
    }

    private String SpotDescription;
    private String ImageURL;
    private String SpotRate;
    private String SpotAddress;

}
