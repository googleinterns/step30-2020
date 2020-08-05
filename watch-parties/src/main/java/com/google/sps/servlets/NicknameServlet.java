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
import javax.servlet.http.HttpServletResponse;

@WebServlet("/nickname")
public class NicknameServlet extends HttpServlet {
  private ArrayList<Member> members = new ArrayList<Member>();
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    UserService userService = UserServiceFactory.getUserService();
    String title = request.getParameter("title");
    String nickname;
    if(!request.getParameter("nickname").equals(null) || !request.getParameter("nickname").trim().equals("")){
        nickname = request.getParameter("nickname");
    }
    else{
        nickname = userService.getCurrentUser().getEmail();
    }
    String creator = request.getParameter("creator");
    if(creator.equals("true"))
        members.add(new Member(userService.getCurrentUser(), true, nickname));
    else
        members.add(new Member(userService.getCurrentUser(), false, nickname));
    response.sendRedirect("/index.html?id="+title);
  }
}