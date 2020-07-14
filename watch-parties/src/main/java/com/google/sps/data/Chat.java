package com.google.sps.data;
import java.util.Date;
//import java.sql.Timestamp;
 
/** An item on a todo list. */
public final class Chat {
 
  private String message;
  private long authorID;
  private long timestamp;
 
  public Chat(String message, long authorID, long timestamp) {
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
 
  public void setMessage(String message) {
    this.message = message;
  }
 
  public void setAuthorId(long authorID) {
    this.authorID = authorID;
  }
 
  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }
 
  public String getMessage() {
    return message;
  }
 
  public long getAuthorId() {
    return authorID;
  }
 
  public long getTimestamp() {
    return timestamp;
  }
}
