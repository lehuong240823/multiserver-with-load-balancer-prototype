/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cn4.nhom2;

import com.cn4.nhom2.controller.ServerController;

/**
 *
 * @author User
 */
public class MultiServerSimulation extends Thread {
    public static int NUM = 4;
    int instance;

    public MultiServerSimulation(int instance) {
        this.instance = instance;
    }

    @Override
    public void run() {
        ServerController serverController = new ServerController(this.instance);
    }

    public static void main(String[] args) {
        startMultiThreadServer(NUM);
    }
    
    public static void startMultiThreadServer(int num) {
        for (int i = 0; i < num; ++i) {
            MultiServerSimulation multiServer = new MultiServerSimulation(i);
            multiServer.start();
        }
    }
}
