package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import model.Kho;
import model.Sachmodel;

public class SachDAO {
	private static final String URL = "jdbc:mysql://localhost:3306/admin";
	private static final String jdbcname = "root";
	private static final String jdbcpas = "W@2915djkq#";
	private final static String select = "SELECT * FROM Sach";
	private final String selectdh = "SELECT * FROM Donhang";
	private final static String selectk = "SELECT * FROM kho";

	private final String selectHinhAnh = "SELECT HinhAnh FROM Anhtruyen WHERE MS = ?";

	public static Connection getConnection() throws ClassNotFoundException {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(URL, jdbcname, jdbcpas);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	public static ResultSet getAllRecords() {
		ResultSet rs = null;
		try {
			Connection con = DriverManager.getConnection(URL, jdbcname, jdbcpas);
			Statement st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM kho");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public static List<Sachmodel> getAllBooks() {
		List<Sachmodel> sachList = new ArrayList<>();
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(select);
				ResultSet rs = preparedStatement.executeQuery()) {
			while (rs.next()) {
				int maSach = rs.getInt("MS");
				String tenSach = rs.getString("TenSach");
				String tacGia = rs.getString("TacGia");
				double gia = rs.getDouble("Gia");
				int sl = rs.getInt("Sl");
				Sachmodel sach = new Sachmodel(maSach, tenSach, tacGia, gia, sl);
				sachList.add(sach);
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return sachList;
	}
	public static boolean addUser(String username, String hashedPassword) {
        String query = "INSERT INTO users (username, hashed_password) VALUES (?, ?)";
        try (Connection con = DriverManager.getConnection(URL, jdbcname, jdbcpas);
        		PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean userExists(String username) {
        String query = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (Connection con = DriverManager.getConnection(URL, jdbcname, jdbcpas);
        		PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getPasswordHash(String username) {
        String query = "SELECT hashed_password FROM users WHERE username = ?";
        try (Connection con = DriverManager.getConnection(URL, jdbcname, jdbcpas);
        		PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("hashed_password");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

	public static List<Kho> getAllkho() {
		List<Kho> sachList = new ArrayList<>();
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(selectk);
				ResultSet rs = preparedStatement.executeQuery()) {
			while (rs.next()) {
				int maSach = rs.getInt("MS");
				int sl = rs.getInt("Slnv");
				String Nhacc = rs.getString("Nhacc");
				Date ngaynhap = rs.getDate("Ngaynhap");
				Kho sach = new Kho(maSach, sl, Nhacc, ngaynhap);
				sachList.add(sach);
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return sachList;
	}

	public List<Kho> getAllKho() {
		List<Kho> khoList = new ArrayList<>();
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(selectk);
				ResultSet rs = preparedStatement.executeQuery()) {

			while (rs.next()) {
				int ms = rs.getInt("MS");
				int slnv = rs.getInt("Slnv");
				String nhacc = rs.getString("Nhacc");
				Date ngaynhap = rs.getDate("Ngaynhap");
				Kho kho = new Kho(ms, slnv, nhacc, ngaynhap);
				khoList.add(kho);
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return khoList;
	}

	public String getHinhAnhByMaSach(int maSach) {
		String anh = null;
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(selectHinhAnh)) {
			preparedStatement.setInt(1, maSach);
			try (ResultSet rs = preparedStatement.executeQuery()) {
				if (rs.next()) {
					anh = rs.getString("HinhAnh");
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return anh;
	}

	public void insertOrder(Sachmodel sach) throws ClassNotFoundException {
		String sql = "INSERT INTO Donmua (MS, TenSach, TacGia, Gia, Sl) VALUES (?, ?, ?, ?,?) "
				+ "ON DUPLICATE KEY UPDATE TenSach = VALUES(TenSach), TacGia = VALUES(TacGia), Gia = VALUES(Gia), Sl=VALUES(Sl)";

		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, sach.getMaSach());
			pstmt.setString(2, sach.getTenSach());
			pstmt.setString(3, sach.getTacGia());
			pstmt.setDouble(4, sach.getGia());
			pstmt.setInt(5, sach.getSl());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static boolean addkho(Kho book) {
		try (Connection conn = DriverManager.getConnection(URL, jdbcname, jdbcpas);
				PreparedStatement pstmt = conn
						.prepareStatement("INSERT INTO kho (MS, Slnv, Nhacc, Ngaynhap) VALUES (?, ?, ?, ?) ")) {

			pstmt.setInt(1, book.getMS());
			pstmt.setInt(2, book.getSlnv());
			pstmt.setString(3, book.getNhacc());
			pstmt.setDate(4, book.getNgaynhap());
			int affectedRows = pstmt.executeUpdate();
			return affectedRows > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean addBook(Sachmodel book) {
		try (Connection conn = DriverManager.getConnection(URL, jdbcname, jdbcpas);
				PreparedStatement pstmt = conn
						.prepareStatement("INSERT INTO sach (MS, TenSach, TacGia, Gia,Sl) VALUES (?, ?, ?, ?,?)")) {

			pstmt.setInt(1, book.getMaSach());
			pstmt.setString(2, book.getTenSach());
			pstmt.setString(3, book.getTacGia());
			pstmt.setDouble(4, book.getGia());
			pstmt.setInt(5, book.getSl());
			int affectedRows = pstmt.executeUpdate();
			return affectedRows > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean deleteBook(int id) {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = DriverManager.getConnection(URL, jdbcname, jdbcpas);
			String query = "DELETE FROM Sach WHERE MS = ?";
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);

			int rowsDeleted = statement.executeUpdate();

			return rowsDeleted > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			// Đóng kết nối và tuyên bố
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public Sachmodel getBookDetailsByID(int bookID) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Sachmodel book = null;

		try {
			connection = DriverManager.getConnection(URL, jdbcname, jdbcpas);
			String query = "SELECT TenSach, Gia FROM Sach WHERE MS = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, bookID);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				String tenSach = resultSet.getString("TenSach");
				double gia = resultSet.getDouble("Gia");

				book = new Sachmodel(bookID, tenSach, gia);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {

				if (resultSet != null)
					resultSet.close();
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// Trả về đối tượng sách
		return book;
	}

	public void addToDoanhThu(int bookID, int quantity, double totalPrice, Date date) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DriverManager.getConnection(URL, jdbcname, jdbcpas);
			String sql = "INSERT INTO Doanhthu (MS, Sl, Tong, Ngayban) VALUES (?, ?, ?, ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, bookID);
			stmt.setInt(2, quantity);
			stmt.setDouble(3, totalPrice);
			stmt.setDate(4, date);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Object[]> getAllDoanhThu() {
		List<Object[]> data = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(URL, jdbcname, jdbcpas);
			stmt = conn.createStatement();
			String sql = "SELECT * FROM Doanhthu";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Object[] rowData = { rs.getInt("MS"), rs.getInt("Sl"), rs.getDate("Ngayban"), rs.getDouble("Tong") };
				data.add(rowData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	public void updateBookQuantity(int bookID, int newQuantity) {
		String query = "UPDATE sach SET Sl = ? WHERE MS = ?";
		try (Connection connection = DriverManager.getConnection(URL, jdbcname, jdbcpas);
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, newQuantity);
			statement.setInt(2, bookID);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Sachmodel> getAllOrders() {
		List<Sachmodel> donhangList = new ArrayList<>();
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(selectdh);
				ResultSet rs = preparedStatement.executeQuery()) {
			while (rs.next()) {
				int maSach = rs.getInt("MS");
				String tenSach = rs.getString("TenSach");
				String tacGia = rs.getString("TacGia");
				double gia = rs.getDouble("Gia");
				int sl = rs.getInt("Sl");
				Sachmodel donhang = new Sachmodel(maSach, tenSach, tacGia, gia, sl);
				donhangList.add(donhang);
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return donhangList;
	}
	public boolean updateKho(Kho kho) {
	    String query = "UPDATE kho SET Slnv = ?, Nhacc = ?, Ngaynhap = ? WHERE MS = ?";
	    try (Connection connection = getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setInt(1, kho.getSlnv());
	        preparedStatement.setString(2, kho.getNhacc());
	        preparedStatement.setDate(3, kho.getNgaynhap());
	        preparedStatement.setInt(4, kho.getMS());

	        int rowsAffected = preparedStatement.executeUpdate();
	        return rowsAffected > 0;
	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	public boolean updateBook(Sachmodel book) {
	    String updateQuery = "UPDATE Sach SET TenSach=?, TacGia=?, Gia=?, Sl=? WHERE MS=?";
	    try (Connection connection = SachDAO.getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
	        preparedStatement.setString(1, book.getTenSach());
	        preparedStatement.setString(2, book.getTacGia());
	        preparedStatement.setDouble(3, book.getGia());
	        preparedStatement.setInt(4, book.getSl());
	        preparedStatement.setInt(5, book.getMaSach());

	        int rowsAffected = preparedStatement.executeUpdate();
	        return rowsAffected > 0;
	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	        return false;
	    }
	}


	public void deleteOrder(int maSach) throws ClassNotFoundException {
		String query = "DELETE FROM Donhang WHERE MS = ?";
		try (Connection connection = DriverManager.getConnection(URL, jdbcname, jdbcpas);
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, maSach);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
