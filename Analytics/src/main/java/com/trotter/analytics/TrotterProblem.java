package com.trotter.analytics;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.lang.String;
import java.util.Date;

@Entity
public class TrotterProblem {

  @Id public Long id;
  @Index public Date date;

  public String category;
  public String shortMessage;
  public String verboseMessage;

  public TrotterProblem() {
    date = new Date();
  };

  public TrotterProblem(String category, String shortMessage, String verboseMessage) {
    this();
    this.category = category;
    this.shortMessage = shortMessage;
    this.verboseMessage = verboseMessage;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getShortMessage() {
    return shortMessage;
  }

  public void setShortMessage(String shortMessage) {
    this.shortMessage = shortMessage;
  }

  public String getVerboseMessage() {
    return verboseMessage;
  }

  public void setVerboseMessage(String verboseMessage) {
    this.verboseMessage = verboseMessage;
  }
}