<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.*, model.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
     ArrayList<MemberDTO> al = 
     (ArrayList<MemberDTO>)request.getAttribute("list");
     for(MemberDTO dto : al) { // enhanced ioop statement
%>
	<td><%= dto.getEmail() %></td>
    <td><%= dto.getPw() %></td>
    <td><%= dto.getPhone() %></td>
<%
     }
 %>
<form action="member-list.do" method="get">
                <div class="form-group">
                  <input id="email_modal" name="email" type="text" placeholder="email">
                </div>
                <div class="form-group">
                  <input id="password_modal" name="pw" type="password" placeholder="password">
                </div>
                <p class="text-center">
                  <button class="btn btn-template-outlined"><i class="fa fa-sign-in"></i> Log in</button>
                </p>
</form>
<a href="table.jsp">table보기</a>
<br>
			  <h3>201512034 한정훈</h3>
              <h4 class="text-muted"> 
			  <%= (String) request.getAttribute("attr") %>
              <%= request.getParameter("email") %>, <%= request.getParameter("pw") %>
              </h4>	

</body>
</html>