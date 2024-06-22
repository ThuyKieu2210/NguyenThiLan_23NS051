package view;

import javax.swing.*;

import DAO.SachDAO;

import java.awt.*;
import model.Sachmodel;

public class Chitietsach extends JFrame {
	private JLabel maSachLabel;
	private JLabel anhLabel;
	private JLabel tenSachLabel;
	private JLabel tacGiaLabel;
	private JLabel giaLabel;

	public Chitietsach(Sachmodel sach) {
		getContentPane().setBackground(new Color(255, 250, 205));
		setBackground(new Color(240, 255, 240));
		setTitle("Chi Tiết Sách");
		setSize(467, 277);
		anhLabel = new JLabel();
		anhLabel.setBounds(23, 11, 163, 225);
		SachDAO sachDAO = new SachDAO();
		String anhPath = sachDAO.getHinhAnhByMaSach(sach.getMaSach());

		if (anhPath != null) {
			ImageIcon imageIcon = new ImageIcon(getClass().getResource(anhPath));
			Image img = imageIcon.getImage();
			Image newImg = img.getScaledInstance(100, 150, java.awt.Image.SCALE_SMOOTH);
			anhLabel.setIcon(new ImageIcon(newImg));
		} else {
			anhLabel.setText("No Image");
		}

		tenSachLabel = new JLabel("Tên Sách: " + sach.getTenSach());
		tenSachLabel.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		tenSachLabel.setBounds(196, 64, 331, 53);
		tacGiaLabel = new JLabel("Tác Giả: " + sach.getTacGia());
		tacGiaLabel.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		tacGiaLabel.setBounds(196, 112, 331, 53);
		giaLabel = new JLabel("Giá: " + sach.getGia());
		giaLabel.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		giaLabel.setBounds(196, 165, 331, 53);
		getContentPane().setLayout(null);

		maSachLabel = new JLabel("Mã Sách: " + sach.getMaSach());
		maSachLabel.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		maSachLabel.setBounds(196, 11, 331, 53);

		getContentPane().add(maSachLabel);
		getContentPane().add(anhLabel);
		getContentPane().add(tenSachLabel);
		getContentPane().add(tacGiaLabel);
		getContentPane().add(giaLabel);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
