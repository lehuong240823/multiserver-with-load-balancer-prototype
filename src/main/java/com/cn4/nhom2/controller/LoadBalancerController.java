/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cn4.nhom2.controller;

import com.cn4.nhom2.model.LoadBalancer;
import com.cn4.nhom2.entity.ServerNode;
import com.cn4.nhom2.view.LoadBalancerView;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 *
 * @author User
 */
public class LoadBalancerController {
private LoadBalancerView view;
private final LoadBalancer loadBalancer;

    public LoadBalancerController() {
        loadBalancer = new LoadBalancer();
        ObservableList<ServerNode> serverNodes = loadBalancer.getServerNodes();
        view = new LoadBalancerView(serverNodes);
            serverNodes.addListener((ListChangeListener.Change<? extends ServerNode> change) -> {
                while (change.next()) {
                    if (change.wasAdded()) {
                        view.createServerLabel(serverNodes);
                    }
                    if (change.wasRemoved()) {
                        view.createServerLabel(serverNodes);
                   
                    }
                }
            });
    }
    
}
