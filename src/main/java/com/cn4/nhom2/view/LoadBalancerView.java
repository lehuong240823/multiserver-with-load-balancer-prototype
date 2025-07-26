/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cn4.nhom2.view;

import com.cn4.nhom2.entity.ServerNode;
import javafx.collections.ObservableList;
import javax.swing.*;
import javax.swing.GroupLayout.*;

/**
 *
 * @author User
 */
public class LoadBalancerView extends JFrame {

    private JPanel panel;
    private GroupLayout layout;
    private ParallelGroup parallelGroup;
    private SequentialGroup sequentialGroup;
    private ObservableList<ServerNode> nodes;

    public ObservableList<ServerNode> getNodes() {
        return nodes;
    }

    public void setNodes(ObservableList<ServerNode> nodes) {
        this.nodes = nodes;
    }

    public LoadBalancerView(ObservableList<ServerNode> nodes) {
        setTitle("Load Banlancer");
        initComponents();
        setLayout();
        createServerLabel(nodes);
        addComponents();
        setFrameDisplay();
    }

    private void initComponents() {
        panel = new JPanel();
        layout = new GroupLayout(panel);
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

    public void createServerLabel(ObservableList<ServerNode> nodes) {
        panel.removeAll();
        parallelGroup = layout.createParallelGroup();
        sequentialGroup = layout.createSequentialGroup();
        nodes.forEach((node) -> {
            String nodeValue = String.format("%s:%d", node.getAddress(), node.getPort());
            JLabel label = new JLabel(nodeValue);
            parallelGroup.addComponent(label);
            sequentialGroup.addComponent(label);
        });

        layout.setHorizontalGroup(parallelGroup);
        layout.setVerticalGroup(sequentialGroup);

        panel.revalidate();
        panel.repaint();
        

        setFrameDisplay();
    }

}
