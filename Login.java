package view;

import javax.swing.*;

import DAO.UserDT;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class Login extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public Login() {
        setTitle("Đăng nhập");
        getContentPane().setBackground(new Color(250, 235, 215));
        this.setSize(356, 294);
        getContentPane().setLayout(null);

        JPanel pn1 = new JPanel();
        pn1.setBackground(new Color(255, 228, 225));
        pn1.setBounds(0, 0, 342, 53);
        getContentPane().add(pn1);
        pn1.setLayout(null);

        JLabel lb = new JLabel("ĐĂNG NHẬP");
        lb.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        lb.setBounds(112, 11, 202, 43);
        pn1.add(lb);

        JPanel pn2 = new JPanel();
        pn2.setBackground(new Color(250, 250, 210));
        pn2.setBounds(0, 52, 342, 100);
        getContentPane().add(pn2);
        pn2.setLayout(null);

        JLabel lbdn = new JLabel("Tên đăng nhập");
        lbdn.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbdn.setBounds(10, 20, 146, 30);
        pn2.add(lbdn);

        usernameField = new JTextField();
        usernameField.setBounds(156, 21, 186, 30);
        pn2.add(usernameField);
        usernameField.setColumns(10);

        JLabel lbmk = new JLabel("Mật khẩu");
        lbmk.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbmk.setBounds(10, 60, 116, 30);
        pn2.add(lbmk);

        passwordField = new JPasswordField();
        passwordField.setBounds(156, 61, 186, 30);
        pn2.add(passwordField);

        JButton btnLogin = new JButton("Đăng nhập");
        btnLogin.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        btnLogin.setBackground(new Color(154, 205, 50));
        btnLogin.setBounds(35, 161, 120, 23);
        getContentPane().add(btnLogin);
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String hashedPassword = md5Hash(password);
                System.out.println("Attempting to login with hashed password: " + hashedPassword);
                UserDT userDT = new UserDT();
                try {
					if (userDT.userExists(username) && hashedPassword.equals(userDT.getPasswordHash(username))) {
					    JOptionPane.showMessageDialog(null, "Đăng nhập thành công!");
					    new Giaodien();
					    dispose();
					} else {
					    JOptionPane.showMessageDialog(null, "Tên đăng nhập và mật khẩu không hợp lệ!");
					}
				} catch (HeadlessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });

        JButton btnExit = new JButton("Thoát");
        btnExit.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        btnExit.setBackground(new Color(154, 205, 50));
        btnExit.setBounds(190, 161, 120, 23);
        getContentPane().add(btnExit);

        JLabel lblNewLabel = new JLabel("Bạn chưa có tài khoản? Hãy đăng kí!");
        lblNewLabel.setForeground(new Color(220, 20, 60));
        lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        lblNewLabel.setBounds(10, 195, 252, 23);
        getContentPane().add(lblNewLabel);

        JButton btnngK = new JButton("Đăng ký");
        btnngK.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        btnngK.setBackground(new Color(154, 205, 50));
        btnngK.setBounds(105, 223, 120, 23);
        getContentPane().add(btnngK);
        btnngK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new dangki();
            }
        });

        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    private String md5Hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
