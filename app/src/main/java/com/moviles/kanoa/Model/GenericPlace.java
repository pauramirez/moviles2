package com.moviles.kanoa.Model;

/**
 * Created by root on 11/04/18.
 */

public class GenericPlace {
    private String name;
    private String description;
    private double longitude;
    private double latitude;
    private double rating;
    private String imgUrl;

    public GenericPlace() {
    }

    public GenericPlace(String name, String description, double longitude, double latitude, double rating, String imgUrl) {
        this.name = name;
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;
        this.rating = rating;
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}

