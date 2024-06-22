package DAO;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import model.Kho;
import model.Sachmodel;
import view.Doanhthu;

public class ServerConnection {
	private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8888;
    private  PrintWriter out;
    public static void sendBookInfoToServer(Sachmodel book) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            out.println("THEM SACH"); 
            out.println("Ma sach: "+book.getMaSach()+ ", Ten sach: "+book.getTenSach()+", So luong: "+book.getSl());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void sendBookUpdateToServer(Sachmodel book,String changes) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
        	out.println("CAP NHAT SACH");
        	 out.println("\n" + changes);
        	
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void sendDeletedBookToServer(Sachmodel deletedBook) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            out.println("XOA SACH");
            out.println("Da xoa sach co ma sach: " + deletedBook.getMaSach());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void sendSoldBookToServer(int bookID, int quantity, double totalPrice) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            out.println("BAN SACH"); 
            out.println("Ma sach: " + bookID + ", So luong: " + quantity + ", Thanh toan: " + totalPrice);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void sendDoanhthu(double thu) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
        	 out.println("DOANH THU");
        	 out.println("Doanh thu: "+thu);
        	
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void sendUpdatedStockToServer(Kho stock) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            out.println("CAP NHAT KHO");
            out.println("Thong tin cap nhat:");
            out.println("Ma sach: " + stock.getMS());
            out.println("So luong: " + stock.getSlnv());
            out.println("Nha cung cap: " + stock.getNhacc());
            out.println("Ngay nhap: " + stock.getNgaynhap());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendNewStockToServer(Kho stock) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            out.println("THEM KHO");
            out.println("Ma sach: " + stock.getMS() + ", So luong: " + stock.getSlnv() + ", Nha cung cap: " + stock.getNhacc() + ", Ngay nhap: " + stock.getNgaynhap());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
