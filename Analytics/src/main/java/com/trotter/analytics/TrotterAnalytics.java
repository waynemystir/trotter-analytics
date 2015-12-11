package com.trotter.analytics;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.NotFoundException;

import java.lang.Float;
import java.util.List;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.inject.Named;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

import static com.googlecode.objectify.ObjectifyService.ofy;

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

	private Boolean isValidKey(String apiKey) {
		Boolean rb = apiKey.equals(Constants.TROTTER_API_KEY);
		if (!rb) log.warning("Invalid API key " + apiKey + " was submitted");
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
		ofy().save().entity(launch).now();

		if (Environment.shouldCheckForAdWordsInstall() /*&& newInstall*/) {
			Queue queue = QueueFactory.getDefaultQueue();
			queue.add(TaskOptions.Builder
						.withUrl("/CheckIfAdWordsServlet")
						.method(Method.GET)
						.param("launchid", Long.toString(launch.id))
			);
		}

		log.info("BACK in postLaunch, about to return");
		return launchResponse;
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

		HotelSearch hotelSearch = new HotelSearch(ipAddress, placeName, placeId, displayName, latitude, longitude,
													zoomRadius, numberResults);
		ofy().save().entity(hotelSearch).now();
		return hotelSearchResponse;
	}

	@ApiMethod(name = "postHotelInfo")
	public Hashtable postHotelInfo(@Named("apiKey") String apiKey,
									@Named("ipAddress") String ipAddress,
									@Named("hotelId") String hotelId,
									@Named("hotelName") String hotelName) {

		if (!isValidKey(apiKey)) return null;

		HotelInfo hotelInfo = new HotelInfo(ipAddress, hotelId, hotelName);
		ofy().save().entity(hotelInfo).now();
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
		ofy().save().entity(rooms).now();
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
		ofy().save().entity(bookingRequest).now();
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
		ofy().save().entity(bookingResponse).now();
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
		ofy().save().entity(ee).now();
		return eanErrorResponse;
	}

	@ApiMethod(name = "postTrotterProblem")
	public Hashtable postTrotterProblem(@Named("apiKey") String apiKey,
										@Named("category") String category,
										@Named("shortMessage") String shortMessage,
										@Named("verboseMessage") String verboseMessage) {

		if (!isValidKey(apiKey)) return null;
		TrotterProblem tp = new TrotterProblem(category, shortMessage, verboseMessage);
		ofy().save().entity(tp).now();
		return trotterProblemResponse;
	}
}