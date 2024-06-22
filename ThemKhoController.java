package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.JOptionPane;

import DAO.SachDAO;
import DAO.ServerConnection;
import model.Kho;
import view.QuanLyKho;
import view.Themkho;

public class ThemKhoController {
    private Themkho view;

    public ThemKhoController(Themkho view) {
        this.view = view;
        initView();
    }

    private void initView() {
        view.getBtnNewButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Kiểm tra nhập đủ thông tin
                    if (view.getTextField().getText().isEmpty() || view.getTextField_1().getText().isEmpty()
                            || view.getTextField_2().getText().isEmpty() || view.getDateChooser().getDate() == null) {
                        view.showMessage("Vui lòng nhập đầy đủ thông tin!");
                        return;
                    }

                    int maSach = Integer.parseInt(view.getTextField().getText());
                    int soLuong = Integer.parseInt(view.getTextField_1().getText());
                    String noiNhap = view.getTextField_2().getText();
                    java.util.Date ngayNhapUtil = view.getDateChooser().getDate();
                    java.sql.Date ngayNhapSql = new java.sql.Date(ngayNhapUtil.getTime());

                    // Kiểm tra số lượng là số nguyên dương
                    if (soLuong <= 0) {
                        view.showMessage("Số lượng phải là số nguyên dương!");
                        return;
                    }

                    Kho book = new Kho(maSach, soLuong, noiNhap, ngayNhapSql);

                    boolean success = SachDAO.addkho(book);

                    if (success) {
                        view.showMessage("Thêm thành công");
                        ServerConnection.sendNewStockToServer(book);
                        view.dispose();
                        QuanLyKho.updateTableData();
                    } else {
                        view.showMessage("Đã xảy ra lỗi khi thêm sách.");
                    }
                } catch (NumberFormatException ex) {
                    view.showMessage("Vui lòng nhập đúng định dạng số cho mã sách và số lượng!");
                } catch (Exception ex) {
                    view.showMessage("Đã xảy ra lỗi: " + ex.getMessage());
                }
            }
        });

        view.getBtnThot().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                view.dispose();
            }
        });
    }
}
