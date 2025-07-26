/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cn4.nhom2.model;

import com.cn4.nhom2.entity.ServerNode;
import com.cn4.nhom2.handler.HealthCheck;
import com.cn4.nhom2.handler.UserHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class Server {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private String TAG = "Server";
    private int TIME_OUT = 5;
    private int TIME_DELAY = 0;
    public static String EP_HEALTH = "/health";
    public static String EP_USER = "/user";
    public static int DEFAULT_PORT = 8000;
    private HttpServer httpServer;
    private HttpClient client;
    private int port;
    private InetAddress address;

    public Server(int instance) {
        try {
            port = instance + DEFAULT_PORT;
            address = InetAddress.getLocalHost();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start(InetAddress address, int port) {
        try {
            InetSocketAddress socketAddress = new InetSocketAddress(address, port);
            httpServer = HttpServer.create(socketAddress, 0);
            httpServer.createContext(EP_HEALTH, new HealthCheck());
            httpServer.createContext(EP_USER, new UserHandler());
            httpServer.setExecutor(null);
            httpServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        httpServer.stop(TIME_DELAY);
    }

    public void createClient() {
        client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(TIME_OUT))
                .proxy(ProxySelector.getDefault())
                .build();
    }

    public int postNode(String url, ServerNode node) {
        try {
            createClient();
            String json = objectMapper.writeValueAsString(node);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode();
        } catch (ConnectException e) {
            JOptionPane.showMessageDialog(null, "Can't connect with Load Balance Server", e.getClass().getSimpleName(), JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int deleteNode(String url, ServerNode node) {
        try {
            createClient();
            String delURL = String.format("%s/%s:%d", url, node.getAddress(), node.getPort());
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(delURL))
                    .header("Content-Type", "application/json")
                    .DELETE()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode();
        } catch (ConnectException e) {
            JOptionPane.showMessageDialog(null, "Can't connect with Load Balance Server", e.getClass().getSimpleName(), JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }
}
