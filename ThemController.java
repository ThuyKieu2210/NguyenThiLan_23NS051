package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import DAO.SachDAO;
import DAO.ServerConnection;
import model.Sachmodel;
import view.Them;
import view.Trangchu;

public class ThemController implements ActionListener {
    private Them view;

    public ThemController(Them view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int maSach = Integer.parseInt(view.getTextField().getText());
            String tenSach = view.getTextField_1().getText();
            String tacGia = view.getTextField_2().getText();
            double gia = Double.parseDouble(view.getTextField_3().getText());
            int sl = Integer.parseInt(view.getTextField_4().getText());

            // Validate inputs
            if (tenSach.isEmpty() || tacGia.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin!");
                return;
            }

            if (gia <= 0) {
                JOptionPane.showMessageDialog(view, "Giá phải là số dương!");
                return;
            }

            if (sl <= 0) {
                JOptionPane.showMessageDialog(view, "Số lượng phải là số nguyên dương!");
                return;
            }

            Sachmodel book = new Sachmodel(maSach, tenSach, tacGia, gia, sl);
            boolean success = SachDAO.addBook(book);

            if (success) {
                JOptionPane.showMessageDialog(view, "Thêm thành công");
                view.dispose();
                Trangchu.updateTableData();
                ServerConnection.sendBookInfoToServer(book);
            } else {
                JOptionPane.showMessageDialog(view, "Đã xảy ra lỗi khi thêm sách.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập đúng định dạng số cho Mã sách, Giá và Số lượng!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Đã xảy ra lỗi: " + ex.getMessage());
        }
    }
}
