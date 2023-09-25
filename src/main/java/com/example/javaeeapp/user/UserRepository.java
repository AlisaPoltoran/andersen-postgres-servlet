package com.example.javaeeapp.user;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UserRepository {

    private static UserRepository userRepository;

    private static final String SELECT_ALL = "SELECT * from users";
    private static final String DELETE_BY_ID = "DELETE from users WHERE id=?";
    private static final String ADD = "INSERT into users (name, surname, age) values (?, ?, ?)";
    private static final String SELECT_BY_ID = "SELECT * from users WHERE id=?";
    private static final String UPDATE = "UPDATE users SET name=?, surname =?, age=? WHERE id=?";

    private UserRepository() {
    }

    public static UserRepository getInstance() {
        if (userRepository == null) {
            userRepository = new UserRepository();
        }
        return userRepository;
    }

    public List<User> getAllUsers(Connection connection) {
        List<User> users = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                int age = resultSet.getInt("age");
                users.add(new User(id, name, surname, age));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users.stream()
                .sorted(Comparator.comparing(User::getId)).collect(Collectors.toList());
    }

    public void deleteUserById(Connection connection, long id) {
        if (findUserById(connection, id)) {
            try (PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID)) {
                statement.setLong(1, id);
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void addUser(Connection connection, HttpServletRequest req) {
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        int age = Integer.parseInt(req.getParameter("age"));

        try (PreparedStatement statement = connection.prepareStatement(ADD)) {
            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setInt(3, age);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void alterUser(Connection connection, HttpServletRequest req) {
        long id = Long.parseLong(req.getParameter("id"));
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        int age = Integer.parseInt(req.getParameter("age"));

        if (findUserById(connection, id)) {
            try (PreparedStatement updateStatement = connection.prepareStatement(UPDATE)) {
                updateStatement.setString(1, name);
                updateStatement.setString(2, surname);
                updateStatement.setInt(3, age);
                updateStatement.setLong(4, id);
                updateStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean findUserById(Connection connection, long id) {
        boolean result = false;
        try (PreparedStatement selectStatement = connection.prepareStatement(SELECT_BY_ID)) {
            selectStatement.setLong(1, id);
            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}