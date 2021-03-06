<%--
  Copyright 2017 Google Inc.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
--%>
<!DOCTYPE html>
<html>
<head>
  <title>ForgotPassword</title>
  <link rel="stylesheet" href="/css/main.css">
</head>
<body>

  <%@include file= "navbar.jsp"%>

  <div id="container">
    <h1>Forgot Password</h1>

    <% if(request.getAttribute("error") != null){ %>
        <h2 style="color:red"><%= request.getAttribute("error") %></h2>
    <% } %>

     <% if(request.getAttribute("success") != null){ %>
        <h2 style="color:blue"><%= request.getAttribute("success") %></h2>
     <% } %>

    <form action="/forgotPassword" method="POST">
      <label for="email">Please enter email: </label>
      <br/>
      <input type="email" name="email" id="email">
      <br/><br/>
      <button type="recoverPassword">Recover Password!</button>
    </form>
  </div>
</body>
</html>
