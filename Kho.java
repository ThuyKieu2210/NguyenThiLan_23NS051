package model;

import java.sql.Date;

public class Kho {


    private int MS;
    private int Slnv;
    private String Nhacc;
    private Date Ngaynhap;

    // Getters and setters
    public int getMS() {
        return MS;
    }

    public void setMS(int MS) {
        this.MS = MS;
    }

    public int getSlnv() {
        return Slnv;
    }

    public void setSlnv(int slnv) {
        Slnv = slnv;
    }

    public String getNhacc() {
        return Nhacc;
    }

    public void setNhacc(String nhacc) {
        Nhacc = nhacc;
    }

    public Date getNgaynhap() {
        return Ngaynhap;
    }

    public void setNgaynhap(Date ngaynhap) {
        Ngaynhap = ngaynhap;
    }

	public Kho(int mS, int slnv, String nhacc, Date ngaynhap) {
		super();
		MS = mS;
		Slnv = slnv;
		Nhacc = nhacc;
		Ngaynhap = ngaynhap;
	}
}

