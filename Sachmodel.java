package model;

public class Sachmodel {
    private int maSach;
    private String tenSach;
    private String tacGia;
    private double gia;
    public int getSl() {
		return sl;
	}

	public void setSl(int sl) {
		this.sl = sl;
	}
	
	public Sachmodel(int maSach, String tenSach, double gia) {
		super();
		this.maSach = maSach;
		this.tenSach = tenSach;
		this.gia = gia;
	}

	private int sl;
    public Sachmodel() {
    }

    public Sachmodel(int maSach, String tenSach, String tacGia, double gia,int sl) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.tacGia = tacGia;
        this.gia = gia;
        this.sl=sl;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }
}
