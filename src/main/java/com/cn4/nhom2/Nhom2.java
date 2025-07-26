/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.cn4.nhom2;

import com.cn4.nhom2.controller.ClientController;
import com.cn4.nhom2.controller.LoadBalancerController;

/**
 *
 * @author User
 */
public class Nhom2 {
    
    public static void main(String[] args) {
        try {
            Thread t1 = new Thread(() -> {
                new LoadBalancerController();
            });
            Thread t2 = new Thread(() -> {
                MultiServerSimulation.startMultiThreadServer(MultiServerSimulation.NUM);
            });
            Thread t3 = new Thread(() -> {
                new ClientController();
            });
            t1.start();
            t2.start();
            t3.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
