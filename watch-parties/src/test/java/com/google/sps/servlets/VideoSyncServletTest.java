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
    private HttpServletRequest hostMockRequest;       
    private HttpServletRequest userMockRequest;       
    private HttpServletResponse mockResponse; 

    @Before
    public void setUp() {
        // Create a mock request with host privileges
        hostMockRequest = mock(HttpServletRequest.class);      
        when(hostMockRequest.getParameter("host")).thenReturn("true"); 
        userMockRequest = mock(HttpServletRequest.class);       
        mockResponse = mock(HttpServletResponse.class);   
    }

    @Test
    public void testDoGet() throws IOException {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(mockResponse.getWriter()).thenReturn(writer);

        VideoSyncServlet servlet = new VideoSyncServlet();
        servlet.videoStatus = 200;
        servlet.doGet(userMockRequest, mockResponse);

        writer.flush();
        Assert.assertTrue(stringWriter.toString().contains("200"));
    }

    @Test
    public void testDoPostOnHostUser() throws IOException {
        when(hostMockRequest.getParameter("status")).thenReturn("100");
        when(hostMockRequest.getParameter("time")).thenReturn("10");

        VideoSyncServlet servlet = new VideoSyncServlet();
        servlet.doPost(hostMockRequest, mockResponse);
        
        Assert.assertEquals(100, servlet.videoStatus); 
        Assert.assertEquals(10, servlet.videoTime); 
    }

    @Test
    public void testDoPostOnGeneralUser() throws IOException {
        when(userMockRequest.getParameter("status")).thenReturn("100");
        when(userMockRequest.getParameter("time")).thenReturn("10");

        VideoSyncServlet servlet = new VideoSyncServlet();
        servlet.doPost(userMockRequest, mockResponse);

        Assert.assertEquals(2, servlet.videoStatus); 
        Assert.assertEquals(0, servlet.videoTime); 
    }

    @Test 
    public void testVideoTimeStamp() throws IOException {
        VideoSyncServlet servlet = new VideoSyncServlet();
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        
        // Check that get requests are in sync with the time stamp that is done with post
        for(int testTime = 0; testTime < 10; testTime++) {
            String testTimeStr = Integer.toString(testTime);
            when(hostMockRequest.getParameter("status")).thenReturn("1");
            when(hostMockRequest.getParameter("time")).thenReturn(testTimeStr);
            servlet.doPost(hostMockRequest, mockResponse);

            when(mockResponse.getWriter()).thenReturn(writer);
            servlet.doGet(userMockRequest, mockResponse);
            writer.flush();

            Assert.assertTrue(stringWriter.toString().contains(testTimeStr));
        }
    }
}
