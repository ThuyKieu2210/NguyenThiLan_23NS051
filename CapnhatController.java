package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import DAO.SachDAO;
import DAO.ServerConnection;
import model.Sachmodel;
import view.Capnhat;
import view.Trangchu;

public class CapnhatController implements ActionListener {
    private Capnhat cn;
    private Sachmodel bookToUpdate;
    private Trangchu quanlysach;
    private SachDAO sd;

    public CapnhatController(Capnhat cn, Sachmodel bookToUpdate, Trangchu quanlysach) {
        this.cn = cn;
        this.bookToUpdate = bookToUpdate;
        this.quanlysach = quanlysach;
        this.sd = new SachDAO();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String tenSach = cn.getTextFieldTenSach().getText();
            String tacGia = cn.getTextFieldTacGia().getText();
            String giaText = cn.getTextFieldGia().getText();
            String slText = cn.getTextField().getText();

            if (tenSach.isEmpty() || tacGia.isEmpty() || giaText.isEmpty() || slText.isEmpty()) {
                JOptionPane.showMessageDialog(cn, "Vui lòng nhập đầy đủ thông tin!");
                return;
            }

            int sl = Integer.parseInt(slText);
            double gia = Double.parseDouble(giaText);

            if (sl <= 0) {
                JOptionPane.showMessageDialog(cn, "Số lượng phải là số nguyên dương!");
                return;
            }

            if (gia <= 0) {
                JOptionPane.showMessageDialog(cn, "Giá phải là số dương!");
                return;
            }

            StringBuilder changes = new StringBuilder();
            if (!bookToUpdate.getTenSach().equals(tenSach)) {
                changes.append("Tên sách: ").append(bookToUpdate.getTenSach()).append(" -> ").append(tenSach).append("\n");
                bookToUpdate.setTenSach(tenSach);
            }
            if (!bookToUpdate.getTacGia().equals(tacGia)) {
                changes.append("Tác giả: ").append(bookToUpdate.getTacGia()).append(" -> ").append(tacGia).append("\n");
                bookToUpdate.setTacGia(tacGia);
            }
            if (bookToUpdate.getGia() != gia) {
                changes.append("Giá: ").append(bookToUpdate.getGia()).append(" -> ").append(gia).append("\n");
                bookToUpdate.setGia(gia);
            }
            if (bookToUpdate.getSl() != sl) {
                changes.append("Số lượng: ").append(bookToUpdate.getSl()).append(" -> ").append(sl).append("\n");
                bookToUpdate.setSl(sl);
            }

            boolean isSuccess = sd.updateBook(bookToUpdate);

            if (isSuccess) {
                JOptionPane.showMessageDialog(cn, "Cập nhật thành công");
                quanlysach.updateBook(bookToUpdate);
                ServerConnection.sendBookUpdateToServer(bookToUpdate, changes.toString());
                cn.dispose();
            } else {
                JOptionPane.showMessageDialog(cn, "Cập nhật không thành công");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(cn, "Vui lòng nhập đúng định dạng số cho Giá và Số lượng!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(cn, "Đã xảy ra lỗi: " + ex.getMessage());
        }
    }
}
