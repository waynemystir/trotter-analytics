package com.trotter.analytics;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import java.util.List;
import java.util.ArrayList;

import java.util.logging.Logger;

public class TrotterEanDatastore {

	// Get the Datastore Service
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	private static final Logger log = Logger.getLogger(TrotterEanDatastore.class.getName());

	public ArrayList<BookingRequest> getAllBookingRequestsForAffiliateConfirmationId(String affiliateConfirmationId) {
		Key bookKey = KeyFactory.createKey("Booking", affiliateConfirmationId);
		Query query = new Query("BookingRequest", bookKey)
        		            .setAncestor(bookKey)
                		    .addSort("date", Query.SortDirection.DESCENDING);

		List<Entity> entities = datastore.prepare(query)
                                  .asList(FetchOptions.Builder.withLimit(10));

        ArrayList<BookingRequest> bookingReqs = new ArrayList<BookingRequest>();
        for (Entity entity : entities) {
			BookingRequest bookingRequest = new BookingRequest();
			bookingRequest.setRoom1FirstName((String)entity.getProperty("room1FirstName"));
			bookingRequest.setRoom1LastName((String)entity.getProperty("room1LastName"));
			bookingReqs.add(bookingRequest);
		}

		return bookingReqs;
	}

	public ArrayList<BookingResponse> getAllBookingResponsesForAffiliateConfirmationId(String affiliateConfirmationId) {
		Key bookKey = KeyFactory.createKey("Booking", affiliateConfirmationId);
		Query query = new Query("BookingResponse", bookKey)
        		            .setAncestor(bookKey)
                		    .addSort("date", Query.SortDirection.DESCENDING);

		List<Entity> entities = datastore.prepare(query)
                                  .asList(FetchOptions.Builder.withLimit(10));

        ArrayList<BookingResponse> bookingRess = new ArrayList<BookingResponse>();
        for (Entity entity : entities) {
			BookingResponse bookingResponse = new BookingResponse(affiliateConfirmationId,
				(Long)entity.getProperty("itineraryId"), (Long)entity.getProperty("confirmationId"),
				(Boolean)entity.getProperty("processedWithConfirmation"), (String)entity.getProperty("reservationStatusCode"),
				(Boolean)entity.getProperty("nonrefundable"), (String)entity.getProperty("customerSessionId"));
			bookingRess.add(bookingResponse);
		}

		return bookingRess;
	}

	public ArrayList<Booking> getAllBookings() {
		log.warning("WAYNE");
		ArrayList<Booking> bookings = new ArrayList<Booking>();
		// Use class Query to assemble a query
		Query gaeQuery = new Query("Booking");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		for (Entity entity : list) {
			Booking booking = new Booking();
			booking.setAffiliateConfirmationId((String)entity.getProperty("affiliateConfirmationId"));
			bookings.add(booking);
		}

		return bookings;
	}

	public void printAll() {
  		log.info("WAYNE: TrotterEanDatastore.printAll()");
		// Use class Query to assemble a query
		Query q = new Query("HelloGreeting");

		// Use PreparedQuery interface to retrieve results
		PreparedQuery pq = datastore.prepare(q);

		for (Entity result : pq.asIterable()) {
  			// String firstName = (String) result.getProperty("firstName");
  			// String lastName = (String) result.getProperty("lastName");
  			// Long height = (Long) result.getProperty("height");
  			String message = (String) result.getProperty("message");

  			// System.out.println(firstName + " " + lastName + ", " + height + " inches tall");
  			log.info("WAYNE: da message is:" + message);
		}
	}

}