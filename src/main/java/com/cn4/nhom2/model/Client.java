/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cn4.nhom2.model;

import com.cn4.nhom2.entity.ServerNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.*;
import java.net.http.*;
import java.net.http.HttpClient.*;
import java.time.Duration;

/**
 *
 * @author User
 */
public class Client {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private HttpClient client;

    public Client() {
    }

    public void createClient() {
        client = HttpClient.newBuilder()
                .version(Version.HTTP_1_1)
                .followRedirects(Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(20))
                .proxy(ProxySelector.getDefault())
                .build();
    }

    public ServerNode fetchAvailableServer() {
        try {
            createClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(LoadBalancer.getUrlEndpoint(LoadBalancer.EP_AVAILABLE)))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ServerNode node = objectMapper.readValue(response.body(), ServerNode.class);

            System.out.println("Read Response: " + response.body());
            return node;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getUrlEndpoint(ServerNode node, String endpoint) {
        return String.format("http://%s:%d%s", node.getAddress(), node.getPort(), endpoint);
    }

    public HttpResponse<String> post(String url, String json) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Create Response: " + response.body());
        return response;
    }

    public HttpResponse<String> put(String url, String json) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    public HttpResponse<String> get(String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    public HttpResponse<String> delete(String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }
}
