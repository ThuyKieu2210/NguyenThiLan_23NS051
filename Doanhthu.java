package view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import DAO.SachDAO;
import DAO.ServerConnection;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Doanhthu extends JPanel {
	private JTable table;
	private DefaultTableModel model;
	private JTextField textField;
	double totalRevenue;
	
	public Doanhthu() {
		setBackground(new Color(255, 228, 196));

		model = new DefaultTableModel();
		model.addColumn("Mã Sách");
		model.addColumn("Số Lượng");
		model.addColumn("Ngày Mua");
		model.addColumn("Thành Tiền");
		setLayout(null);

		table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 84, 637, 259);
		add(scrollPane);

		JLabel lblNewLabel = new JLabel("DOANH THU BÁN HÀNG");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
		lblNewLabel.setBounds(170, 11, 428, 46);
		add(lblNewLabel);

		textField = new JTextField();
		textField.setBounds(295, 374, 235, 28);
		add(textField);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("Tổng doanh thu:");
		btnNewButton.setBackground(new Color(244, 164, 96));
		btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnNewButton.setBounds(119, 373, 151, 28);
		add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calculateTotalRevenue();
				ServerConnection.sendDoanhthu(totalRevenue);
			}
		});
		loadDataFromDatabase();
	}

	private void loadDataFromDatabase() {
		SachDAO dao = new SachDAO();
		List<Object[]> data = dao.getAllDoanhThu();
		for (Object[] rowData : data) {
			model.addRow(rowData);
		}
	}

	public void calculateTotalRevenue() {
		totalRevenue = 0;
		for (int i = 0; i < table.getRowCount(); i++) {
			double revenue = Double.parseDouble(table.getValueAt(i, 3).toString());
			totalRevenue += revenue;
		}
		textField.setText(String.valueOf(totalRevenue));
	}

	public void addRow(Object[] rowData) {
		model.addRow(rowData);
	}

	public JPanel getPanel() {
		return this;
	}
}
