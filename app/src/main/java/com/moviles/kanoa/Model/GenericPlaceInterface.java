package com.moviles.kanoa.Model;

/**
 * Created by root on 18/04/18.
 */

public interface GenericPlaceInterface {

    //adress methods
    public String getAddress();
    public void setAddress(String address);

    //city Methods
    public String getCity();
    public void setCity(String city);

    //Cost methods
    public double getCost();
    public void setCost(double cost);

    //Currency methods
    public String getCurrency();
    public void setCurrency(String currency);

    //Description methods
    public String getDescription();
    public void setDescription(String description);

    //Distance to the place methods
    public double getDistanceToPlace();
    public void setDistanceToPlace(double distanceToPlace);

    //Hours open methods
    public String getHoursOpen();
    public void setHoursOpen(String hoursOpen);

    // img url methods
    public String getImgUrl();
    public void setImgUrl(String imgUrl);

    // latitude methods
    public double getLatitude();
    public void setLatitude(double latitude);

    //longitude methods
    public double getLongitude();
    public void setLongitude(double longitude);

    //name methods
    public String getName();
    public void setName(String name);

    //number of followers methods
    public int getNumberOfFollowers();
    public void setNumberOfFollowers(int numberOfFollowers);

    //rating methods
    public double getRating();
    public void setRating(double rating);

    //time to the place methods
    public String getTimeToPlace();
    public void setTimeToPlace(String timeToPlace);

    //website url methods
    public String getUrl();
    public void setUrl(String url);


}
