package view;

import java.awt.*;

import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import DAO.SachDAO;
import model.Sachmodel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Sach extends JPanel {
	public JTable table;
	public DefaultTableModel tableModel;

	public Sach() {
		setBackground(new Color(230, 230, 250));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 235, 205));

		panel.setBounds(22, 23, 540, 332);
		panel.setLayout(null);
		setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 64, 555, 289);
		add(scrollPane);

		tableModel = new DefaultTableModel();
		tableModel.addColumn("Mã Sách");
		tableModel.addColumn("Tên Sách");
		tableModel.addColumn("Tác Giả");
		tableModel.addColumn("Giá");
		tableModel.addColumn("Số Lượng");

		table = new JTable(tableModel);
		scrollPane.setViewportView(table);

		JLabel lblNewLabel = new JLabel("DANH SÁCH TIỂU THUYẾT");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel.setBounds(128, 11, 461, 25);
		panel.add(lblNewLabel);

		updateTableData();
		this.setSize(602, 438);

		JLabel lblNewLabel_1 = new JLabel("DANH SÁCH TIỂU THUYẾT");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel_1.setBounds(163, 11, 443, 24);
		add(lblNewLabel_1);

		JButton btnNewButton_2 = new JButton("Xem chi tiết");
		btnNewButton_2.setBackground(new Color(244, 164, 96));
		btnNewButton_2.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		btnNewButton_2.setBounds(223, 380, 159, 29);
		add(btnNewButton_2);

		btnNewButton_2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					viewBookDetails();
				}
			}
		});

		this.setVisible(true);

	}

	public void viewBookDetails() {
		int row = table.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(this, "Hãy chọn sách bạn muốn.");
			return;
		}

		int maSach = (int) tableModel.getValueAt(row, 0);
		String tenSach = (String) tableModel.getValueAt(row, 1);
		String tacGia = (String) tableModel.getValueAt(row, 2);
		double gia = (double) tableModel.getValueAt(row, 3);
		int sl = (int) tableModel.getValueAt(row, 4);

		Sachmodel sach = new Sachmodel(maSach, tenSach, tacGia, gia, sl);
		new Chitietsach(sach);
	}

	public void updateTableData() {
		SachDAO sachDAO = new SachDAO();
		List<Sachmodel> sachList = sachDAO.getAllBooks();

		tableModel.setRowCount(0);

		for (Sachmodel sach : sachList) {
			Object[] rowData = { sach.getMaSach(), sach.getTenSach(), sach.getTacGia(), sach.getGia(), sach.getSl() };
			tableModel.addRow(rowData);
		}
	}

	public DefaultTableModel getTableModel() {
		return tableModel;
	}
}
