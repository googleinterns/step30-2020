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
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html");
    UserService userService = UserServiceFactory.getUserService();
    if (userService.isUserLoggedIn()) {
      String userNickname= userService.getCurrentUser().getNickname();
      response.getWriter().println("<p>Set your nickname here:</p>");
      response.getWriter().println("<form method=\"POST\" action=\"/nickname\">");
      response.getWriter().println("<input type=\"hidden\" name=\"creator\" value=\"false\"/>");
      response.getWriter().println("<input name=\"nickname\" value=\"" + userNickname + "\" />");
      response.getWriter().println("<br/>");
      response.getWriter().println("<button>Submit</button>");
      response.getWriter().println("</form>");
    } else {
      String urlToRedirectToAfterUserLogsIn = "/login";
      String loginUrl = userService.createLoginURL(urlToRedirectToAfterUserLogsIn);

      response.getWriter().println("<p>Hello.</p>");
      response.getWriter().println("<p>Login <a href=\"" + loginUrl + "\">here</a>.</p>");
    }
  }
}