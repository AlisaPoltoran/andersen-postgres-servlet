<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit User</title>
</head>
<body>
<h1>Edit User</h1>

<%
    String userIdStr = request.getParameter("id");
    String userName = request.getParameter("name");
    String userSurname = request.getParameter("surname");
    String userAge = request.getParameter("age");
%>

<form action="/users" method="post">
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="id" value="<%= userIdStr %>">

    <label for="name">Name:</label>
    <input type="text" name="name" value="<%= userName %>" required><br>

    <label for="surname">Surname:</label>
    <input type="text" name="surname" value="<%= userSurname %>" required><br>

    <label for="age">Age:</label>
    <input type="number" name="age" value="<%= userAge %>" required><br>

    <button type="submit">Update User</button>
</form>

<p><a href="/users-list.jsp">Back to User List</a></p>
</body>
</html>