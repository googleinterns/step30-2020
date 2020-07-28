// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.data;

public final class Chat {
 
  private final String message; // The submitted comment
  private final String authorID; // Where the username goes
  private final long timestamp; //used for ordering comments
  private boolean writtenAlready = false; // makes sure comments arent written multiple times
  private boolean adminStatus = false; // Was this written by an admin
 
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
        return "<br /><li><b id=\"normalComment\">" + authorID + ": </b>" + message + "</li><br/>";
    }
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
