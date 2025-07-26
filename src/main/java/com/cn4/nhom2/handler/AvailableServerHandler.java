/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cn4.nhom2.handler;

import com.cn4.nhom2.model.LoadBalancer;
import com.cn4.nhom2.entity.ServerNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author User
 */
public class AvailableServerHandler implements HttpHandler {

    private String TAG = "AvailableServerHandler";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private ObservableList<ServerNode> nodes = FXCollections.observableArrayList();

    public AvailableServerHandler(ObservableList<ServerNode> nodes) {
        this.nodes = nodes;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String response = "";

        try {
            switch (method) {
                case "POST" -> {

                }
                case "GET" -> {
               
//response = "oke";
                    response = objectMapper.writeValueAsString(LoadBalancer.getNextAvailableServer());
                    //System.out.println(LoadBalancer.currentServerIndex);

                }
                case "PUT" -> {
                    // Update

                }
                case "DELETE" -> {

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
