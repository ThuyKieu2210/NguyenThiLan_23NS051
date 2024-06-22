package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import DAO.SachDAO;
import model.Kho;
import model.Sachmodel;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.awt.event.ActionEvent;

public class QuanLyKho extends JPanel {
	private static JTable table;
	private SachDAO dao;
	private Kho selectedSach;
	private DefaultTableModel model;
	private JTextField textField;

	public QuanLyKho() {
		setBackground(new Color(255, 228, 196));
		setLayout(null);

		JLabel lblNewLabel = new JLabel("QUẢN LÝ KHO SÁCH");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
		lblNewLabel.setBounds(174, 11, 390, 53);
		add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(240, 240, 240));
		panel.setBounds(26, 75, 627, 231);
		add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 627, 220);
		panel.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		dao = new SachDAO();
		String[] columnNames = { "Mã Sách", "Số lượng nhập vào", "Nhà cung cấp", "Ngày nhập" };
		model = new DefaultTableModel();
		model.setColumnIdentifiers(columnNames);

		for (Kho kho : dao.getAllKho()) {
			model.addRow(new Object[] { kho.getMS(), kho.getSlnv(), kho.getNhacc(), kho.getNgaynhap() });
		}

		table.setModel(model);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				if (row != -1) {
					int maSach = (int) model.getValueAt(row, 0);
					int sl = (int) model.getValueAt(row, 1);
					String nhacc = (String) model.getValueAt(row, 2);
					Date nn = (Date) model.getValueAt(row, 3);
					selectedSach = new Kho(maSach, sl, nhacc, nn);
				}
			}
		});

		JButton btnNewButton = new JButton("Thêm");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Themkho();
			}
		});
		btnNewButton.setBackground(new Color(244, 164, 96));
		btnNewButton.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btnNewButton.setBounds(189, 328, 125, 28);
		add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Cập nhật");
		btnNewButton_1.setBackground(new Color(244, 164, 96));
		btnNewButton_1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btnNewButton_1.setBounds(398, 328, 125, 28);
		add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedSach != null) {
					new Capnhatkho(QuanLyKho.this, selectedSach).setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Hãy chọn một cuốn sách trước khi cập nhật.");
				}
			}
		});
		updateTableData();

		JComboBox<String> comboBox = new JComboBox<>();
		comboBox.addItem("Mã Sách");
		comboBox.addItem("Số lượng nhập vào");
		comboBox.addItem("Nhà cung cấp");
		comboBox.addItem("Ngày nhập");
		comboBox.setBounds(65, 381, 165, 26);
		add(comboBox);

		JTextField textField = new JTextField();
		textField.setBounds(255, 381, 211, 26);
		add(textField);
		textField.setColumns(10);

		JButton btnTimKiem = new JButton("Tìm kiếm");
		btnTimKiem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedColumn = (String) comboBox.getSelectedItem();
				String searchText = textField.getText();
				search(selectedColumn, searchText);
			}
		});
		btnTimKiem.setBackground(new Color(244, 164, 96));
		btnTimKiem.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btnTimKiem.setBounds(492, 379, 125, 28);
		add(btnTimKiem);

	}

	public static void updateTableData() {
		SachDAO sachDAO = new SachDAO();
		List<Kho> khoList = sachDAO.getAllkho();

		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);

		for (Kho kho : khoList) {
			Object[] rowData = { kho.getMS(), kho.getSlnv(), kho.getNhacc(), kho.getNgaynhap() };
			model.addRow(rowData);
		}
	}

	public void updatekho(Kho book) {
		for (int i = 0; i < model.getRowCount(); i++) {
			if ((int) model.getValueAt(i, 0) == book.getMS()) {
				model.setValueAt(book.getSlnv(), i, 1);
				model.setValueAt(book.getNhacc(), i, 2);
				model.setValueAt(book.getNgaynhap(), i, 3);

				break;
			}
		}
	}

	public void search(String selectedColumn, String searchText) {
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
		table.setRowSorter(sorter);

		if (selectedColumn != null && !selectedColumn.isEmpty()) {
			sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText, getColumnIndex(selectedColumn)));
		} else {
			sorter.setRowFilter(null);
		}
	}

	public int getColumnIndex(String selectedColumn) {
		switch (selectedColumn) {
		case "Mã Sách":
			return 0;
		case "Số lượng nhập vào":
			return 1;
		case "Nhà cung cấp":
			return 2;
		case "Ngày nhập":
			return 3;
		default:
			return 0;
		}
	}

	public JPanel getPanel() {
		return this;
	}
}
