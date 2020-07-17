package com.google.sps.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.ArrayList;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.sps.data.Chat;
 
@WebServlet("/chatstorer")
public class ChatReceiverServlet extends HttpServlet {
    String justSettingUp = "lmao";
 
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String comment = request.getParameter("submitted-comment");
        long timestamp = System.currentTimeMillis();
        String authorID = "Tommy"; //placeholder for now

        //From here on we make sure the messages is stored properly
        Entity c = new Entity("Chat");
        c.setProperty("message", comment);
        c.setProperty("authorID", authorID);
        c.setProperty("timestamp", timestamp);

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(c);
        response.setStatus(HttpServletResponse.SC_OK); 
    }
}
