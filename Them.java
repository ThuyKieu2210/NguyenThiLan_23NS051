package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import controller.ThemController;

public class Them extends JFrame {
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JTextField textField_4;

    public Them() {
        getContentPane().setBackground(new Color(250, 250, 210));
        getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("THÊM TIỂU THUYẾT MỚI");
        lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lblNewLabel.setBounds(25, 25, 302, 41);
        getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Mã sách");
        lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblNewLabel_1.setBounds(25, 77, 104, 28);
        getContentPane().add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Tác giả");
        lblNewLabel_2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblNewLabel_2.setBounds(25, 155, 104, 28);
        getContentPane().add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Giá");
        lblNewLabel_3.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblNewLabel_3.setBounds(25, 194, 104, 28);
        getContentPane().add(lblNewLabel_3);

        JLabel lblNewLabel_4 = new JLabel("Tên sách");
        lblNewLabel_4.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblNewLabel_4.setBounds(25, 116, 104, 28);
        getContentPane().add(lblNewLabel_4);

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

        textField_3 = new JTextField();
        textField_3.setBounds(157, 195, 145, 28);
        getContentPane().add(textField_3);
        textField_3.setColumns(10);

        JButton btnNewButton = new JButton("Thêm");
        btnNewButton.setBackground(new Color(244, 164, 96));
        btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 17));
        btnNewButton.setBounds(43, 289, 104, 28);
        getContentPane().add(btnNewButton);

        JLabel lblNewLabel_5 = new JLabel("Số lượng");
        lblNewLabel_5.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblNewLabel_5.setBounds(25, 235, 104, 28);
        getContentPane().add(lblNewLabel_5);

        textField_4 = new JTextField();
        textField_4.setBounds(157, 234, 145, 28);
        getContentPane().add(textField_4);
        textField_4.setColumns(10);

        JButton btnNewButton_1 = new JButton("Thoát");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnNewButton_1.setFont(new Font("Times New Roman", Font.BOLD, 17));
        btnNewButton_1.setBackground(new Color(244, 164, 96));
        btnNewButton_1.setBounds(172, 289, 104, 28);
        getContentPane().add(btnNewButton_1);

        // Set controller for the add button
        ThemController controller = new ThemController(this);
        btnNewButton.addActionListener(controller);

        this.setVisible(true);
        this.setSize(323, 375);
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

    public JTextField getTextField_3() {
        return textField_3;
    }

    public JTextField getTextField_4() {
        return textField_4;
    }

    public static void main(String[] args) {
        new Them();
    }
}
