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
//import atg.repository.QueryOptions;
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
    Query query = new Query("Chat").addSort("timestamp", SortDirection.DESCENDING); 
    PreparedQuery results = datastore.prepare(query);

    // convert to actual chat object, add to list of comments
    for (Entity entity : results.asIterable()) {
        String message = (String) entity.getProperty("message");
        String authorID = (String) entity.getProperty("authorID");
        long timestamp = (long) entity.getProperty("timestamp");

        //comments.add(new Chat(message, authorID, timestamp));
        stackAdd(new Chat(message, authorID, timestamp));
    }

    // preparing to write comments into file
    response.setContentType("text/html;");

    // writing comments into file while iterating through stack
    for (int i = stackComments.size()-1; i >= 0; i--) {
        if (!stackComments.peek().getWritten()) {
            response.getWriter().println(stackComments.peek().toHTML());
            stackComments.peek().setWritten(true);
            stackComments.pop();
        }
    }
  }

  // ensures stack only stays a certain size
  public void stackAdd(Chat input) {
      stackComments.push(input);
      if (stackComments.size() > 50) {
          stackComments.pop();
      }
  }
}