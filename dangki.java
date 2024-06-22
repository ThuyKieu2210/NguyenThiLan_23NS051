package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controller.DangKyController;

public class dangki extends JFrame {
    JPanel pn1, pn2;
    JLabel lb, lbdn, lbtk, lbmk;
    JButton btdn, btt;
    JTextField tftk;
    private JLabel lblNewLabel;
    private JButton btnNewButton;
    private JPasswordField passwordField;
    private JPasswordField passwordField_1;
    private JPasswordField passwordField_2;

    public dangki() {
        getContentPane().setBackground(new Color(250, 235, 215));
        this.setSize(356, 382);
        pn1 = new JPanel();
        pn1.setBounds(0, 0, 342, 53);
        pn1.setBackground(new Color(255, 228, 225));
        pn1.setLayout(null);
        lb = new JLabel("ĐĂNG KÝ");
        lb.setBackground(new Color(0, 0, 0));
        lb.setBounds(112, 11, 202, 43);
        lb.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        pn1.add(lb);

        getContentPane().setLayout(null);
        getContentPane().add(pn1);

        pn2 = new JPanel();
        pn2.setBackground(new Color(250, 250, 210));
        pn2.setBounds(0, 52, 342, 180);
        getContentPane().add(pn2);
        pn2.setLayout(null);

        JLabel lbdn = new JLabel("Tên đăng nhập");
        lbdn.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbdn.setBounds(10, 20, 146, 30);
        pn2.add(lbdn);

        JLabel lbmk = new JLabel("Mật khẩu");
        lbmk.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbmk.setBounds(10, 103, 116, 30);
        pn2.add(lbmk);

        tftk = new JTextField();
        tftk.setBounds(156, 21, 186, 30);
        pn2.add(tftk);
        tftk.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("Nhập lại mật khẩu");
        lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lblNewLabel_1.setBounds(10, 139, 126, 30);
        pn2.add(lblNewLabel_1);

        passwordField = new JPasswordField();
        passwordField.setBounds(156, 140, 186, 30);
        pn2.add(passwordField);

        passwordField_1 = new JPasswordField();
        passwordField_1.setBounds(156, 103, 186, 30);
        pn2.add(passwordField_1);

        passwordField_2 = new JPasswordField();
        passwordField_2.setBounds(156, 61, 186, 30);
        pn2.add(passwordField_2);

        JLabel lbmk_1 = new JLabel("Số ĐT");
        lbmk_1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbmk_1.setBounds(10, 61, 116, 30);
        pn2.add(lbmk_1);

        lblNewLabel = new JLabel("Bạn đã có tài khoản? Đăng nhập ngay!");
        lblNewLabel.setForeground(new Color(255, 0, 0));
        lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        lblNewLabel.setBounds(0, 277, 332, 23);
        getContentPane().add(lblNewLabel);

        btnNewButton = new JButton("Đăng nhập");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Login();
            }
        });
        btnNewButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        btnNewButton.setBackground(new Color(154, 205, 50));
        btnNewButton.setBounds(112, 311, 112, 23);
        getContentPane().add(btnNewButton);

        btt = new JButton("Thoát");
        btt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btt.setBounds(187, 243, 120, 23);
        getContentPane().add(btt);
        btt.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        btt.setBackground(new Color(154, 205, 50));

        btdn = new JButton("Đăng ký");
        btdn.setBounds(35, 243, 120, 23);
        getContentPane().add(btdn);
        btdn.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        btdn.setBackground(new Color(154, 205, 50));

        // Set controller for the register button
        DangKyController controller = new DangKyController(this);
        btdn.addActionListener(controller);

        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    public JTextField getTftk() {
        return tftk;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JPasswordField getPasswordField_1() {
        return passwordField_1;
    }

    public JPasswordField getPasswordField_2() {
        return passwordField_2;
    }

    public static void main(String[] args) {
        new dangki();
    }
}
