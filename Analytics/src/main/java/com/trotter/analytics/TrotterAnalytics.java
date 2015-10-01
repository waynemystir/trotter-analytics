package com.trotter.analytics;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.users.User;

import java.lang.Float;
import java.util.ArrayList;

import javax.inject.Named;

import com.googlecode.objectify.ObjectifyService;

import java.util.logging.Logger;

/**
 * Defines v1 of a Trotter Analytics
 */
@Api(name = "wanalytics",
    version = "v1",
    scopes = {Constants.EMAIL_SCOPE},
    audiences = {Constants.ANDROID_AUDIENCE}
)
public class TrotterAnalytics {
  
  private static final Logger log = Logger.getLogger(TrotterEanDatastore.class.getName());

  private Boolean isValidKey(String apiKey) {
      Boolean rb = apiKey.equals(Constants.TROTTER_API_KEY);

      if (!rb) 
          log.warning("Invalid API key " + apiKey + " was submitted");

      return rb;
  }

  @ApiMethod(name = "postBookingRequest")
  public void postBookingRequest(@Named("apiKey") String apiKey,
                                @Named("affiliateConfirmationId") String affiliateConfirmationId,
                              	@Named("room1FirstName") String room1FirstName,
                              	@Named("room1LastName") String room1LastName,
                              	@Named("hotelId") String hotelId,
                          	  	@Named("hotelName") String hotelName,
                          	  	@Named("arrivalDate") String arrivalDate,
                          	  	@Named("departDate") String departDate,
                          	  	@Named("chargeableRate") Float chargeableRate,
                          	  	@Named("email") String email,
                          		@Named("homePhone") String homePhone,
                          		@Named("rateKey") String rateKey,
                          		@Named("roomTypeCode") String roomTypeCode,
                          		@Named("rateCode") String rateCode,
                          		@Named("roomDescription") String roomDescription,
                          		@Named("bedTypeId") String bedTypeId,
                          		@Named("smokingPref") String smokingPref,
                              @Named("nonrefundable") Boolean nonrefundable,
                              @Named("customerSessionId") String customerSessionId) {

    if (!isValidKey(apiKey)) return;

    BookingRequest bookingRequest = new BookingRequest(affiliateConfirmationId, room1FirstName, room1LastName, hotelId,
                          hotelName, arrivalDate, departDate, chargeableRate, email, homePhone, rateKey, roomTypeCode, rateCode,
                          roomDescription, bedTypeId, smokingPref, nonrefundable, customerSessionId);
    ObjectifyService.ofy().save().entity(bookingRequest).now();
  }

  @ApiMethod(name = "postBookingResponse")
  public void postBookingResponse(@Named("apiKey") String apiKey,
                                @Named("affiliateConfirmationId") String affiliateConfirmationId,
                              @Named("itineraryId") Long itineraryId,
                              @Named("confirmationId") Long confirmationId,
                              @Named("processedWithConfirmation") Boolean processedWithConfirmation,
                              @Named("reservationStatusCode") String reservationStatusCode,
                              @Named("nonrefundable") Boolean nonrefundable,
                              @Named("customerSessionId") String customerSessionId) {

    if (!isValidKey(apiKey)) return;

    BookingResponse bookingResponse = new BookingResponse(affiliateConfirmationId, itineraryId, confirmationId, 
                                            processedWithConfirmation, reservationStatusCode, nonrefundable, customerSessionId);
    ObjectifyService.ofy().save().entity(bookingResponse).now();
  }

  // @ApiMethod(name = "getAllBookingRequestsForAffiliateConfirmationId")
  // public ArrayList<BookingRequest> getAllBookingRequestsForAffiliateConfirmationId(@Named("affiliateConfirmationId") String affiliateConfirmationId) {
  //     return new TrotterEanDatastore().getAllBookingRequestsForAffiliateConfirmationId(affiliateConfirmationId);
  // }

  // @ApiMethod(name = "getAllBookingResponsesForAffiliateConfirmationId")
  // public ArrayList<BookingResponse> getAllBookingResponsesForAffiliateConfirmationId(@Named("affiliateConfirmationId") String affiliateConfirmationId) {
  //     return new TrotterEanDatastore().getAllBookingResponsesForAffiliateConfirmationId(affiliateConfirmationId);
  // }

  @ApiMethod(name = "postEanError")
  public void postEanError(@Named("apiKey") String apiKey,
                                @Named("itineraryId") Long itineraryId,
                              @Named("handling") String handling,
                              @Named("category") String category,
                              @Named("presentationMessage") String presentationMessage,
                              @Named("verboseMessage") String verboseMessage) {

    if (!isValidKey(apiKey)) return;

      EanError ee = new EanError(itineraryId, handling, category, presentationMessage, verboseMessage);
      ObjectifyService.ofy().save().entity(ee).now();
  }

  @ApiMethod(name = "postTrotterProblem")
  public void postTrotterProblem(@Named("apiKey") String apiKey,
                                @Named("category") String category,
                              @Named("shortMessage") String shortMessage,
                              @Named("verboseMessage") String verboseMessage) {

    if (!isValidKey(apiKey)) return;

      TrotterProblem tp = new TrotterProblem(category, shortMessage, verboseMessage);
      ObjectifyService.ofy().save().entity(tp).now();
  }
}