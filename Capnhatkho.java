package view;

import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import javax.swing.*;

import model.Kho;

import com.toedter.calendar.JDateChooser;

import DAO.SachDAO;
import DAO.ServerConnection;

public class Capnhatkho extends JFrame {
    private JTextField textFieldMaSach;
    private JTextField textFieldSoLuong;
    private JTextField textFieldNhaCungCap;
    private QuanLyKho quanlysach;
    private Kho bookToUpdate;
    private SachDAO sd;

    public Capnhatkho(QuanLyKho qls, Kho book) {
        this.quanlysach = qls;
        this.bookToUpdate = book;
        sd = new SachDAO();
        getContentPane().setBackground(new Color(250, 250, 210));
        getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("CẬP NHẬT KHO");
        lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lblNewLabel.setBounds(60, 25, 302, 41);
        getContentPane().add(lblNewLabel);

        JLabel lblNewLabelMaSach = new JLabel("Mã sách");
        lblNewLabelMaSach.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblNewLabelMaSach.setBounds(25, 77, 104, 28);
        getContentPane().add(lblNewLabelMaSach);

        textFieldMaSach = new JTextField();
        textFieldMaSach.setBounds(157, 78, 145, 28);
        getContentPane().add(textFieldMaSach);
        textFieldMaSach.setColumns(10);
        textFieldMaSach.setText(String.valueOf(bookToUpdate.getMS()));

        JLabel lblNewLabel_1 = new JLabel("Số Lượng");
        lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblNewLabel_1.setBounds(25, 116, 104, 28);
        getContentPane().add(lblNewLabel_1);

        textFieldSoLuong = new JTextField();
        textFieldSoLuong.setBounds(157, 117, 145, 28);
        getContentPane().add(textFieldSoLuong);
        textFieldSoLuong.setColumns(10);
        textFieldSoLuong.setText(String.valueOf(bookToUpdate.getSlnv()));

        JLabel lblNewLabel_1_1 = new JLabel("Nhà cung cấp");
        lblNewLabel_1_1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblNewLabel_1_1.setBounds(25, 156, 104, 28);
        getContentPane().add(lblNewLabel_1_1);

        textFieldNhaCungCap = new JTextField();
        textFieldNhaCungCap.setBounds(157, 156, 145, 28);
        getContentPane().add(textFieldNhaCungCap);
        textFieldNhaCungCap.setColumns(10);
        textFieldNhaCungCap.setText(bookToUpdate.getNhacc());

        JLabel lblNewLabel_1_1_1 = new JLabel("Ngày nhập");
        lblNewLabel_1_1_1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblNewLabel_1_1_1.setBounds(25, 195, 104, 28);
        getContentPane().add(lblNewLabel_1_1_1);

        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setBounds(157, 195, 145, 28);
        getContentPane().add(dateChooser);

        JButton btnNewButton = new JButton("Cập nhật");
        btnNewButton.setBackground(new Color(244, 164, 96));
        btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 17));
        btnNewButton.setBounds(24, 248, 121, 35);
        getContentPane().add(btnNewButton);

        JButton btnThoat = new JButton("Thoát");
        btnThoat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnThoat.setFont(new Font("Times New Roman", Font.BOLD, 17));
        btnThoat.setBackground(new Color(244, 164, 96));
        btnThoat.setBounds(157, 248, 121, 35);
        getContentPane().add(btnThoat);

        // Thiết lập dữ liệu cho các trường nhập liệu từ đối tượng bookToUpdate
        textFieldMaSach.setText(String.valueOf(bookToUpdate.getMS()));
        textFieldSoLuong.setText(String.valueOf(bookToUpdate.getSlnv()));
        textFieldNhaCungCap.setText(bookToUpdate.getNhacc());
        dateChooser.setDate(bookToUpdate.getNgaynhap());

        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int maSach = Integer.parseInt(textFieldMaSach.getText());
                    int soLuong = Integer.parseInt(textFieldSoLuong.getText());
                    String nhaCungCap = textFieldNhaCungCap.getText();
                    java.util.Date ngayNhapUtil = dateChooser.getDate();
                    java.sql.Date ngayNhapSql = new java.sql.Date(ngayNhapUtil.getTime());

                    // Kiểm tra số lượng là số nguyên dương
                    if (soLuong <= 0) {
                        JOptionPane.showMessageDialog(null, "Số lượng phải là số nguyên dương!");
                        return;
                    }

                    // Cập nhật thông tin cho đối tượng bookToUpdate
                    bookToUpdate.setMS(maSach);
                    bookToUpdate.setSlnv(soLuong);
                    bookToUpdate.setNhacc(nhaCungCap);
                    bookToUpdate.setNgaynhap(ngayNhapSql);

                    // Gọi phương thức updateKho từ lớp SachDAO để cập nhật vào cơ sở dữ liệu
                    boolean isSuccess = sd.updateKho(bookToUpdate);

                    if (isSuccess) {
                        JOptionPane.showMessageDialog(null, "Cập nhật thành công");
                        qls.updatekho(bookToUpdate); // Cập nhật lại giao diện QuanLyKho
                        ServerConnection.sendUpdatedStockToServer(bookToUpdate); // Gửi thông tin cập nhật lên server
                        dispose(); // Đóng cửa sổ sau khi cập nhật thành công
                    } else {
                        JOptionPane.showMessageDialog(null, "Cập nhật không thành công");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng định dạng số.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi: " + ex.getMessage());
                }
            }
        });

        setSize(320, 339);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
