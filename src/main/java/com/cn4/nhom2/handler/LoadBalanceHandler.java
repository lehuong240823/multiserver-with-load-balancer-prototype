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
public class LoadBalanceHandler implements HttpHandler {

    private String TAG = "LoadBalanceHandler";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private ObservableList<ServerNode> nodes = FXCollections.observableArrayList();

    public LoadBalanceHandler(ObservableList<ServerNode> nodes) {
        this.nodes = nodes;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String response = "";

        try {
            switch (method) {
                case "POST" -> {
                    ServerNode node = objectMapper.readValue(exchange.getRequestBody(), ServerNode.class);
                    nodes.add(node);
                }
                case "GET" -> {

                    response = objectMapper.writeValueAsString(nodes);
                }
                case "PUT" -> {
                    // Update

                }
                case "DELETE" -> {
                    String path = exchange.getRequestURI().getPath();
                    String inetAddress = path.split("/")[2];
                    String address = inetAddress.split(":")[0];
                    String port = inetAddress.split(":")[1];
                    nodes.removeIf((e) -> address.equals(e.getAddress()) && port.equals(String.valueOf(e.getPort())));
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
