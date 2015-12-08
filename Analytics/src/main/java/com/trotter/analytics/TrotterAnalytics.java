package com.trotter.analytics;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.users.User;
import com.google.appengine.api.ThreadManager;
 
import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
 
import com.google.appengine.api.urlfetch.HTTPHeader;

import java.lang.Float;
import java.util.List;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.inject.Named;

import com.googlecode.objectify.ObjectifyService;

import java.util.logging.Logger;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
  private static final Hashtable launchResponse;
  private static final Hashtable hotelSearchResponse;
  private static final Hashtable hotelInfoResponse;
  private static final Hashtable roomsResponse;
  private static final Hashtable eanErrorResponse;
  private static final Hashtable trotterProblemResponse;

  static {
    launchResponse = new Hashtable();
    hotelSearchResponse = new Hashtable();
    hotelInfoResponse = new Hashtable();
    roomsResponse = new Hashtable();
    eanErrorResponse = new Hashtable();
    trotterProblemResponse = new Hashtable();

    launchResponse.put("verboseAnalytics", true);
    hotelSearchResponse.put("verboseAnalytics", true);
    hotelInfoResponse.put("verboseAnalytics", true);
    roomsResponse.put("verboseAnalytics", true);
    eanErrorResponse.put("verboseAnalytics", true);
    trotterProblemResponse.put("verboseAnalytics", true);
    
    launchResponse.put("apiMethod", "postLaunch");
    hotelSearchResponse.put("apiMethod", "postHotelSearch");
    hotelInfoResponse.put("apiMethod", "postHotelInfo");
    roomsResponse.put("apiMethod", "postRooms");
    eanErrorResponse.put("apiMethod", "postEanError");
    trotterProblemResponse.put("apiMethod", "postTrotterProblem");
  }

  private static final String ADWORDS_URL = "https://www.googleadservices.com/pagead/conversion";
  private static final String ADWORDS_CONVERSION_ID = "12345";
  private static final String ADWORDS_CONVERSION_LABEL = "waynester";

  private static final String ADWORDS_PK_LABEL = "label";
  private static final String ADWORDS_PK_RDID = "rdid";
  private static final String ADWORDS_PK_BUNDLEID = "bundleid";
  private static final String ADWORDS_PK_IDTYPE = "idtype";
  private static final String ADWORDS_PK_LIMIT_AD_TRACKING_STATUS = "lat";

  private Boolean isValidKey(String apiKey) {
      Boolean rb = apiKey.equals(Constants.TROTTER_API_KEY);

      if (!rb) 
          log.warning("Invalid API key " + apiKey + " was submitted");

      return rb;
  }

  @ApiMethod(name = "postLaunch")
  public Hashtable postLaunch(@Named("apiKey") String apiKey,
                              @Named("ipAddress") String ipAddress,
                              @Named("osType") String osType,
                              @Named("osVersion") String osVersion,
                              @Named("deviceType") String deviceType,
                              @Named("newInstall") Boolean newInstall,
                              @Named("iosIdfa") String iosIdfa,
                              @Named("limitAdTracking") Boolean limitAdTracking,
                              @Named("bundleId") String bundleId,
                              @Named("bundleVersion") String bundleVersion,
                              @Named("appVersion") String appVersion) {

    if (!isValidKey(apiKey)) return null;

    Launch launch = new Launch(ipAddress, osType, osVersion, deviceType, newInstall, false, iosIdfa, bundleId, bundleVersion,
                                  appVersion, limitAdTracking);
    ObjectifyService.ofy().save().entity(launch).now();

    checkIfAdWordsInstall(launch);
    return launchResponse;
  }

  protected void checkIfAdWordsInstall(Launch launch) {
        log.info("START checkIfAdWordsInstall: " + launch.id);
        URLFetchService fetcher = URLFetchServiceFactory.getURLFetchService();
        try {
            // URL url = new URL("https://api.ipify.org");

            String us = String.format("%s/%s/?%s=%s&%s=%s&%s=%s&%s=%b", ADWORDS_URL, ADWORDS_CONVERSION_ID, ADWORDS_PK_LABEL,
                                                    ADWORDS_CONVERSION_LABEL, ADWORDS_PK_RDID, launch.iosIdfa,
                                                    ADWORDS_PK_IDTYPE, "idfa", ADWORDS_PK_LIMIT_AD_TRACKING_STATUS,
                                                    launch.limitAdTracking);
            log.info("checkIfAdWordsInstall URLstr: " + us);
            URL url = new URL(us);
            Future future = fetcher.fetchAsync(url);
 
            // Other stuff can happen here!
 
            HTTPResponse response = (HTTPResponse) future.get();
            byte[] content = response.getContent();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bos.write(content);
            String responseString = new String(bos.toByteArray());
            int responseCode = response.getResponseCode();
            log.info(String.format("caw code:%d rs:%s", responseCode, responseString));
            URL finalUrl = response.getFinalUrl();
            List headers = response.getHeaders();
 
            // for(HTTPHeader header : headers) {
            //     String headerName = header.getName();
            //     String headerValue = header.getValue();
            // }
 
        } catch (IOException e) {
            // new URL throws MalformedUrlException, which is impossible for us here
        } catch (InterruptedException e) {
            // Exception from using java.concurrent.Future
        } catch (ExecutionException e) {
            // Exception from using java.concurrent.Future
            e.printStackTrace();
        }
 
    }

  @ApiMethod(name = "postHotelSearch")
  public Hashtable postHotelSearch(@Named("apiKey") String apiKey,
                              @Named("ipAddress") String ipAddress,
                              @Named("placeName") String placeName,
                              @Named("placeId") String placeId,
                              @Named("displayName") String displayName,
                              @Named("latitude") Double latitude,
                              @Named("longitude") Double longitude,
                              @Named("zoomRadius") Double zoomRadius,
                              @Named("numberResults") Integer numberResults) {

    if (!isValidKey(apiKey)) return null;

    HotelSearch hotelSearch = new HotelSearch(ipAddress, placeName, placeId, displayName, latitude, longitude, zoomRadius, numberResults);
    ObjectifyService.ofy().save().entity(hotelSearch).now();

    return hotelSearchResponse;
  }

  @ApiMethod(name = "postHotelInfo")
  public Hashtable postHotelInfo(@Named("apiKey") String apiKey,
                              @Named("ipAddress") String ipAddress,
                              @Named("hotelId") String hotelId,
                              @Named("hotelName") String hotelName) {

    if (!isValidKey(apiKey)) return null;

    HotelInfo hotelInfo = new HotelInfo(ipAddress, hotelId, hotelName);
    ObjectifyService.ofy().save().entity(hotelInfo).now();

    return hotelInfoResponse;
  }

  @ApiMethod(name = "postRooms")
  public Hashtable postRooms(@Named("apiKey") String apiKey,
                              @Named("ipAddress") String ipAddress,
                              @Named("hotelId") String hotelId,
                              @Named("hotelName") String hotelName,
                              @Named("numberRooms") Integer numberRooms) {

    if (!isValidKey(apiKey)) return null;

    Rooms rooms = new Rooms(ipAddress, hotelId, hotelName, numberRooms);
    ObjectifyService.ofy().save().entity(rooms).now();

    return roomsResponse;
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
                                @Named("currencyCode") String currencyCode,
                          	  	@Named("email") String email,
                          		@Named("homePhone") String homePhone,
                          		@Named("rateKey") String rateKey,
                          		@Named("roomTypeCode") String roomTypeCode,
                          		@Named("rateCode") String rateCode,
                          		@Named("roomDescription") String roomDescription,
                          		@Named("bedTypeId") String bedTypeId,
                          		@Named("smokingPref") String smokingPref,
                              @Named("nonrefundable") Boolean nonrefundable,
                              @Named("customerSessionId") String customerSessionId,
                              @Named("ipAddress") String ipAddress,
                              @Named("eanCid") String eanCid) {

    if (!isValidKey(apiKey)) return;

    BookingRequest bookingRequest = new BookingRequest(affiliateConfirmationId, room1FirstName, room1LastName, hotelId,
                          hotelName, arrivalDate, departDate, chargeableRate, currencyCode, email, homePhone, rateKey, roomTypeCode, rateCode,
                          roomDescription, bedTypeId, smokingPref, nonrefundable, customerSessionId, ipAddress, eanCid);
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
                              @Named("customerSessionId") String customerSessionId,
                              @Named("ipAddress") String ipAddress,
                              @Named("eanCid") String eanCid) {

    if (!isValidKey(apiKey)) return;

    BookingResponse bookingResponse = new BookingResponse(affiliateConfirmationId, itineraryId, confirmationId, 
                                            processedWithConfirmation, reservationStatusCode, nonrefundable,
                                            customerSessionId, ipAddress, eanCid);
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
  public Hashtable postEanError(@Named("apiKey") String apiKey,
                                @Named("itineraryId") Long itineraryId,
                              @Named("handling") String handling,
                              @Named("category") String category,
                              @Named("presentationMessage") String presentationMessage,
                              @Named("verboseMessage") String verboseMessage) {

    if (!isValidKey(apiKey)) return null;

    EanError ee = new EanError(itineraryId, handling, category, presentationMessage, verboseMessage);
    ObjectifyService.ofy().save().entity(ee).now();

    return eanErrorResponse;
  }

  @ApiMethod(name = "postTrotterProblem")
  public Hashtable postTrotterProblem(@Named("apiKey") String apiKey,
                                @Named("category") String category,
                              @Named("shortMessage") String shortMessage,
                              @Named("verboseMessage") String verboseMessage) {

    if (!isValidKey(apiKey)) return null;

    TrotterProblem tp = new TrotterProblem(category, shortMessage, verboseMessage);
    ObjectifyService.ofy().save().entity(tp).now();

    return trotterProblemResponse;
  }
}