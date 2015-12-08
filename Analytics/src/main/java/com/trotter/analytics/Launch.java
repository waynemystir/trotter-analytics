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
  @Index public Boolean adWordsInstall;

  public String ipAddress;
  public String osType;
  public String osVersion;
  public String deviceType;
  public String iosIdfa;
  public String bundleId;
  public String bundleVersion;
  public String appVersion;
  public Boolean limitAdTracking;

  public Launch() {
    date = new Date();
  };

  public Launch(String ipAddress, String osType, String osVersion, String deviceType, Boolean newInstall, Boolean adWordsInstall,
                  String iosIdfa, String bundleId, String bundleVersion, String appVersion, Boolean limitAdTracking) {
    this();
    this.ipAddress = ipAddress;
    this.osType = osType;
    this.osVersion = osVersion;
    this.deviceType = deviceType;
    this.newInstall = newInstall;
    this.adWordsInstall = adWordsInstall;
    this.iosIdfa = iosIdfa;
    this.bundleId = bundleId;
    this.bundleVersion = bundleVersion;
    this.appVersion = appVersion;
    this.limitAdTracking = limitAdTracking;
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

  public Boolean getAdWordsInstall() {
    return adWordsInstall;
  }

  public void setAdWordsInstall(Boolean adWordsInstall) {
    this.adWordsInstall = adWordsInstall;
  }

  public String getIosIdfa() {
    return iosIdfa;
  }

  public void setIosIdfa(String iosIdfa) {
    this.iosIdfa = iosIdfa;
  }

  public String getBundleId() {
    return bundleId;
  }

  public void setBundleId(String bundleId) {
    this.bundleId = bundleId;
  }

  public String getBundleVersion() {
    return bundleVersion;
  }

  public void setBundleVersion(String bundleVersion) {
    this.bundleVersion = bundleVersion;
  }

  public String getAppVersion() {
    return appVersion;
  }

  public void setAppVersion(String appVersion) {
    this.appVersion = appVersion;
  }

  public Boolean getLimitAdTracking() {
    return limitAdTracking;
  }

  public void setLimitAdTracking(Boolean limitAdTracking) {
    this.limitAdTracking = limitAdTracking;
  }
}