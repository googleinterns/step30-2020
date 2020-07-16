package com.google.sps.data;
import java.util.Date;
//import java.sql.Timestamp;
 
/** An item on a todo list. */
public final class Chat {
 
  private String message;
  private long authorID;
  private long timestamp;
  private boolean writtenAlready = false;
 
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

  public String toHTML() {
    return "<br /><li><b>" + authorID + ": </b>" + message + "</li><br/>";
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

  public void setWritten(boolean writtenAlready) {
    this.writtenAlready = writtenAlready;
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

  public boolean getWritten() {
    return writtenAlready;
  }
}
