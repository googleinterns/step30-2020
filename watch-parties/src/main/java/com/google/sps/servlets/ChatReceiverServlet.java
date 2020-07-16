package com.google.sps.servlets;

//mport java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
//import java.sql.Timestamp;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.sps.data.Chat;
 
@WebServlet("/chatstorer")
public class ChatReceiverServlet extends HttpServlet {
    String justSettingUp = "lmao";
    //ArrayList<Chat> comments = new ArrayList<>();
 
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String comment = request.getParameter("submitted-comment");
        long timestamp = System.currentTimeMillis();
        long authorID = 3;
        //System.out.println(comment + "tralalalalalalalalalala");
        //System.out.println("checkpoint 1");
 
        //Chat ch = new Chat(comment, authorID, new Timestamp(timestamp.getTime()));
        //comments.add(ch);

        Entity c = new Entity("Chat");
        c.setProperty("message", comment);
        c.setProperty("authorID", authorID);
        c.setProperty("timestamp", timestamp);
        //System.out.println("checkpoint 2");
 
        //response.setContentType("application/json;");
        ///for (Chat thisComment : comments) {
        //    response.getWriter().println(thisComment.toJSON());
        //}

        //Entity taskEntity = new Entity("Task"); 
        //taskEntity.setProperty("text-input", text);
        //taskEntity.setProperty("timestamp", timestamp);
        //taskEntity.setProperty("email", userEmail);
        //taskEntity.setProperty("language", languageCode);

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        //PreparedQuery results = datastore.prepare(query);
        datastore.put(c);
        //System.out.println("checkpoint 3");
        response.sendRedirect("/index.html");
        //System.out.println(justSettingUp + "");
    }
}
