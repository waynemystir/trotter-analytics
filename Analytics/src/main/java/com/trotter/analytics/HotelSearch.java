package com.trotter.analytics;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.lang.String;
import java.util.Date;

@Entity
public class HotelSearch {

  @Id public Long id;
  @Index public Date date;

  public String ipAddress;
  public String placeName;
  public String placeId;
  public String displayName;
  public Double latitude;
  public Double longitude;
  public Double zoomRadius;
  public Integer numberResults;

  public HotelSearch() {
    date = new Date();
  };

  public HotelSearch(String ipAddress, String placeName, String placeId, String displayName, Double latitude, Double longitude,
                      Double zoomRadius, Integer numberResults) {
    this();
    this.ipAddress = ipAddress;
    this.placeName = placeName;
    this.placeId = placeId;
    this.displayName = displayName;
    this.latitude = latitude;
    this.longitude = longitude;
    this.zoomRadius = zoomRadius;
    this.numberResults = numberResults;
  }

  public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  public String getPlaceName() {
    return placeName;
  }

  public void setPlaceName(String placeName) {
    this.placeName = placeName;
  }

  public String getPlaceId() {
    return placeId;
  }

  public void setPlaceId(String placeId) {
    this.placeId = placeId;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public Double getZoomRadius() {
    return zoomRadius;
  }

  public void setZoomRadius(Double zoomRadius) {
    this.zoomRadius = zoomRadius;
  }

  public Integer getNumberResults() {
    return numberResults;
  }

  public void setNumberResults(Integer numberResults) {
    this.numberResults = numberResults;
  }
}