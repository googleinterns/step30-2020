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

package com.google.sps.servlets;

import java.util.ArrayList;
import java.lang.Integer;
import java.util.Date;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Key;

public class Room {
  //unique id of the room
  private Key id;
  //title of room
  private String title;
  //user id of partyLeader
  private long partyLeaderID;
  //id of current youtube video being played
  private String videoURL;
  //IDs of all users in the room.
  private ArrayList<Integer> currentUserIDs;
  //true if room is private
  private boolean privateRoom;
  //true if video is paused
  private boolean paused;
  //current time in video
  private long currentPosition;
  public Room(String title, boolean privateRoom){
    this.title = title;
    this.privateRoom = privateRoom;
    this.createRoomEntity();
  }
  public Key getId(){
    return id;
  }
  //Stores Room object as an entity in Datastore
  public void createRoomEntity(){
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Key key = KeyFactory.createKey("Room",new Date().toString());
    this.id = key;
    Entity entity = new Entity("Room",key);
    entity.setProperty("title", title);
    entity.setProperty("users", currentUserIDs);
    entity.setProperty("privateRoom", privateRoom);
    entity.setProperty("paused", paused);
    entity.setProperty("currentPosition", currentPosition);
    entity.setProperty("partyLeaderID", partyLeaderID);
    entity.setProperty("videoURL", videoURL);
    datastore.put(entity);
  }
  //Creates Room object from data in Datastore entity
  public Room(Entity entity){
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    id = entity.getKey();
    title = entity.getProperty("title").toString();
    privateRoom = (boolean)entity.getProperty("privateRoom");
    paused = (boolean)entity.getProperty("paused");
    currentPosition = (long)entity.getProperty("currentPosition");
    partyLeaderID = (long)entity.getProperty("partyLeaderID");
    videoURL = "";
    if(entity.getProperty("videoURL")!=null){
      videoURL=entity.getProperty("videoURL").toString();
    }
  }
}

