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

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.sps.data.Chat;
 
@WebServlet("/chatstorer")
public class ChatReceiverServlet extends HttpServlet {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
 
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String comment = request.getParameter("submitted-comment");
        long timestamp = System.currentTimeMillis();
        String authorID = "User"; //placeholder for now

        // Add Author ID stuff here

        // From here on we make sure the message is stored properly
        Entity chatEntity = new Entity("Chat");
        chatEntity.setProperty("message", comment);
        chatEntity.setProperty("authorID", authorID);
        chatEntity.setProperty("timestamp", timestamp);

        // Store chat message
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(chatEntity);
        response.setStatus(HttpServletResponse.SC_OK); 
    }    
}