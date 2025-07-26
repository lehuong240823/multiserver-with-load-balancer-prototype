/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cn4.nhom2.controller;

import com.cn4.nhom2.entity.ServerNode;
import com.cn4.nhom2.entity.User;
import com.cn4.nhom2.model.Client;
import com.cn4.nhom2.model.Server;
import com.cn4.nhom2.view.ClientView;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.InetSocketAddress;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class ClientController {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private Client client;
    private ClientView clientView;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton getButton;
    private JButton updateButton;
    private JButton addButton;
    private JButton deleteButton;
    private JButton insertButton;
    private Vector rows;

    public static void main(String[] args) {
        try {
            new ClientController();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ClientController() {
        client = new Client();
        clientView = new ClientView();
        table = clientView.getTable();
        tableModel = clientView.getTableModel();
        getButton = clientView.getGetButton();
        updateButton = clientView.getUpdateButton();
        addButton = clientView.getAddButton();
        deleteButton = clientView.getDeleteButton();
        insertButton = clientView.getInsertButton();
        rows = clientView.getRows();
        
        table.getSelectionModel().addListSelectionListener((e) -> {
            deleteButton.setEnabled(table.getSelectedRow() != -1);
            updateButton.setEnabled(table.getSelectedRow() != -1 && table.getEditingRow() != -1);
        });

        tableModel.addTableModelListener((e) -> {

            switch (e.getType()) {
                case TableModelEvent.INSERT -> {
                    insertButton.setEnabled(false);
                }
                case TableModelEvent.DELETE -> {
                    updateButton.setEnabled(false);
                    addButton.setEnabled(false);
                }
                case TableModelEvent.UPDATE -> {
                    updateButton.setEnabled(true);
                }
                default -> {

                }
            }
        });

        getButton.addActionListener(l -> {
            getAllUsers();
        });

        insertButton.addActionListener(l -> {
            table.clearSelection();
            tableModel.addRow(new Vector(Arrays.asList("", "")));
            addButton.setEnabled(true);
        });

        addButton.addActionListener(l -> {
            String username = tableModel.getDataVector().lastElement().get(1).toString();
            System.out.println(username);
            if (username.equals("")) {
                JOptionPane.showMessageDialog(null, "Cell value can't be null", "Warning", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    User newUser = new User();
                    newUser.setUsername(username);
                    String json = objectMapper.writeValueAsString(newUser);

                    ServerNode node = client.fetchAvailableServer();
                    if (node != null) {
                        client.createClient();
                        HttpResponse<String> response = client.post(client.getUrlEndpoint(node, Server.EP_USER), json);
                        if (response.statusCode() == 200) {
                            JOptionPane.showMessageDialog(null, "Add successfull", "Add", JOptionPane.INFORMATION_MESSAGE);
                            getAllUsers();
                        } else {
                            JOptionPane.showMessageDialog(null, response.body(), String.valueOf(response.statusCode()), JOptionPane.ERROR_MESSAGE);
                        }

                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Can't connect with Server", e.getClass().getSimpleName(), JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
                insertButton.setEnabled(true);
            }
            System.out.println(username);

        });

        deleteButton.addActionListener(l -> {
            try {
                int rowIdx = table.getSelectedRow();
                int id = Integer.parseInt(tableModel.getDataVector().get(rowIdx).get(0).toString());
                ServerNode node = client.fetchAvailableServer();
                if (node != null) {
                    client.createClient();
                    HttpResponse<String> response = client.delete(String.format("%s/%d", client.getUrlEndpoint(node, Server.EP_USER), id));
                    if (response.statusCode() == 200) {
                        JOptionPane.showMessageDialog(null, "Delete successfull", "Delete", JOptionPane.INFORMATION_MESSAGE);
                        tableModel.removeRow(table.getSelectedRow());
                    } else {
                        JOptionPane.showMessageDialog(null, response.body(), String.valueOf(response.statusCode()), JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Can't connect with Server", e.getClass().getSimpleName(), JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }

        });

        updateButton.addActionListener(l -> {
            try {
                Vector selectedUser = tableModel.getDataVector().get(table.getSelectedRow());
                User updatedUser = new User(Integer.parseInt(selectedUser.get(0).toString()), selectedUser.get(1).toString());
                String json = objectMapper.writeValueAsString(updatedUser);
                ServerNode node = client.fetchAvailableServer();
                if (node != null) {
                    client.createClient();
                    HttpResponse<String> response = client.put(client.getUrlEndpoint(node, Server.EP_USER), json);
                    if (response.statusCode() == 200) {
                        JOptionPane.showMessageDialog(null, "Updated successfull", "Update", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, response.body(), String.valueOf(response.statusCode()), JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Can't connect with Server", e.getClass().getSimpleName(), JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        });

    }

    private void getAllUsers() {
        try {
            ServerNode node = client.fetchAvailableServer();
            if (node != null) {
                client.createClient();
                HttpResponse<String> response = client.get(client.getUrlEndpoint(node, Server.EP_USER));
                if (response.statusCode() == 200) {
                    List<User> users = objectMapper.readValue(response.body(), new TypeReference<List<User>>() {
                    });
                    rows.clear();
                    users.forEach((user) -> {
                        tableModel.addRow(
                                new Vector(
                                        Arrays.asList(
                                                String.valueOf(user.getId()), user.getUsername()
                                        )
                                )
                        );
                    });
                    insertButton.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(null, response.body(), String.valueOf(response.statusCode()), JOptionPane.ERROR_MESSAGE);
                }

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Can't connect with Server", e.getClass().getSimpleName(), JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

}
