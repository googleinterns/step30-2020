package com.google.sps.servlets;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.util.ArrayList;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/nickname")
public class NicknameServlet extends HttpServlet {
  private ArrayList<Member> members = new ArrayList<Member>();
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    UserService userService = UserServiceFactory.getUserService();
    String creator = request.getParameter("creator");
    if(creator.equals("true"))
        members.add(new Member(userService.getCurrentUser(),true,request.getParameter("nickname")));
    else
        members.add(new Member(userService.getCurrentUser(),false,request.getParameter("nickname")));
    response.sendRedirect("/index.html");
  }
}