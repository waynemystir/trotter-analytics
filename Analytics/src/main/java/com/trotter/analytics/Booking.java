package com.trotter.analytics;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * The @Entity tells Objectify about our entity.  We also register it in
 * OfyHelper.java -- very important.
 *
 * This is never actually created, but gives a hint to Objectify about our Ancestor key.
 */
@Entity
public class Booking {
  @Id public String affiliateConfirmationId;

  public String getAffiliateConfirmationId() {
  	return affiliateConfirmationId;
  }

  public void setAffiliateConfirmationId(String affiliateConfirmationId) {
  	this.affiliateConfirmationId = affiliateConfirmationId;
  }
}