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
  private HttpServletRequest mockRequest;       
  private HttpServletResponse mockResponse; 

  @Before
  public void setUp() {
    mockRequest = mock(HttpServletRequest.class);       
    mockResponse = mock(HttpServletResponse.class);   
  }

  @Test
  public void testDoGet() throws IOException {
    StringWriter stringWriter = new StringWriter();
    PrintWriter writer = new PrintWriter(stringWriter);
    when(mockResponse.getWriter()).thenReturn(writer);

    VideoSyncServlet servlet = new VideoSyncServlet();
    servlet.videoStatus = 200;
    servlet.doGet(mockRequest, mockResponse);

    writer.flush();
    Assert.assertTrue(stringWriter.toString().contains("200"));
  }

  @Test
  public void testDoPost() throws IOException {
    when(mockRequest.getParameter("status")).thenReturn("100");

    VideoSyncServlet servlet = new VideoSyncServlet();
    servlet.doPost(mockRequest, mockResponse);
    Assert.assertEquals(100, servlet.videoStatus); 
  }
}
