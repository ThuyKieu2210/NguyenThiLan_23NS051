package view;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import DAO.SachDAO;
import DAO.ServerConnection;
import model.Sachmodel;

public class Trangchu extends JPanel {
	private static Sach sach;
	private JTable table;
	private Sachmodel selectedSach;
	private static DefaultTableModel tableModel;

	public Trangchu() {
		setBackground(new Color(255, 228, 196));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 235, 205));

		panel.setBounds(22, 23, 540, 332);
		panel.setLayout(null);
		setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 64, 644, 275);
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

		sach = new Sach();
		sach.updateTableData();

		this.setSize(708, 425);

		JLabel lblNewLabel_1 = new JLabel("CỬA HÀNG BÁN TIỂU THUYẾT");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 30));
		lblNewLabel_1.setBounds(126, 18, 508, 35);
		add(lblNewLabel_1);

		JButton btnNewButton_2 = new JButton("Xem chi tiết");
		btnNewButton_2.setBackground(new Color(244, 164, 96));
		btnNewButton_2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btnNewButton_2.setBounds(52, 364, 113, 29);
		add(btnNewButton_2);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedSach != null) {
					new Chitietsach(selectedSach);
				} else {
					JOptionPane.showMessageDialog(null, "Hãy chọn một cuốn sách trước khi xem chi tiết.");
				}
			}
		});
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				if (row != -1) {
					int maSach = (int) tableModel.getValueAt(row, 0);
					String tenSach = (String) tableModel.getValueAt(row, 1);
					String tacGia = (String) tableModel.getValueAt(row, 2);
					double gia = (double) tableModel.getValueAt(row, 3);
					int sl = (int) tableModel.getValueAt(row, 4);
					selectedSach = new Sachmodel(maSach, tenSach, tacGia, gia, sl);
				}
			}
		});

		JButton btnNewButton = new JButton("Thêm");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Them();
			}
		});
		btnNewButton.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btnNewButton.setBackground(new Color(244, 164, 96));
		btnNewButton.setBounds(216, 364, 113, 29);
		add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Xóa");
		btnNewButton_1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btnNewButton_1.setBackground(new Color(244, 164, 96));
		btnNewButton_1.setBounds(377, 364, 113, 29);
		add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) {

					int id = (int) tableModel.getValueAt(selectedRow, 0);

					boolean success = SachDAO.deleteBook(id);

					if (success) {
						Sachmodel deletedBook = new Sachmodel(id, "", "", 0.0, 0);
		                ServerConnection.sendDeletedBookToServer(deletedBook);

						tableModel.removeRow(selectedRow);
						JOptionPane.showMessageDialog(null, "Đã xóa sách khỏi cơ sở dữ liệu.");
					} else {
						JOptionPane.showMessageDialog(null, "Xảy ra lỗi khi xóa sách từ cơ sở dữ liệu.");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Hãy chọn một hàng để xóa.");
				}
			}
		});

		JButton btnNewButton_3 = new JButton("Cập nhật");
		btnNewButton_3.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btnNewButton_3.setBackground(new Color(244, 164, 96));
		btnNewButton_3.setBounds(537, 364, 113, 29);
		add(btnNewButton_3);
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedSach != null) {
					new Capnhat(Trangchu.this, selectedSach).setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Hãy chọn một cuốn sách trước khi cập nhật.");
				}
			}
		});

		updateTableData();

		this.setVisible(true);
	}

	public static void updateTableData() {
		SachDAO sachDAO = new SachDAO();
		List<Sachmodel> sachList = sachDAO.getAllBooks();

		tableModel.setRowCount(0);

		for (Sachmodel sach : sachList) {
			Object[] rowData = { sach.getMaSach(), sach.getTenSach(), sach.getTacGia(), sach.getGia(), sach.getSl() };
			tableModel.addRow(rowData);
		}
	}

	public void updateBook(Sachmodel book) {
	  
	    for (int i = 0; i < tableModel.getRowCount(); i++) {
	        
	        if ((int) tableModel.getValueAt(i, 0) == book.getMaSach()) {
	          
	            tableModel.setValueAt(book.getTenSach(), i, 1);
	            tableModel.setValueAt(book.getTacGia(), i, 2);
	            tableModel.setValueAt(book.getGia(), i, 3);
	            tableModel.setValueAt(book.getSl(), i, 4);
	           
	            break;
	        }
	    }
	}

	public static int getBookQuantity(int bookID) {
		for (int i = 0; i < tableModel.getRowCount(); i++) {
			if ((int) tableModel.getValueAt(i, 0) == bookID) {
				return (int) tableModel.getValueAt(i, 4);
			}
		}
		return -1;
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Quản lý sách");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 450);
		frame.getContentPane().add(new Trangchu());
		frame.setVisible(true);
	}

	public JPanel getPanel() {
		return this;
	}
}