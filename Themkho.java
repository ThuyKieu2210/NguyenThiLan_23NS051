package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import com.toedter.calendar.JDateChooser;
import controller.ThemKhoController;
import model.Kho;

public class Themkho extends JFrame {
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JDateChooser dateChooser;
    private JButton btnNewButton;
    private JButton btnThot;

    public Themkho() {
        getContentPane().setBackground(new Color(250, 250, 210));
        getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("NHẬP KHO TIỂU THUYẾT");
        lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lblNewLabel.setBounds(37, 22, 302, 41);
        getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Mã sách");
        lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblNewLabel_1.setBounds(25, 77, 104, 28);
        getContentPane().add(lblNewLabel_1);

        textField = new JTextField();
        textField.setBounds(157, 78, 145, 28);
        getContentPane().add(textField);
        textField.setColumns(10);

        textField_1 = new JTextField();
        textField_1.setBounds(157, 117, 145, 28);
        getContentPane().add(textField_1);
        textField_1.setColumns(10);

        textField_2 = new JTextField();
        textField_2.setBounds(157, 156, 145, 28);
        getContentPane().add(textField_2);
        textField_2.setColumns(10);

        JLabel lblNewLabel_5 = new JLabel("Số lượng");
        lblNewLabel_5.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblNewLabel_5.setBounds(25, 116, 104, 28);
        getContentPane().add(lblNewLabel_5);

        JLabel lblNewLabel_2 = new JLabel("Nơi nhập");
        lblNewLabel_2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblNewLabel_2.setBounds(25, 156, 104, 28);
        getContentPane().add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Ngày nhập");
        lblNewLabel_3.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblNewLabel_3.setBounds(25, 195, 104, 28);
        getContentPane().add(lblNewLabel_3);

        dateChooser = new JDateChooser();
        dateChooser.setBounds(157, 197, 145, 28);
        getContentPane().add(dateChooser);

        btnNewButton = new JButton("Thêm");
        btnNewButton.setBackground(new Color(244, 164, 96));
        btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 17));
        btnNewButton.setBounds(37, 257, 104, 28);
        getContentPane().add(btnNewButton);

        btnThot = new JButton("Thoát");
        btnThot.setFont(new Font("Times New Roman", Font.BOLD, 17));
        btnThot.setBackground(new Color(244, 164, 96));
        btnThot.setBounds(157, 257, 104, 28);
        getContentPane().add(btnThot);


        new ThemKhoController(this);

        this.setVisible(true);
        this.setSize(323, 346);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public JTextField getTextField() {
        return textField;
    }

    public JTextField getTextField_1() {
        return textField_1;
    }

    public JTextField getTextField_2() {
        return textField_2;
    }

    public JDateChooser getDateChooser() {
        return dateChooser;
    }

    public JButton getBtnNewButton() {
        return btnNewButton;
    }

    public JButton getBtnThot() {
        return btnThot;
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
