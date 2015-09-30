package com.trotter.analytics;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.lang.String;
import java.util.Date;

@Entity
public class IpAddress {

  @Id public Long id;
  @Index public Date date;

  public String ipAdd;

  public IpAddress() {
    date = new Date();
  };

  public IpAddress(String ipAdd) {
    this();
    this.ipAdd = ipAdd;
  }

  public String getIpAdd() {
    return ipAdd;
  }

  public void setIpAdd(String ipAdd) {
    this.ipAdd = ipAdd;
  }
}