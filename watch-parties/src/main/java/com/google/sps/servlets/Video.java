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
public class Video {
  private int position;
  private String videoId;
  public Video(String url){
      videoId = url.substring(url.length()-11   );
      System.out.println(videoId);
      this.createVideoEntity();
  }
  public void createVideoEntity(){
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Key key = KeyFactory.createKey("Video",new Date().toString());
    Entity entity = new Entity("Video",key);
    entity.setProperty("videoId", videoId);
    datastore.put(entity);
  }
  public Video(Entity entity){
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    videoId = entity.getProperty("videoId").toString();
  }
}
