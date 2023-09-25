<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Management</title>
    <style>
        table {
            border-collapse: collapse;
            width: 80%;
            margin: 20px auto;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
<h1>User Management</h1>

<!-- Display User List -->
<h2>User List</h2>
<table>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Surname</th>
        <th>Age</th>
        <th>Actions</th>
    </tr>
    <c:forEach items="${users}" var="user">
        <tr>
            <td>${user.id}</td>
            <td>${user.name}</td>
            <td>${user.surname}</td>
            <td>${user.age}</td>
            <td>
                <form action="/users" method="post">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="id" value="${user.id}">
                    <button type="submit">Delete</button>
                </form>
                <form action="/update-user.jsp" method="post">
                    <input type="hidden" name="id" value="${user.id}">
                    <input type="hidden" name="name" value="${user.name}">
                    <input type="hidden" name="surname" value="${user.surname}">
                    <input type="hidden" name="age" value="${user.age}">
                    <button type="submit">Edit</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<!-- Add User Form -->
<h2>Add User</h2>
<form action="/users" method="post">
    <input type="hidden" name="action" value="add">
    <label for="name">Name:</label>
    <input type="text" name="name" required><br>
    <label for="surname">Surname:</label>
    <input type="text" name="surname" required><br>
    <label for="age">Age:</label>
    <input type="number" name="age" required><br>
    <button type="submit">Add User</button>
</form>
</body>
</html>