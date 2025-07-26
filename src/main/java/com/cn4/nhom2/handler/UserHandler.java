/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cn4.nhom2.handler;

import com.cn4.nhom2.model.MySQLConnection;
import com.cn4.nhom2.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author User
 */
public class UserHandler implements HttpHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String response = "";

        try {
            switch (method) {
                case "POST" -> {
                    User newUser = objectMapper.readValue(exchange.getRequestBody(), User.class);
                    String query = "INSERT INTO user (username) VALUES (?);";
                    PreparedStatement statement = MySQLConnection.create().prepareStatement(query);
                    statement.setString(1, newUser.getUsername());
                    int rowsAffected = statement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Add user successfully!");
                    }
                }
                case "GET" -> {
                    String query = "SELECT * FROM user;";
                    PreparedStatement statement = MySQLConnection.create().prepareStatement(query);
                    ResultSet resultSet = statement.executeQuery();
                    response = objectMapper.writeValueAsString(User.getUsers(resultSet));
                    MySQLConnection.close();
                }
                case "PUT" -> {
                    // Update
                    User user = objectMapper.readValue(exchange.getRequestBody(), User.class);
                    String query = "UPDATE user SET username = ? WHERE id = ?";
                    PreparedStatement statement = MySQLConnection.create().prepareStatement(query);
                    statement.setString(1, user.getUsername());
                    statement.setInt(2, user.getId());
                    int rowsAffected = statement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Update user successfully!");
                    }

                }

                case "DELETE" -> {
                    // Delete
                    String path = exchange.getRequestURI().getPath();
                    int id = Integer.parseInt(path.split("/")[2]);
                    String query = "DELETE FROM user WHERE id = ?;";
                    PreparedStatement statement = MySQLConnection.create().prepareStatement(query);
                    statement.setInt(1, id);
                    int rowsAffected = statement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Delete user successfully!");
                    }
                }
                default -> {
                    response = "Unsupported method: " + method;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, response.getBytes().length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
