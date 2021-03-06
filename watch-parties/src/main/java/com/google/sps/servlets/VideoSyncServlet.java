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
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;

@WebServlet("/sync")
public class VideoSyncServlet extends HttpServlet {
    // See onStateChange docs for video state values:
    // https://developers.google.com/youtube/iframe_api_reference#Events

    // When the servlet starts, video status is at 2 and video time is at 0 to prevent a non host user from changing player state
    int videoStatus = 2;
    int videoTime = 0;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException { 
        response.setContentType("application/json");
        response.getWriter().println("{\"status\": \"" + videoStatus + "\", \"timeStamp\": \"" + videoTime + "\"}");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean hostStatus = Boolean.parseBoolean(request.getParameter("host"));
        if (hostStatus) {
            videoStatus = Integer.parseInt(request.getParameter("status"));
            videoTime = Math.round(Float.parseFloat(request.getParameter("time")));
        }
    }
}