package view;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.LineBorder;

public class Giaodien extends JFrame {
    private JPanel panel_1;
    private JPanel contentPanel;
    private JPanel currentPanel;
    private Socket socket;
    private PrintWriter out;
    private static Socket clientSocket;
    private String selectedButton;

    public Giaodien() {
        try {
            socket = new Socket("localhost", 8888); // Connect to the server
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        getContentPane().setFont(new Font("Times New Roman", Font.BOLD, 17));
        getContentPane().setBackground(new Color(255, 240, 245));
        getContentPane().setSize(800, 600);
        getContentPane().setLayout(null);

        panel_1 = new JPanel();
        panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel_1.setBackground(new Color(255, 218, 185));
        panel_1.setBounds(10, 87, 1, 365);
        panel_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openMenuBar();
            }
        });
        getContentPane().add(panel_1);
        panel_1.setLayout(null);

        JButton btnNewButton = new JButton("Trang chủ");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    switchPanel(new Trangchu().getPanel());
                });
            }
        });

        btnNewButton.setBounds(10, 42, 156, 38);
        panel_1.add(btnNewButton);
        btnNewButton.setBackground(new Color(244, 164, 96));
        btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 17));

        JButton btnNewButton_1 = new JButton("Quản lý kho");
        btnNewButton_1.setBounds(10, 126, 156, 38);
        panel_1.add(btnNewButton_1);
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    switchPanel(new QuanLyKho().getPanel());
                });
            }
        });
        btnNewButton_1.setBackground(new Color(244, 164, 96));
        btnNewButton_1.setFont(new Font("Times New Roman", Font.BOLD, 17));

        JButton btnNewButton_4 = new JButton("Doanh thu");
        btnNewButton_4.setBounds(10, 282, 156, 38);
        panel_1.add(btnNewButton_4);
        btnNewButton_4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    switchPanel(new Doanhthu().getPanel());
                });
            }
        });
        btnNewButton_4.setBackground(new Color(244, 164, 96));
        btnNewButton_4.setFont(new Font("Times New Roman", Font.BOLD, 17));

        JLabel lblNewLabel_2 = new JLabel("");
        lblNewLabel_2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                closeMenuBar();
            }
        });
        lblNewLabel_2.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(Giaodien.class.getResource("dong.jpg"))));
        lblNewLabel_2.setBounds(148, 0, 28, 31);
        panel_1.add(lblNewLabel_2);

        JButton btnNewButton_2 = new JButton("Trang bán hàng");
        btnNewButton_2.setBackground(new Color(244, 164, 96));
        btnNewButton_2.setFont(new Font("Times New Roman", Font.BOLD, 17));
        btnNewButton_2.setBounds(10, 203, 156, 38);
        panel_1.add(btnNewButton_2);
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    DefaultTableModel tableModel = new DefaultTableModel();
                    Doanhthu doanhthuPanel = new Doanhthu();
                    Trangbanhang trangbanhang = new Trangbanhang(tableModel, doanhthuPanel);
                    trangbanhang.setActionListener(action -> sendMessageToServer(action));
                    switchPanel(trangbanhang.getPanel());
                });
            }
        });

        JPanel panel_2 = new JPanel();
        panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel_2.setBackground(new Color(222, 184, 135));
        panel_2.setBounds(10, 26, 58, 63);
        getContentPane().add(panel_2);
        panel_2.setLayout(null);

        JLabel lblNewLabel_1 = new JLabel("New label");
        lblNewLabel_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openMenuBar();
            }
        });
        lblNewLabel_1.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(Giaodien.class.getResource("trangchu.jpg"))));
        lblNewLabel_1.setBounds(5, 5, 49, 47);
        panel_2.add(lblNewLabel_1);

        contentPanel = new JPanel(null);
        contentPanel.setBackground(new Color(255, 240, 245));
        contentPanel.setBounds(191, 28, 685, 424);
        getContentPane().add(contentPanel);

        Trangchu trangchu = new Trangchu();
        trangchu.setBounds(0, 0, 696, 425);
        trangchu.setBorder(new LineBorder(new Color(0, 0, 0)));
//        currentPanel = trangchu.getPanel();
//        currentPanel.setBounds(0, 0, contentPanel.getWidth(), contentPanel.getHeight());
//        contentPanel.add(currentPanel);

        this.setVisible(true);
        this.setSize(900, 513);
    }

    private void sendMessageToServer(String message) {
        out.println(message);
    }
    
    private void sendAndStart(String function) {
        SwingUtilities.invokeLater(() -> {
            start();
            if (!function.equals(selectedButton)) {
                addToLogs(selectedButton + " STOP");
                selectedButton = function;
                addToLogs(function.toUpperCase() + " START");
            }
            out.println(function + " START");
        });
    }
    
    private void switchPanel(JPanel newPanel) {
        if (currentPanel != null) {
            contentPanel.remove(currentPanel);
        }
        currentPanel = newPanel;
        currentPanel.setBounds(0, 0, contentPanel.getWidth(), contentPanel.getHeight());
        contentPanel.add(currentPanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    int width = 176;
    int height = 336;

    void openMenuBar() {
        new Thread(() -> {
            for (int i = 0; i < width; i++) {
                panel_1.setSize(i, height);
                try {
                    Thread.sleep(5);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Giaodien.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }

    public void start() {
//        
    }
    public static void addToLogs(String message) {
		System.out.printf("%s %s\n", ServerUI.formatter.format(new Date()), message);
	}

	private static class Listener implements Runnable {
		private BufferedReader in;
		@Override
		public void run() {
			try {
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				String read;
				for(;;) {
					read = in.readLine();
					if (read != null && !(read.isEmpty())) addToLogs(read);
				}
			} catch (IOException e) {
				return;
			}
		}

	}
        void closeMenuBar() {
        new Thread(() -> {
            for (int i = width; i > 0; i--) {
                panel_1.setSize(i, height);
                try {
                    Thread.sleep(5);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Giaodien.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        new Giaodien();
    }
}
