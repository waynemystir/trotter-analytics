package com.trotter.analytics;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.Key;

import java.lang.String;
import java.util.Date;

@Entity
public class BookingResponse {
  @Parent Key<Booking> theBooking;

  @Id public Long id;
  @Index public Date date;

  public String affiliateConfirmationId;
  public Long itineraryId;
  public Long confirmationId;
  public String reservationStatusCode;

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

  public BookingResponse(String affiliateConfirmationId, Long itineraryId, Long confirmationId) {
    this(affiliateConfirmationId);
    this.affiliateConfirmationId = affiliateConfirmationId;
    this.itineraryId = itineraryId;
    this.confirmationId = confirmationId;
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

  public String getReservationStatusCode() {
    return reservationStatusCode;
  }

  public void setReservationStatusCode(String reservationStatusCode) {
    this.reservationStatusCode = reservationStatusCode;
  }
}