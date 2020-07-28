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

import com.google.appengine.api.users.User;
import java.util.Date;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Key;
public class Member {
  //unique id of room member
  private Key id;
  //nickname of room member
  private String nickname;
  //true if room member is the leader
  private boolean partyLeader;
  //User object of the room member
  private User user;

  public Member(User user, boolean partyLeader, String nickname){
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      this.user = user;
      this.partyLeader = partyLeader;
      this.nickname = nickname;
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
