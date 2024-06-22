package controller;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.regex.Pattern;

import DAO.UserDT;
import view.dangki;
import view.Login;
import view.Giaodien;

public class DangKyController implements ActionListener {
    private dangki view;

    public DangKyController(dangki view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = view.getTftk().getText();
        String password = new String(view.getPasswordField_1().getPassword());
        String confirmPassword = new String(view.getPasswordField().getPassword());
        String phoneNumber = view.getPasswordField_2().getText();

        // Validate username (should not start with a digit)
        if (username.matches("^\\d.*")) {
            JOptionPane.showMessageDialog(view, "Tên đăng nhập không được bắt đầu bằng số!");
            return;
        }

        // Validate password strength (minimum length and complexity)
        if (!isValidPassword(password)) {
            JOptionPane.showMessageDialog(view, "Mật khẩu không đủ mạnh! Đảm bảo mật khẩu có ít nhất 8 ký tự và bao gồm chữ hoa, chữ thường và số.");
            return;
        }

        // Validate phone number format (exactly 10 digits and not starting with 0)
        if (!isValidPhoneNumber(phoneNumber)) {
            JOptionPane.showMessageDialog(view, "Số ĐT không hợp lệ! Đảm bảo số ĐT có 10 chữ số và không bắt đầu bằng số 0.");
            return;
        }

        // Hash the password before storing it
        String hashedPassword = md5Hash(password);
        System.out.println("Tài khoản đăng ký với mật khẩu được mã hóa là: " + hashedPassword);
        UserDT userDT = new UserDT();
        try {
            if (userDT.addUser(username, hashedPassword)) {
                JOptionPane.showMessageDialog(view, "Đăng ký thành công!");
                new Giaodien();
                view.dispose();
            } else {
                JOptionPane.showMessageDialog(view, "Đăng ký thất bại! Tên đăng nhập đã tồn tại.");
            }
        } catch (HeadlessException e1) {
            e1.printStackTrace();
            JOptionPane.showMessageDialog(view, "Lỗi giao diện: " + e1.getMessage());
        } catch (SQLException e1) {
            e1.printStackTrace();
            JOptionPane.showMessageDialog(view, "Lỗi cơ sở dữ liệu: " + e1.getMessage());
        }
    }

    private boolean isValidPassword(String password) {
        // Password must be at least 8 characters long and contain at least one digit, one uppercase letter, and one lowercase letter
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
        return Pattern.matches(passwordRegex, password);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        // Phone number must be exactly 10 digits and should not start with '0'
        return phoneNumber.matches("^0\\d{9}$");
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
}
