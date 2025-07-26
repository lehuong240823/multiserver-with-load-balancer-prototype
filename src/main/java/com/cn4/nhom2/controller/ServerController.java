/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cn4.nhom2.controller;

import com.cn4.nhom2.entity.ServerNode;
import com.cn4.nhom2.model.LoadBalancer;
import com.cn4.nhom2.model.Server;
import com.cn4.nhom2.view.ServerView;
import java.net.InetAddress;
import java.time.LocalDateTime;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author User
 */
public class ServerController {

    private Server server;
    private ServerView serverView;

    public ServerController() {
        new ServerController(0);
    }

    public ServerController(int instance) {
        server = new Server(instance);
        InetAddress address = server.getAddress();
        int port = server.getPort();
        ServerNode node = new ServerNode(address.getHostAddress(), port, "");
        serverView = new ServerView(instance + 1, node);

        JButton startButton = serverView.getStartButton();
        JButton stopButton = serverView.getStopButton();
        JLabel startValue = serverView.getStartValue();

        startButton.addActionListener((l) -> {
            try {

                if (server.postNode(LoadBalancer.getUrlEndpoint(LoadBalancer.EP_BALANCER), node) == 200) {
                    server.start(address, port);

                    startButton.setEnabled(false);
                    serverView.getStopButton().setEnabled(true);
                    startValue.setText(LocalDateTime.now().toString());

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        stopButton.addActionListener((l) -> {
            try {
                if (server.deleteNode(LoadBalancer.getUrlEndpoint(LoadBalancer.EP_BALANCER), node) == 200) {
                    server.stop();
                    startButton.setEnabled(true);
                    stopButton.setEnabled(false);
                    startValue.setText(" ");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }

}
