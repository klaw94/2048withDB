package com.example.spring;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBpopup extends JFrame implements ActionListener {
    JLabel DBlabel = new JLabel();
    JButton btn = new JButton();
    JTextField DBtext = new JTextField(10);
    JPanel panel = new JPanel();
    int score;


        public DBpopup(int score){
            DBlabel.setText("What's your name?");
            DBlabel.setSize(100,50);
            btn.setSize(10, 10);
            btn.setText("Submit");
            btn.addActionListener(this);
            DBlabel.setSize(100,50);
            DBtext.setSize(200, 50);

            this.add(panel);
            panel.add(DBlabel);
            panel.add(DBtext);
            panel.add(btn);
            this.setSize(200, 200);
            DBtext.setVisible(true);
            DBlabel.setVisible(true);
            panel.setVisible(true);
            this.setVisible(true);
            this.pack();
            this.score = score;
        }


    @Override
    public void actionPerformed(ActionEvent e) {
            String text = DBtext.getText();
        try {
            //Creating Connection Object
            Connection connection= DriverManager.getConnection("jdbc:postgresql://localhost:5432/newdb","postgres","password");
            //Preapared Statement
            PreparedStatement ps =connection.prepareStatement("INSERT INTO person (name, score) VALUES (?, ?)");
            //Specifying the values of it's parameter
            ps.setString(1, text);
            ps.setInt(2, score);
                //Checking for the Password match
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null,"Data Registered Successfully");


            } catch (SQLException e1) {
                e1.printStackTrace();
            }

        HallOfFame hallOfFame = new HallOfFame();
        this.dispose();
    }
}
