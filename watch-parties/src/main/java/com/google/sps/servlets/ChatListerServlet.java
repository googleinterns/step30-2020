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
//import com.google.gson.Gson;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import java.util.Date;
import com.google.sps.data.Chat;
//import java.sql.Timestamp;


@WebServlet("/chatstorage")
public class ChatListerServlet extends HttpServlet {

  public ArrayList<Chat> comments = new ArrayList<>();
  public ArrayList<String> JSONcomments = new ArrayList<>();
  public ArrayList<String> HTMLcomments = new ArrayList<>();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Query query = new Query("Chat").addSort("timestamp", SortDirection.DESCENDING);
    //System.out.println("checkpoint 4");
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    //int i = 0;
    for (Entity entity : results.asIterable()) {
        //long id = entity.getKey().getId();
        //String title = (String) entity.getProperty("text-input");
        //long timestamp = (long) entity.getProperty("timestamp");
        //String email = (String) entity.getProperty("email");
        //String languageCode = (String) entity.getProperty("language");
        //c.setProperty("message", request.getParameter("submitted-comment"));

        String message = (String) entity.getProperty("message");
        String authorID = (String) entity.getProperty("authorID");
        long timestamp = (long) entity.getProperty("timestamp");
        //System.out.println("checkpoint 5");

        //String languageCode = request.getParameter("language").value;
        //System.out.println(languageCode);

        // Do the translation.
        //Translate translate = TranslateOptions.getDefaultInstance().getService();
        //Translation translation = translate.translate(title, Translate.TranslateOption.targetLanguage(DataServlet.languageCode));
        //title = translation.getTranslatedText();

        //Task task = new Task(id, title, timestamp, email, DataServlet.languageCode);
        //tasks.add(task);
        //System.out.println(DataServlet.ncInput); Date
        //i++;
        //if (i == DataServlet.ncInput) {break;}
        Chat ch = new Chat(message, authorID, timestamp);
        JSONcomments.add(ch.toJSON());
        HTMLcomments.add(ch.toHTML());
        comments.add(ch);
        //System.out.println("checkpoint 6");
    }

    //Gson gson = new Gson();

    //response.setContentType("application/json;");
    response.setContentType("text/html;");
    //response.getWriter().println((comments.get(comments.size()-1)).toHTML());

    /**
    for (int i = 0; i < comments.size(); i++) {
        if (!comments.get(i).getWritten()) {
            response.getWriter().println(comments.get(i).toHTML());
            //comments.get(i).setWritten(true);
        }
    } */

    for (int i = comments.size()-1; i >= 0; i--) {
        if (!comments.get(i).getWritten()) {
            response.getWriter().println(comments.get(i).toHTML());
            comments.get(i).setWritten(true);
        }
    }
    //System.out.println("checkpoint 7");
  }
}