package com.google.sps.data;
import java.util.Date;

public final class Chat {
 
  private String message;
  private String authorID;
  private long timestamp;
  private boolean writtenAlready = false; // makes sure comments arent written multiple times
  private boolean adminStatus = false;
 
  public Chat(String message, String authorID, long timestamp) {
    this.message = message;
    this.authorID = authorID;
    this.timestamp = timestamp;
  }
 
  public String toJSON() {
    String m = "message: ";
    String aid = "authorID: ";
    String t = "timestamp: ";
    return "{" + m + message + ", " + aid + authorID + ", " + t + timestamp + "}";
  }

  public String toHTML() {
    if (adminStatus) {
        return "<br /><li><b id=\"admin\">" + authorID + ": </b>" + message + "</li><br/>";
    } else {
        return "<br /><li><b>" + authorID + ": </b>" + message + "</li><br/>";
    }
  }
 
  public void setMessage(String message) {
    this.message = message;
  }
 
  public void setAuthorId(String authorID) {
    this.authorID = authorID;
  }
 
  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public void setWritten(boolean writtenAlready) {
    this.writtenAlready = writtenAlready;
  }

  public void setAdminStatus(boolean adminStatus) {
    this.adminStatus = adminStatus;
  }
 
  public String getMessage() {
    return message;
  }
 
  public String getAuthorId() {
    return authorID;
  }
 
  public long getTimestamp() {
    return timestamp;
  }

  public boolean getWritten() {
    return writtenAlready;
  }

  public boolean getAdminStatus() {
    return adminStatus;
  }
}
