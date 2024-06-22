package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import controller.CapnhatController;
import model.Sachmodel;

public class Capnhat extends JFrame {
    private JTextField textFieldMaSach;
    private JTextField textFieldTenSach;
    private JTextField textFieldTacGia;
    private JTextField textFieldGia;
    private JTextField textField;
    private Trangchu quanlysach;
    private Sachmodel bookToUpdate;

    public Capnhat(Trangchu qls, Sachmodel book) {
        this.quanlysach = qls;
        this.bookToUpdate = book;

        getContentPane().setBackground(new Color(250, 250, 210));
        getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("CẬP NHẬT TIỂU THUYẾT");
        lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lblNewLabel.setBounds(25, 25, 302, 41);
        getContentPane().add(lblNewLabel);

        JLabel lblNewLabelMaSach = new JLabel("Mã sách");
        lblNewLabelMaSach.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblNewLabelMaSach.setBounds(25, 77, 104, 28);
        getContentPane().add(lblNewLabelMaSach);

        JLabel lblNewLabelTenSach = new JLabel("Tên sách");
        lblNewLabelTenSach.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblNewLabelTenSach.setBounds(25, 116, 104, 28);
        getContentPane().add(lblNewLabelTenSach);

        JLabel lblNewLabelTacGia = new JLabel("Tác giả");
        lblNewLabelTacGia.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblNewLabelTacGia.setBounds(25, 155, 104, 28);
        getContentPane().add(lblNewLabelTacGia);

        JLabel lblNewLabelGia = new JLabel("Giá");
        lblNewLabelGia.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblNewLabelGia.setBounds(25, 194, 104, 28);
        getContentPane().add(lblNewLabelGia);

        textFieldMaSach = new JTextField();
        textFieldMaSach.setBounds(157, 78, 145, 28);
        getContentPane().add(textFieldMaSach);
        textFieldMaSach.setColumns(10);
        textFieldMaSach.setText(String.valueOf(bookToUpdate.getMaSach()));
        textFieldMaSach.setEditable(false);

        textFieldTenSach = new JTextField();
        textFieldTenSach.setBounds(157, 117, 145, 28);
        getContentPane().add(textFieldTenSach);
        textFieldTenSach.setColumns(10);
        textFieldTenSach.setText(bookToUpdate.getTenSach());

        textFieldTacGia = new JTextField();
        textFieldTacGia.setBounds(157, 156, 145, 28);
        getContentPane().add(textFieldTacGia);
        textFieldTacGia.setColumns(10);
        textFieldTacGia.setText(bookToUpdate.getTacGia());

        textFieldGia = new JTextField();
        textFieldGia.setBounds(157, 195, 145, 28);
        getContentPane().add(textFieldGia);
        textFieldGia.setColumns(10);
        textFieldGia.setText(String.valueOf(bookToUpdate.getGia()));

        textField = new JTextField();
        textField.setBounds(157, 234, 145, 28);
        getContentPane().add(textField);
        textField.setColumns(10);
        textField.setText(String.valueOf(bookToUpdate.getSl()));

        JLabel lblNewLabel_1 = new JLabel("Số Lượng");
        lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblNewLabel_1.setBounds(25, 233, 104, 28);
        getContentPane().add(lblNewLabel_1);

        JButton btnNewButton = new JButton("Cập nhật");
        btnNewButton.setBackground(new Color(244, 164, 96));
        btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 17));
        btnNewButton.setBounds(30, 288, 121, 35);
        getContentPane().add(btnNewButton);

        JButton btnThot = new JButton("Thoát");
        btnThot.setFont(new Font("Times New Roman", Font.BOLD, 17));
        btnThot.setBackground(new Color(244, 164, 96));
        btnThot.setBounds(157, 288, 121, 35);
        getContentPane().add(btnThot);
        btnThot.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        CapnhatController controller = new CapnhatController(this, bookToUpdate, quanlysach);
        btnNewButton.addActionListener(controller);

        setSize(320, 386);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public JTextField getTextFieldTenSach() {
        return textFieldTenSach;
    }

    public JTextField getTextFieldTacGia() {
        return textFieldTacGia;
    }

    public JTextField getTextFieldGia() {
        return textFieldGia;
    }

    public JTextField getTextField() {
        return textField;
    }
}
