package com.trotter.analytics;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.lang.String;
import java.util.Date;

@Entity
public class HotelInfo {

  @Id public Long id;
  @Index public Date date;

  public String ipAddress;
  public String hotelId;
  public String hotelName;

  public HotelInfo() {
    date = new Date();
  };

  public HotelInfo(String ipAddress, String hotelId, String hotelName) {
    this();
    this.ipAddress = ipAddress;
    this.hotelId = hotelId;
    this.hotelName = hotelName;
  }

  public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  public String getHotelId() {
    return hotelId;
  }

  public void setHotelId(String hotelId) {
    this.hotelId = hotelId;
  }

  public String getHotelName() {
    return hotelName;
  }

  public void setHotelName(String hotelName) {
    this.hotelName = hotelName;
  }
}