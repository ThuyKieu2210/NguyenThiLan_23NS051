package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDT {

	private static final String URL = "jdbc:mysql://localhost:3306/admin";
	private static final String jdbcname = "root";
	private static final String jdbcpas = "W@2915djkq#";

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

    public boolean addUser(String username, String hashedPassword) throws SQLException {
    	Connection con = DriverManager.getConnection(URL, jdbcname, jdbcpas);
        String query = "INSERT INTO users (username, hashed_password) VALUES (?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, hashedPassword);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean userExists(String username) throws SQLException {
    	Connection con = DriverManager.getConnection(URL, jdbcname, jdbcpas);
        String query = "SELECT 1 FROM users WHERE username = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getPasswordHash(String username) throws SQLException {
        Connection con = DriverManager.getConnection(URL, jdbcname, jdbcpas);
        String query = "SELECT hashed_password FROM users WHERE username = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("hashed_password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
