package com.moviles.kanoa.Model;

/**
 * Created by root on 11/04/18.
 */

public class Museum implements GenericPlaceInterface{

    //attributes of the interface, all generic places attributes
    private String address;
    private String city;
    private double cost;
    private String currency;
    private String description;
    private double distanceToPlace;
    private String hoursOpen;
    private String imgUrl;
    private double latitude;
    private double longitude;
    private String name;
    private int numberOfFollowers;
    private double rating;
    private String timeToPlace;
    private String url;


    public Museum() {
    }


    public Museum(String address, String city, double cost, String currency, String description, double distanceToPlace, String hoursOpen, String imgUrl, double latitude, double longitude, String name, int numberOfFollowers, double rating,String timeToPlace ,String url) {
        this.address = address;
        this.city = city;
        this.cost = cost;
        this.currency = currency;
        this.description = description;
        this.distanceToPlace = distanceToPlace;
        this.hoursOpen = hoursOpen;
        this.imgUrl = imgUrl;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.numberOfFollowers = numberOfFollowers;
        this.rating = rating;
        this.timeToPlace = timeToPlace;
        this.url = url;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public double getCost() {
        return cost;
    }

    @Override
    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String getCurrency() {
        return currency;
    }

    @Override
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public double getDistanceToPlace() {
        return distanceToPlace;
    }

    @Override
    public void setDistanceToPlace(double distanceToPlace) {
        this.distanceToPlace = distanceToPlace;
    }

    @Override
    public String getHoursOpen() {
        return hoursOpen;
    }

    @Override
    public void setHoursOpen(String hoursOpen) {
        this.hoursOpen = hoursOpen;
    }

    @Override
    public String getImgUrl() {
        return imgUrl;
    }

    @Override
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public double getLatitude() {
        return latitude;
    }

    @Override
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }

    @Override
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getNumberOfFollowers() {
        return numberOfFollowers;
    }

    @Override
    public void setNumberOfFollowers(int numberOfFollowers) {
        this.numberOfFollowers = numberOfFollowers;
    }
    @Override
    public double getRating() {
        return rating;
    }

    @Override
    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String getTimeToPlace() {
        return timeToPlace;
    }

    @Override
    public void setTimeToPlace(String timeToPlace) {
        this.timeToPlace = timeToPlace;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }
}

