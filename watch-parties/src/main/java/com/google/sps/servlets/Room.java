package com.google.sps.servlets;
import java.util.ArrayList;
import java.lang.Integer;
import java.util.Date;
import java.io.PrintWriter;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Key;
import java.util.PriorityQueue;
public class Room {
  private Key id;
  private String title;
  private long partyLeaderID;
  private String videoURL; 
  private ArrayList<Integer> currentUserIDs;
  private boolean privateRoom;
  //private int currentVideo;//index of current video in queue
  private boolean paused;
  private long currentPosition;
  private ArrayList<String> queuedVideos = new ArrayList<String>();
  public Room(String title, boolean privateRoom){
    this.title = title;
    this.privateRoom = privateRoom;
    this.createRoomEntity();
  }
  public void addVideoToQueue(String url){
    queuedVideos.add(url);
  }
  public Key getId(){
      return id;
  }
  public String getTitle(){
      return title;
  }
  public String toString(){
      return title;
  }
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
  public Room(Entity entity){
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    id = entity.getKey();
    title = entity.getProperty("title").toString();
    privateRoom = (boolean)entity.getProperty("privateRoom");
    paused = (boolean)entity.getProperty("paused");
    currentPosition = (long)entity.getProperty("currentPosition");
    partyLeaderID = (long)entity.getProperty("partyLeaderID");
    videoURL = "";
    if(entity.getProperty("videoURL")!=null)
      videoURL=entity.getProperty("videoURL").toString();
  }
}

