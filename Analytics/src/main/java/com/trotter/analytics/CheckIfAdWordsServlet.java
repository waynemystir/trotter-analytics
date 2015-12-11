package com.trotter.analytics;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.URL;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;

import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import com.google.appengine.api.urlfetch.HTTPResponse;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.logging.Logger;

public class CheckIfAdWordsServlet extends HttpServlet {
  
	private static final Logger log = Logger.getLogger(TrotterEanDatastore.class.getName());

	private static final String ADWORDS_URL = "https://www.googleadservices.com/pagead/conversion";
	private static final String ADWORDS_CONVERSION_ID = "12345";
	private static final String ADWORDS_CONVERSION_LABEL = "waynester";

	private static final String ADWORDS_PK_LABEL = "label";
	private static final String ADWORDS_PK_RDID = "rdid";
	private static final String ADWORDS_PK_BUNDLEID = "bundleid";
	private static final String ADWORDS_PK_IDTYPE = "idtype";
	private static final String ADWORDS_PK_LIMIT_AD_TRACKING_STATUS = "lat";

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			   throws IOException, ServletException {
		if (!Environment.shouldCheckForAdWordsInstall()) return;
		final String lid = request.getParameter("launchid");
		Long launchid = null;
		try {
			launchid = Long.parseLong(lid);
		} catch (NumberFormatException e) {
			log.warning("Exception occurred when trying to parse lid: " + e.getMessage());
			return;
		}
		Launch launch = ofy().load().type(Launch.class).id(launchid).now();
		if (launch != null /*&& launch.newInstall*/) checkIfAdWordsInstall(launch);
		else log.warning("We're not going to check this launch");
	}

	protected void checkIfAdWordsInstall(Launch launch) {
		log.info("START checkIfAdWordsInstall: " + launch.id);
		URLFetchService fetcher = URLFetchServiceFactory.getURLFetchService();
		String responseString = null;
		int responseCode = -1;
		try {
			String us = String.format("%s/%s/?%s=%s&%s=%s&%s=%s&%s=%d", ADWORDS_URL, ADWORDS_CONVERSION_ID,
											ADWORDS_PK_LABEL, ADWORDS_CONVERSION_LABEL,
											ADWORDS_PK_RDID, launch.iosIdfa,
											ADWORDS_PK_IDTYPE, "idfa",
											ADWORDS_PK_LIMIT_AD_TRACKING_STATUS, launch.limitAdTracking ? 1 : 0);
			log.info("checkIfAdWordsInstall URLstr: " + us);
			URL url = new URL(us);
			Future future = fetcher.fetchAsync(url); 
			HTTPResponse response = (HTTPResponse) future.get();

			byte[] content = response.getContent();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			bos.write(content);

			responseString = new String(bos.toByteArray());
			responseCode = response.getResponseCode();
			log.info(String.format("caw code:%d rs:%s", responseCode, responseString));
 
		} catch (IOException e) {
			// new URL throws MalformedUrlException
			e.printStackTrace();
			return;
		} catch (InterruptedException e) {
			// Exception from using java.concurrent.Future
			e.printStackTrace();
			return;
		} catch (ExecutionException e) {
			// Exception from using java.concurrent.Future
			e.printStackTrace();
			return;
		}

		switch (responseCode) {
			case 200:
				log.info(String.format("checkIfAdWordsInstall install id %s wasn't generated by AdWords", launch.id));
				break;
			case 302:
				launch.adWordsInstall = true;
				ofy().save().entity(launch).now();
			case 400:
				log.warning(String.format("checkIfAdWordsInstall install id %s 400 Bad Request", launch.id));
			default:
				log.warning(String.format("checkIfAdWordsInstall install id %s Http Status Code %d", launch.id, responseCode));
		}
 
	}

}