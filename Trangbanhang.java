package view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Date;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import DAO.SachDAO;
import DAO.ServerConnection;
import model.Sachmodel;

public class Trangbanhang extends JPanel {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_4;
	private JTextField textField_5;
	private SachDAO dao;
	private JDateChooser dateChooser;
	private Sachmodel currentBook;
	private static DefaultTableModel tableModel;
	private Doanhthu doanhthuPanel;
	private Consumer<String> actionListener;

	public Trangbanhang(DefaultTableModel tableModel, Doanhthu doanhthuPanel) {
		setBackground(new Color(255, 228, 181));
		setLayout(null);
		dao = new SachDAO();
		this.tableModel = tableModel;
		this.doanhthuPanel = doanhthuPanel;

		JLabel lblNewLabel = new JLabel("BÁN TIỂU THUYẾT");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
		lblNewLabel.setBounds(89, 0, 312, 46);
		add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Mã sách");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		lblNewLabel_1.setBounds(59, 74, 125, 28);
		add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Tên sách");
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		lblNewLabel_2.setBounds(59, 113, 125, 28);
		add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Giá sách");
		lblNewLabel_3.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		lblNewLabel_3.setBounds(59, 155, 125, 28);
		add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Ngày bán");
		lblNewLabel_4.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		lblNewLabel_4.setBounds(59, 255, 125, 28);
		add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("Thành tiền");
		lblNewLabel_5.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		lblNewLabel_5.setBounds(59, 311, 125, 28);
		add(lblNewLabel_5);

		textField = new JTextField();
		textField.setBounds(194, 78, 187, 25);
		add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(194, 117, 187, 25);
		add(textField_1);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(194, 159, 187, 25);
		add(textField_2);

		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(194, 315, 187, 25);
		add(textField_4);

		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(194, 209, 187, 25);
		add(textField_5);

		JLabel lblNewLabel_3_1 = new JLabel("Số lượng");
		lblNewLabel_3_1.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		lblNewLabel_3_1.setBounds(59, 205, 125, 28);
		add(lblNewLabel_3_1);

		dateChooser = new JDateChooser();
		dateChooser.setBounds(196, 255, 185, 28);
		add(dateChooser);

		textField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {

				updateBookInfo(Integer.parseInt(textField.getText()));
			}
		});

		textField_5.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				calculateTotal();
			}
		});

		dateChooser.addPropertyChangeListener("date", evt -> {
			calculateTotal();
		});

		JButton btnNewButton = new JButton("Bán hàng");
		btnNewButton.setBackground(new Color(244, 164, 96));
		btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 17));
		btnNewButton.setBounds(89, 373, 125, 28);
		add(btnNewButton);
		
		JButton btnThot = new JButton("Thoát");
		btnThot.setFont(new Font("Times New Roman", Font.BOLD, 17));
		btnThot.setBackground(new Color(244, 164, 96));
		btnThot.setBounds(243, 373, 125, 28);
		add(btnThot);

		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sellBook();
			}
		});
	}

	private void updateBookInfo(int bookID) {

		currentBook = dao.getBookDetailsByID(bookID);
		if (currentBook != null) {

			textField_1.setText(currentBook.getTenSach());
			textField_2.setText(String.valueOf(currentBook.getGia()));

			textField_5.requestFocusInWindow();
		} else {

			JOptionPane.showMessageDialog(Trangbanhang.this, "Không tìm thấy sách với mã " + bookID);
			textField_1.setText("");
			textField_2.setText("");
		}
	}
	public void setActionListener(Consumer<String> actionListener) {
        this.actionListener = actionListener;
    }

	private void calculateTotal() {
		try {
			double gia = Double.parseDouble(textField_2.getText());
			int soLuong = Integer.parseInt(textField_5.getText());
			if (soLuong <= 0) {
                JOptionPane.showMessageDialog(Trangbanhang.this, "Số lượng phải là số nguyên dương.");
                textField_5.setText("");
                return;
            }

			java.util.Date utilDate = dateChooser.getDate();
			if (utilDate != null) {
				java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

				double thanhTien = gia * soLuong;

				textField_4.setText(String.valueOf(thanhTien));
			} else {

				textField_4.setText("");
			}
		} catch (NumberFormatException e) {

			textField_4.setText("");
		}
	}

	private void sellBook() {
		try {
			int bookID = Integer.parseInt(textField.getText());
			int soLuongBan = Integer.parseInt(textField_5.getText());

			if (currentBook != null) {
				int soLuongConLai = Trangchu.getBookQuantity(bookID) - soLuongBan;
				if (soLuongConLai >= 0) {
					dao.updateBookQuantity(bookID, soLuongConLai);

					double totalPrice = Double.parseDouble(textField_4.getText());
					Date currentDate = new Date(System.currentTimeMillis());
					dao.addToDoanhThu(bookID, soLuongBan, totalPrice, currentDate);

					JOptionPane.showMessageDialog(Trangbanhang.this, "Bán hàng thành công!");
					Object[] rowData = { bookID, soLuongBan, currentDate, totalPrice };
					doanhthuPanel.addRow(rowData);

					Trangchu.updateTableData();

					textField.setText("");
					textField_1.setText("");
					textField_2.setText("");
					textField_4.setText("");
					textField_5.setText("");
					dateChooser.setDate(null);
					ServerConnection.sendSoldBookToServer(bookID, soLuongBan, totalPrice);

				} else {
					JOptionPane.showMessageDialog(Trangbanhang.this, "Không đủ số lượng sách để bán.");
				}
			} else {
				JOptionPane.showMessageDialog(Trangbanhang.this, "Không tìm thấy sách với mã " + bookID);
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(Trangbanhang.this, "Dữ liệu không hợp lệ.");
		}
	}

	public JPanel getPanel() {
		return this;
	}
}
