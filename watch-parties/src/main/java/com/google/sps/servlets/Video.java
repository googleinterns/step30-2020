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
import java.util.Date;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Key;
public class Video {
  //current time in video
  private int position;
  //youtube video id
  private String videoId;
  public Video(String url){
      videoId = url.substring(url.length()-11   );
      System.out.println(videoId);
      this.createVideoEntity();
  }
  //Stores Video object as entity in Datastore
  public void createVideoEntity(){
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Key key = KeyFactory.createKey("Video",new Date().toString());
    Entity entity = new Entity("Video",key);
    entity.setProperty("videoId", videoId);
    datastore.put(entity);
  }
  //Creates Video object from data in Datastore entity
  public Video(Entity entity){
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    videoId = entity.getProperty("videoId").toString();
  }
}
