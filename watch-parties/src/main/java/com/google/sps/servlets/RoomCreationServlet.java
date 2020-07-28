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

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.util.ArrayList;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import com.google.appengine.api.datastore.KeyFactory;
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
    Room room = new Room(title, privateRoom);
    String id = KeyFactory.createKeyString(room.getId(), "Room", title);
    UserService userService = UserServiceFactory.getUserService();
    if (userService.isUserLoggedIn()) {
      String userNickname= userService.getCurrentUser().getNickname();
      response.getWriter().println(nicknameForm(userNickname, id));
    } else {
      String urlToRedirectToAfterUserLogsIn = "/index.html";
      String loginUrl = userService.createLoginURL(urlToRedirectToAfterUserLogsIn);
      response.sendRedirect(loginUrl);
    }
  }
  public static String nicknameForm(String userNickname, String id){
      return "<p>Set your nickname here:</p><form method=\"POST\" action=\"/nickname\"><input type=\"hidden\" name=\"creator\" value=\"true\"/><input type=\"hidden\" name=\"title\" value=\""+id+"\"/><input name=\"nickname\" value=\"" + userNickname + "\" /><br/><button>Submit</button></form>";
  }
}
