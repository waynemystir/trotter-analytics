package com.trotter.analytics;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.Key;

import java.lang.String;
import java.lang.Float;
import java.util.Date;
import java.lang.Boolean;

@Entity
public class BookingRequest {
  @Parent Key<Booking> theBooking;

  @Id public Long id;
  @Index public Date date;

  public String affiliateConfirmationId;
  public String room1FirstName;
  public String room1LastName;
  public String hotelId;
  public String hotelName;
  public String arrivalDate;
  public String departDate;
  public Float chargeableRate;
  public String currencyCode;
  public String email;
  public String homePhone;
  public String rateKey;
  public String roomTypeCode;
  public String rateCode;
  public String roomDescription;
  public String bedTypeId;
  public String smokingPref;
  public Boolean nonrefundable;
  public String customerSessionId;
  public String ipAddress;
  public String eanCid;

  public BookingRequest() {
    date = new Date();
  };

  public BookingRequest(String affiliateConfirmationId) {
    this();

    if( affiliateConfirmationId != null ) {
      theBooking = Key.create(Booking.class, affiliateConfirmationId);  // Creating the Ancestor key
    } else {
      theBooking = Key.create(Booking.class, "default");
    }
  }

  public BookingRequest(String affiliateConfirmationId, String room1FirstName, String room1LastName, String hotelId,
                          String hotelName, String arrivalDate, String departDate, Float chargeableRate, String currencyCode,
                          String email, String homePhone, String rateKey, String roomTypeCode, String rateCode,
                          String roomDescription, String bedTypeId, String smokingPref, Boolean nonrefundable,
                          String customerSessionId, String ipAddress, String eanCid) {
  	this(affiliateConfirmationId);
    this.affiliateConfirmationId = affiliateConfirmationId;
  	this.room1FirstName = room1FirstName;
  	this.room1LastName = room1LastName;
    this.hotelId = hotelId;
    this.hotelName = hotelName;
    this.arrivalDate = arrivalDate;
    this.departDate = departDate;
    this.chargeableRate = chargeableRate;
    this.currencyCode = currencyCode;
    this.email = email;
    this.homePhone = homePhone;
    this.rateKey = rateKey;
    this.roomTypeCode = roomTypeCode;
    this.rateCode = rateCode;
    this.roomDescription = roomDescription;
    this.bedTypeId = bedTypeId;
    this.smokingPref = smokingPref;
    this.nonrefundable = nonrefundable;
    this.customerSessionId = customerSessionId;
    this.ipAddress = ipAddress;
    this.eanCid = eanCid;
  }

  public String getAffiliateConfirmationId() {
    return affiliateConfirmationId;
  }

  public void setAffiliateConfirmationId(String affiliateConfirmationId) {
    this.affiliateConfirmationId = affiliateConfirmationId;
  }

  public String getRoom1FirstName() {
    return room1FirstName;
  }

  public void setRoom1FirstName(String room1FirstName) {
    this.room1FirstName = room1FirstName;
  }

  public String getRoom1LastName() {
    return room1LastName;
  }

  public void setRoom1LastName(String room1LastName) {
    this.room1LastName = room1LastName;
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

  public String getArrivalDate() {
    return arrivalDate;
  }

  public void setArrivalDate(String arrivalDate) {
    this.arrivalDate = arrivalDate;
  }

  public String getDepartDate() {
    return departDate;
  }

  public void setDepartDate(String departDate) {
    this.departDate = departDate;
  }

  public Float getChargeableRate() {
    return chargeableRate;
  }

  public void setChargeableRate(Float chargeableRate) {
    this.chargeableRate = chargeableRate;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getHomePhone() {
    return homePhone;
  }

  public void setHomePhone(String homePhone) {
    this.homePhone = homePhone;
  }

  public String getRateKey() {
    return rateKey;
  }

  public void setRateKey(String rateKey) {
    this.rateKey = rateKey;
  }

  public String getRoomTypeCode() {
    return roomTypeCode;
  }

  public void setRoomTypeCode(String roomTypeCode) {
    this.roomTypeCode = roomTypeCode;
  }

  public String getRateCode() {
    return rateCode;
  }

  public void setRateCode(String rateCode) {
    this.rateCode = rateCode;
  }

  public String getRoomDescription() {
    return roomDescription;
  }

  public void setRoomDescription(String roomDescription) {
    this.roomDescription = roomDescription;
  }

  public String getBedTypeId() {
    return bedTypeId;
  }

  public void setBedTypeId(String bedTypeId) {
    this.bedTypeId = bedTypeId;
  }

  public String getSmokingPref() {
    return smokingPref;
  }

  public void setSmokingPref(String smokingPref) {
    this.smokingPref = smokingPref;
  }

  public Boolean getNonrefundable() {
    return nonrefundable;
  }

  public void setNonrefundable(Boolean nonrefundable) {
    this.nonrefundable = nonrefundable;
  }

  public String getCustomerSessionId() {
    return customerSessionId;
  }

  public void setCustomerSessionId(String customerSessionId) {
    this.customerSessionId = customerSessionId;
  }

  public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  public String getEanCid() {
    return eanCid;
  }

  public void setEanCid(String eanCid) {
    this.eanCid = eanCid;
  }
}