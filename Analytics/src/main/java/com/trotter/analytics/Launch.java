package com.trotter.analytics;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.lang.String;
import java.util.Date;

@Entity
public class Launch {

  @Id public Long id;
  @Index public Date date;
  @Index public Boolean newInstall;

  public String ipAddress;
  public String osType;
  public String osVersion;
  public String deviceType;

  public Launch() {
    date = new Date();
  };

  public Launch(String ipAddress, String osType, String osVersion, String deviceType, Boolean newInstall) {
    this();
    this.ipAddress = ipAddress;
    this.osType = osType;
    this.osVersion = osVersion;
    this.deviceType = deviceType;
    this.newInstall = newInstall;
  }

  public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  public String getOsType() {
    return osType;
  }

  public void setOsType(String osType) {
    this.osType = osType;
  }

  public String getOsVersion() {
    return osVersion;
  }

  public void setOsVersion(String osVersion) {
    this.osVersion = osVersion;
  }

  public String getDeviceType() {
    return deviceType;
  }

  public void setDeviceType(String deviceType) {
    this.deviceType = deviceType;
  }

  public Boolean getNewInstall() {
    return newInstall;
  }

  public void setNewInstall(Boolean newInstall) {
    this.newInstall = newInstall;
  }
}