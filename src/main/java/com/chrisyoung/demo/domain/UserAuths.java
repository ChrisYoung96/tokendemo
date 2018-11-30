package com.chrisyoung.demo.domain;


public class UserAuths {

  private long uaId;
  private String uId;
  private String identify;
  private String credential;
  private String role;


  public long getUaId() {
    return uaId;
  }

  public void setUaId(long uaId) {
    this.uaId = uaId;
  }


  public String getUId() {
    return uId;
  }

  public void setUId(String uId) {
    this.uId = uId;
  }

  public String getIdentify() {
    return identify;
  }

  public void setIdentify(String identify) {
    this.identify = identify;
  }


  public String getCredential() {
    return credential;
  }

  public void setCredential(String credential) {
    this.credential = credential;
  }


  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

}
