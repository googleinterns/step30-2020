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

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.mockito.Mockito.*;

/** Class to test VideoSyncServlet. */
@RunWith(JUnit4.class)
public final class VideoSyncServletTest {
    private HttpServletRequest HostMockRequest;       
    private HttpServletResponse HostMockResponse; 
    private HttpServletRequest UserMockRequest;       
    private HttpServletResponse UserMockResponse; 

    @Before
    public void setUp() {
        // Create mock and request with host privileges
        HostMockRequest = mock(HttpServletRequest.class);      
        when(HostMockRequest.getParameter("host")).thenReturn("true"); 
        HostMockResponse = mock(HttpServletResponse.class);   

        // Create mock and request with regular privileges
        UserMockRequest = mock(HttpServletRequest.class);       
        UserMockResponse = mock(HttpServletResponse.class);   
    }

    @Test
    public void testDoGet() throws IOException {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(UserMockResponse.getWriter()).thenReturn(writer);

        VideoSyncServlet servlet = new VideoSyncServlet();
        servlet.videoStatus = 200;
        servlet.doGet(UserMockRequest, UserMockResponse);

        writer.flush();
        Assert.assertTrue(stringWriter.toString().contains("200"));
    }

    @Test
    public void testDoPostHostTrue() throws IOException {
        when(HostMockRequest.getParameter("status")).thenReturn("100");
        when(HostMockRequest.getParameter("time")).thenReturn("0");

        VideoSyncServlet servlet = new VideoSyncServlet();
        servlet.doPost(HostMockRequest, HostMockResponse);
        Assert.assertEquals(100, servlet.videoStatus); 
    }

    @Test
    public void testDoPostHostFalse() throws IOException {
        when(UserMockRequest.getParameter("status")).thenReturn("100");
        when(UserMockRequest.getParameter("time")).thenReturn("0");

        VideoSyncServlet servlet = new VideoSyncServlet();
        servlet.doPost(UserMockRequest, UserMockResponse);
        Assert.assertEquals(0, servlet.videoStatus); 
    }
}
