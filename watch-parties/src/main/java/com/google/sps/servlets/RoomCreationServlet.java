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
import com.google.gson.Gson;
import java.util.ArrayList;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/create-room")
public class RoomCreationServlet extends HttpServlet {
  private ArrayList<Room> watchRooms = new ArrayList<Room>();
  private ArrayList<Member> members = new ArrayList<Member>();
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html; charset=UTF-8");
    String title = request.getParameter("setTitle");
    System.out.println(title);
    boolean privateRoom;
    if(request.getParameter("privateRoom") == "on")
      privateRoom = true;
    else privateRoom = false;
    watchRooms.add(new Room(title, privateRoom));
    UserService userService = UserServiceFactory.getUserService();
    if (userService.isUserLoggedIn()) {
      String userNickname= userService.getCurrentUser().getNickname();
      response.getWriter().println("<p>Set your nickname here:</p>");
      response.getWriter().println("<form method=\"POST\" action=\"/nickname\">");
      response.getWriter().println("<input type=\"hidden\" name=\"creator\" value=\"true\"/>");
      response.getWriter().println("<input name=\"nickname\" value=\"" + userNickname + "\" />");
      response.getWriter().println("<br/>");
      response.getWriter().println("<button>Submit</button>");
      response.getWriter().println("</form>");
    } else {
      String urlToRedirectToAfterUserLogsIn = "/index.html";
      String loginUrl = userService.createLoginURL(urlToRedirectToAfterUserLogsIn);
      response.sendRedirect(loginUrl);
    }
  }
}
