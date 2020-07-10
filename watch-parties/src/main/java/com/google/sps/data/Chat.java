package com.google.sps.data;
import java.util.Date;

/** An item on a todo list. */
public final class Chat {

  private String message;
  private int authorID;
  private Date timestamp;

  public Chat(String message, int authorID, Date timestamp) {
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

  public void setAuthorId(int authorID) {
    this.authorID = authorID;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public String getMessage() {
    return message;
  }

  public int getAuthorId() {
    return authorID;
  }

  public Date getTimestamp() {
    return timestamp;
  }
}