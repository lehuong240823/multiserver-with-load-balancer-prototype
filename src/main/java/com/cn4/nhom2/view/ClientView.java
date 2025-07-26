/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cn4.nhom2.view;

import java.util.Arrays;
import java.util.Vector;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class ClientView extends JFrame {

    private JPanel panel;
    private GroupLayout layout;
    private JButton getButton;
    private JButton updateButton;
    private JButton addButton;
    private JButton deleteButton;
    private JButton insertButton;
    private Vector header;
    private Vector rows;
    private DefaultTableModel tableModel;
    private JTable table;

    private JScrollPane scrollPane;

    public JButton getGetButton() {
        return getButton;
    }

    public ClientView() {

        initComponents();

        setLayout();
        setLayoutGroup();

        addComponents();
        setTitle("Client");
        setFrameDisplay();
    }

    private void initComponents() {
        panel = new JPanel();
        layout = new GroupLayout(panel);
        getButton = new JButton("GET");
        updateButton = new JButton("UPDATE");
        addButton = new JButton("ADD");
        deleteButton = new JButton("DELETE");
        insertButton = new JButton("INSERT");
        header = new Vector(Arrays.asList("ID", "Username"));
        rows = new Vector();

        tableModel = new DefaultTableModel(rows, header);
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        insertButton.setEnabled(false);
        deleteButton.setEnabled(false);
        updateButton.setEnabled(false);
        addButton.setEnabled(false);
    }

    private void buttonAction() {
        
        

    }

    private void addComponents() {
        add(panel);

        header.add("ID");
        header.add("Username");
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
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addComponent(scrollPane)
                        .addGroup(
                                layout.createParallelGroup()
                                        .addComponent(getButton)
                                        .addComponent(insertButton)
                                        .addComponent(addButton)
                                        .addComponent(updateButton)
                                        .addComponent(deleteButton)
                        )
        );

        layout.setVerticalGroup(
                layout.createParallelGroup()
                        .addComponent(scrollPane)
                        .addGroup(
                                layout.createSequentialGroup()
                                        .addComponent(getButton)
                                        .addComponent(insertButton)
                                        .addComponent(addButton)
                                        .addComponent(updateButton)
                                        .addComponent(deleteButton)
                        )
        );
    }

    public JButton getUpdateButton() {
        return updateButton;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getInsertButton() {
        return insertButton;
    }

    public Vector getRows() {
        return rows;
    }

    public Vector getHeader() {
        return header;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JTable getTable() {
        return table;
    }
}
