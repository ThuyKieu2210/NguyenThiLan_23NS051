package view;

import java.awt.BorderLayout;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.TextAreaOutputStream;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Color;

public class ServerUI extends JFrame implements ActionListener {	

	public static SimpleDateFormat formatter = new SimpleDateFormat("[hh:mm a]");
	private static HashMap<String, PrintWriter> connectedClients = new HashMap<>();
	private static final int MAX_CONNECTED = 50;
	private static int PORT;
	private static ServerSocket server,server2;
	private Socket sockett;
	private static volatile boolean exit = false;



	private JPanel contentPane;
	private JTextArea txtAreaLogs;
	private JButton btnStart;
	private JLabel lblChatServer;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerUI frame = new ServerUI();
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					SwingUtilities.updateComponentTreeUI(frame);
					//Logs
					System.setOut(new PrintStream(new TextAreaOutputStream(frame.txtAreaLogs)));
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public ServerUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 570, 400);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 222, 173));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblChatServer = new JLabel("QUẢN LÝ CỬA HÀNG");
		lblChatServer.setBounds(-40, 5, 426, 42);
		lblChatServer.setHorizontalAlignment(SwingConstants.CENTER);
		lblChatServer.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		contentPane.add(lblChatServer);

		btnStart = new JButton("START");
		btnStart.setBounds(10, 430, 343, 45);
		btnStart.setBackground(new Color(244, 164, 96));
		btnStart.addActionListener(this);
		btnStart.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		contentPane.add(btnStart);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 58, 343, 361);
		contentPane.add(scrollPane);

		txtAreaLogs = new JTextArea();
		txtAreaLogs.setBackground(new Color(47, 79, 79));
		txtAreaLogs.setForeground(Color.WHITE);
		txtAreaLogs.setLineWrap(true);
		scrollPane.setViewportView(txtAreaLogs);
		setLocation(10,10);
		setSize(375,524);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnStart) {
			if(btnStart.getText().equals("START")) {
				exit = false;
				getRandomPort();
				start();
				new dangki();
				btnStart.setText("STOP");
			}else {
				addToLogs("Server stopped...");
				exit = true;
				btnStart.setText("START");
			}
		}
		
		refreshUIComponents();
	}
	
	public void refreshUIComponents() {
		lblChatServer.setText("SERVER" + (!exit ? ": "+PORT:""));
	}

	public static void start() {
		new Thread(new ServerHandler()).start();
	}

	public static void stop() throws IOException {
		if (!server.isClosed()) server.close();
	}

	private static void broadcastMessage(String message) {
		for (PrintWriter p: connectedClients.values()) {
			p.println(message);
		}
	}
	public static void addToLogs(String message) {
		System.out.printf("%s %s\n", formatter.format(new Date()), message);
	}

	private static int getRandomPort() {
		int port = 8888;
		PORT = port;
		return port;
	}
	private static class ServerHandler implements Runnable{
		@Override
		public void run() {
	        try {
	        	server = new ServerSocket(PORT);
				addToLogs("Bat dau quan ly port: " + PORT);
				addToLogs("Dang cho ket noi...");
				while(!exit) {
					if (connectedClients.size() <= MAX_CONNECTED){
						new Thread(new ClientHandler(server.accept())).start();
					}
				}
	        } catch (Exception e) {
	            addToLogs("\nError occured: \n");
	            addToLogs(Arrays.toString(e.getStackTrace()));
	            addToLogs("\nExiting...");
	        }
	    }
	}
	
	
	private static class ClientHandler implements Runnable {
		private Socket socket;
		private PrintWriter out;
		
		private BufferedReader in;
		private String name;
		
		
		
		public ClientHandler(Socket socket) {
			this.socket = socket;
		}
		
		@Override
		public void run(){
			
			addToLogs("1 Client da ket noi: " + socket.getInetAddress());
			try {
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), true);

				
				
				
				for(;;) {

					
					name = in.readLine();
					if (name == null) {
						return;
					}
					synchronized (connectedClients) {
						if (!name.isEmpty() && !connectedClients.keySet().contains(name)) break;
						else out.println("INVALIDNAME");
					}
				}
				addToLogs(name.toUpperCase() + " START ");
				
				connectedClients.put(name, out);
				
				String message;
				while ((message = in.readLine()) != null && !exit) {
					if (!message.isEmpty()) {
						if (message.toLowerCase().equals("/quit")) break;
						addToLogs(String.format("[%s] %s", name, message));
					}
				}
				
				
			} catch (Exception e) {
				addToLogs(e.getMessage());
			} finally {
				if (name != null) {
					addToLogs(name + " da thoat");
					connectedClients.remove(name);
				}
			}
		}
	}
}
