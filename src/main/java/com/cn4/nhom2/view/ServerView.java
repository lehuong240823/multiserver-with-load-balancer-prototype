/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cn4.nhom2.view;

import com.cn4.nhom2.entity.ServerNode;
import javax.swing.*;

/**
 *
 * @author User
 */
public class ServerView extends JFrame {

    private JPanel panel;
    private GroupLayout layout;
    private JLabel addressLabel;
    private JLabel portLabel;
    private JLabel startLabel;
    private JLabel addressValue;
    private JLabel portValue;
    private JLabel startValue;
    private JButton startButton;
    private JButton stopButton;

    public JLabel getAddressValue() {
        return addressValue;
    }

    public JLabel getPortValue() {
        return portValue;
    }

    public JLabel getStartValue() {
        return startValue;
    }

    public JButton getStartButton() {
        return startButton;
    }

    public JButton getStopButton() {
        return stopButton;
    }
    private ServerNode node;

    public ServerView(int index, ServerNode node) {
        this.node = node;
        try {
            initComponents();
            setLayout();
            setLayoutGroup();
            addComponents();

            setTitle(String.format("Server #%d: %s", index, node.getAddress()));
            setFrameDisplay();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initComponents() throws Exception {
        panel = new JPanel();
        layout = new GroupLayout(panel);

        addressLabel = new JLabel("IP address: ");
        portLabel = new JLabel("Port: ");
        startLabel = new JLabel("Start running at: ");

        addressValue = new JLabel(node.getAddress());
        portValue = new JLabel(String.valueOf(node.getPort()));
        startValue = new JLabel(" ");

        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        stopButton.setEnabled(false);

    }

    private void addComponents() {
        add(panel);
    }

    private void setLayout() {
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        panel.setLayout(layout);
    }

    private void setFrameDisplay() {
        pack();
        setLocationRelativeTo(null);
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void setLayoutGroup() {
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(
                        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(addressLabel)
                                .addComponent(portLabel)
                                .addComponent(startLabel)
                                .addComponent(startButton)
                )
                .addGroup(
                        layout.createParallelGroup()
                                .addComponent(addressValue)
                                .addComponent(portValue)
                                .addComponent(startValue)
                                .addComponent(stopButton)
                )
        );

        layout.setVerticalGroup(layout.createParallelGroup()
                .addGroup(
                        layout.createSequentialGroup()
                                .addComponent(addressLabel)
                                .addComponent(portLabel)
                                .addComponent(startLabel)
                                .addComponent(startButton)
                )
                .addGroup(
                        layout.createSequentialGroup()
                                .addComponent(addressValue)
                                .addComponent(portValue)
                                .addComponent(startValue)
                                .addComponent(stopButton)
                )
        );
    }

    public ServerNode getNode() {
        return node;
    }

    public void setNode(ServerNode node) {
        this.node = node;
        portValue.setText(String.valueOf(node.getPort()));
    }

}
