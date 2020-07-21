package com.google.sps.servlets;
import com.google.appengine.api.users.User;
import java.util.Date;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Key;
public class Member {
  private Key id;
  private String nickname;
  private boolean partyLeader;
  private User user;

  public Member(User u, boolean leader, String name){
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      user = u;
      partyLeader = leader;
      nickname = name;
      Key key = KeyFactory.createKey("Member",new Date().toString());
      this.id = key;
      Entity entity = new Entity("Member",key);
      entity.setProperty("user", user);
      entity.setProperty("partyLeader", partyLeader);
      entity.setProperty("nickname", nickname);
      datastore.put(entity);
  }

  public String getNickname(){
      return nickname;
  }
  
  public void makeUserLeader(){
      partyLeader = true;
  }
}
