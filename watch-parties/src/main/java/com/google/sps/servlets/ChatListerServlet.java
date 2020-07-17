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


@WebServlet("/chatstorage")
public class ChatListerServlet extends HttpServlet {

  public ArrayList<Chat> comments = new ArrayList<>();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // retrieve from datastore
    Query query = new Query("Chat").addSort("timestamp", SortDirection.DESCENDING);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    // convert to actual chat object, add to list of comments
    for (Entity entity : results.asIterable()) {
        String message = (String) entity.getProperty("message");
        String authorID = (String) entity.getProperty("authorID");
        long timestamp = (long) entity.getProperty("timestamp");

        Chat ch = new Chat(message, authorID, timestamp);
        comments.add(ch);
    }

    // preparing to write comments into file
    response.setContentType("text/html;");

    // writing comments into file
    for (int i = comments.size()-1; i >= 0; i--) {
        if (!comments.get(i).getWritten()) {
            response.getWriter().println(comments.get(i).toHTML());
            comments.get(i).setWritten(true);
        }
    }
  }
}