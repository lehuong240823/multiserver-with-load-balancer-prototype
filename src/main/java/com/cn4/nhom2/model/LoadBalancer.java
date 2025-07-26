/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cn4.nhom2.model;

import com.cn4.nhom2.entity.ServerNode;
import com.cn4.nhom2.handler.AvailableServerHandler;
import com.cn4.nhom2.handler.HealthCheck;
import com.cn4.nhom2.handler.LoadBalanceHandler;
import com.sun.net.httpserver.HttpServer;
import java.net.*;
import javafx.collections.*;

/**
 *
 * @author User
 */
public class LoadBalancer {

    public static int TIME_OUT = 1000;
    public static String ADDRESS = "192.168.137.1"; //Default
    public static int PORT = 9000;
    public static String EP_BALANCER = "/load-balancer";
    public static String EP_AVAILABLE = "/available";
    public static int currentServerIndex = 0;
    private static final ObservableList<ServerNode> serverNodes = FXCollections.observableArrayList();

    public LoadBalancer() {
        try {
            InetSocketAddress socketAddress = new InetSocketAddress(InetAddress.getLocalHost(), PORT);
            HttpServer httpServer = HttpServer.create(socketAddress, 0);
            httpServer.createContext(Server.EP_HEALTH, new HealthCheck());
            httpServer.createContext(EP_BALANCER, new LoadBalanceHandler(serverNodes));
            httpServer.createContext(EP_AVAILABLE, new AvailableServerHandler(serverNodes));
            httpServer.setExecutor(null);

            httpServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getUrlEndpoint(String endpoint) throws Exception {
        return String.format("http://%s:%d%s", getBalancerAddress(), PORT, endpoint);
    }

    public static String getBalancerAddress() throws Exception {
        String url = String.format("http://%s:%d", ADDRESS, PORT);
        if (isServerAvailable(url)) {
            return ADDRESS;
        } else {
            return InetAddress.getLocalHost().getHostAddress();
        }
    }

    public ObservableList<ServerNode> getServerNodes() {
        return serverNodes;
    }

    public static boolean isServerAvailable(String serverUrl) {
        try {
            URL url = new URL(String.format("%s%s", serverUrl, Server.EP_HEALTH));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(TIME_OUT);
            connection.setReadTimeout(TIME_OUT);
            return connection.getResponseCode() == 200;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ServerNode getNextAvailableServer() {
        while (true) {
            currentServerIndex = (currentServerIndex + 1) % serverNodes.size();
            ServerNode node = serverNodes.get(currentServerIndex);
            String serverUrl = String.format("http://%s:%d", node.getAddress(), node.getPort());
            if (isServerAvailable(serverUrl)) {
                return node;
            }
        }
    }
}
