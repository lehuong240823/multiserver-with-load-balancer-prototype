/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cn4.nhom2.entity;

/**
 *
 * @author User
 */
public class ServerNode {
    private String address;
    private int port;
    private String sendAT;

    public ServerNode() {
    }

    public ServerNode(String address, int port, String sendAT) {
        this.address = address;
        this.port = port;
        this.sendAT = sendAT;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getSendAT() {
        return sendAT;
    }

    public void setSendAT(String sendAT) {
        this.sendAT = sendAT;
    }
    
    
}
