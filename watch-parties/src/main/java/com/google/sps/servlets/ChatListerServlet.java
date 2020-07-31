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

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.sps.data.Chat;


@WebServlet("/chatstorage")
public class ChatListerServlet extends HttpServlet {
  DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Bring comments back from datastore
    Query query = new Query("Chat").addSort("timestamp", SortDirection.DESCENDING); 
    PreparedQuery results = datastore.prepare(query);
    List<Entity> entities = results.asList(FetchOptions.Builder.withLimit(50));

    // convert to actual chat object, send the 50 recent comments to the fron end
    response.setContentType("text/html;");
    for (int i = entities.size()-1; i>= 0; i--) {
        Entity entity = entities.get(i);

        String message = (String) entity.getProperty("message");
        String authorID = (String) entity.getProperty("authorID");
        long timestamp = (long) entity.getProperty("timestamp");
        
        Chat chatMessage = new Chat(message, authorID, timestamp);
        response.getWriter().println(chatMessage.toHTML());
    }
  }
}