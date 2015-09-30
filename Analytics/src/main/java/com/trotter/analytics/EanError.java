package com.trotter.analytics;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.lang.String;
import java.util.Date;

@Entity
public class EanError {

  @Id public Long id;
  @Index public Date date;

  public Long itineraryId;
  public String handling;
  public String category;
  public String presentationMessage;
  public String verboseMessage;

  public EanError() {
    date = new Date();
  };

  public EanError(Long itineraryId, String handling, String category, String presentationMessage, String verboseMessage) {
    this();
    this.itineraryId = itineraryId;
    this.handling = handling;
    this.category = category;
    this.presentationMessage = presentationMessage;
    this.verboseMessage = verboseMessage;
  }

  public Long getItineraryId() {
    return itineraryId;
  }

  public void setItineraryId(Long itineraryId) {
    this.itineraryId = itineraryId;
  }

  public String getHandling() {
    return handling;
  }

  public void setHandling(String handling) {
    this.handling = handling;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getPresentationMessage() {
    return presentationMessage;
  }

  public void setPresentationMessage(String presentationMessage) {
    this.presentationMessage = presentationMessage;
  }

  public String getVerboseMessage() {
    return verboseMessage;
  }

  public void setVerboseMessage(String verboseMessage) {
    this.verboseMessage = verboseMessage;
  }
}