package com.example.javaeeapp.servlet;

import com.example.javaeeapp.user.User;
import com.example.javaeeapp.user.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class UserServlet extends HttpServlet {

    private static final String DRIVER = "org.postgresql.Driver";
//    private static final String URL = "jdbc:postgresql://localhost:5432/electronic_queue_db";
//    private static final String USER = "user";
//    private static final String PASSWORD = "password";

    private static final String URL = "jdbc:postgresql://192.168.1.22:5432/java_ee_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "iwb18031991";

    Connection connection;
    UserRepository userRepository = UserRepository.getInstance();

    @Override
    public void init() throws ServletException {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = userRepository.getAllUsers(connection);

        req.setAttribute("users", users);
        req.getRequestDispatcher("users-list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if (action.equals("delete")) {
            doDelete(req, resp);
        } else if (action.equals("update")) {
            doPut(req, resp);
        } else if (action.equals("add")) {
            userRepository.addUser(connection, req);
            resp.sendRedirect("/users");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));

        userRepository.deleteUserById(connection, id);

        resp.sendRedirect("/users");
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userRepository.alterUser(connection, req);

        resp.sendRedirect("/users");
    }

    @Override
    public void destroy() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}