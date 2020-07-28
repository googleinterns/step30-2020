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
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import java.util.Date;
import com.google.sps.data.Chat;
import java.util.LinkedList; 
import java.util.Stack; 


@WebServlet("/chatstorage")
public class ChatListerServlet extends HttpServlet {

  public ArrayList<Chat> comments = new ArrayList<>();
  public Stack<Chat> stackComments = new Stack<>();
  DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Bring comments back from datastore
    Query query = new Query("Chat").addSort("timestamp", SortDirection.ASCENDING); 
    PreparedQuery results = datastore.prepare(query);

    // count how many comments there are
    int count = 0;
    for (Entity entity : results.asIterable()) {
        count++;
    }
    int startIndex = calculateStartIndex(count);

    // convert to actual chat object, send the 50 recent comments to the fron end
    int i = 0;
    response.setContentType("text/html;");
    for (Entity entity : results.asIterable()) {
        String message = (String) entity.getProperty("message");
        String authorID = (String) entity.getProperty("authorID");
        long timestamp = (long) entity.getProperty("timestamp");

        Chat chatMessage = new Chat(message, authorID, timestamp);
        if (i >= startIndex) {
            response.getWriter().println(chatMessage.toHTML());
        }
        i++;
    }
  }

  // if there are over fifty comments, calculate where to start so that only fifty load
  public int calculateStartIndex(int input) {
      if (input <= 50) {
          return input;
      }
      return input-50; 
  }
}