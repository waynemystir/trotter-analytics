package com.trotter.analytics;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.Key;

import java.lang.String;
import java.util.Date;
import java.lang.Boolean;

@Entity
public class BookingResponse {
  @Parent Key<Booking> theBooking;

  @Id public Long id;
  @Index public Date date;

  public String affiliateConfirmationId;
  public Long itineraryId;
  public Long confirmationId;
  public Boolean processedWithConfirmation;
  public String reservationStatusCode;
  public Boolean nonrefundable;
  public String customerSessionId;

  public BookingResponse() {
    date = new Date();
  };

  public BookingResponse(String affiliateConfirmationId) {
    this();

    if( affiliateConfirmationId != null ) {
      theBooking = Key.create(Booking.class, affiliateConfirmationId);  // Creating the Ancestor key
    } else {
      theBooking = Key.create(Booking.class, "default");
    }
  }

  public BookingResponse(String affiliateConfirmationId, Long itineraryId, Long confirmationId, Boolean processedWithConfirmation,
                          String reservationStatusCode, Boolean nonrefundable, String customerSessionId) {
    this(affiliateConfirmationId);
    this.affiliateConfirmationId = affiliateConfirmationId;
    this.itineraryId = itineraryId;
    this.confirmationId = confirmationId;
    this.processedWithConfirmation = processedWithConfirmation;
    this.reservationStatusCode = reservationStatusCode;
    this.nonrefundable = nonrefundable;
    this.customerSessionId = customerSessionId;
  }

  public String getAffiliateConfirmationId() {
    return affiliateConfirmationId;
  }

  public void setAffiliateConfirmationId(String affiliateConfirmationId) {
    this.affiliateConfirmationId = affiliateConfirmationId;
  }

  public Long getItineraryId() {
    return itineraryId;
  }

  public void setItineraryId(Long itineraryId) {
    this.itineraryId = itineraryId;
  }

  public Long getConfirmationId() {
    return confirmationId;
  }

  public void setConfirmationId(Long confirmationId) {
    this.confirmationId = confirmationId;
  }

  public Boolean getProcessedWithConfirmation() {
    return processedWithConfirmation;
  }

  public void setProcessedWithConfirmation(Boolean processedWithConfirmation) {
    this.processedWithConfirmation = processedWithConfirmation;
  }

  public String getReservationStatusCode() {
    return reservationStatusCode;
  }

  public void setReservationStatusCode(String reservationStatusCode) {
    this.reservationStatusCode = reservationStatusCode;
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
}