package Bill;

import java.util.*;

public class Bill {
    private String bill_ID;
    private double total;
    // Đổi tên biến sang ID để rõ ràng
    private String readerID; 
    private String librarianID;
    private String date;
    private ArrayList<BillDetail> billDetails = new ArrayList<>();


    public String getBill_ID() { return bill_ID; }
    public void setBill_ID(String bill_ID) { this.bill_ID = bill_ID; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    // Phương thức đã được sửa tên để đồng bộ với BillManager
    public String getReaderID() { return readerID; } 
    public void setReaderID(String readerID) { this.readerID = readerID; } 

    // Phương thức đã được sửa tên để đồng bộ với BillManager
    public String getLibrarianID() { return librarianID; } 
    public void setLibrarianID(String librarianID) { this.librarianID = librarianID; } 

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public ArrayList<BillDetail> getBillDetails() { return billDetails; }

    public Bill() {}

    // Sửa constructor để dùng ID
    public Bill(String bill_ID, String readerID, String librarianID, String date) {
        this.bill_ID = bill_ID;
        this.readerID = readerID;
        this.librarianID = librarianID;
        this.date = date;
        this.total = 0;
    }

    // Thêm chi tiết hóa đơn
    public void addBillDetail(BillDetail detail) {
        billDetails.add(detail);
        calculateTotal();
    }

    // Tính tổng tiền hóa đơn
    public void calculateTotal() {
        double sum = 0;
        for (BillDetail d : billDetails) {
            sum += d.getCost();
        }
        this.total = sum;
    }

    // Hiển thị thông tin hóa đơn
    public void showINFO() {
        System.out.println("\n===== HOA ĐON MUA SACH =====");
        System.out.println("Ma hoa đon: " + bill_ID);
        System.out.println("Ngay lap: " + date);
        System.out.println("Ma nguoi doc: " + this.readerID);
        System.out.println("Ma thu thu: " + this.librarianID);
        System.out.println("----------------------------------");
        
        System.out.println("DANH SACH SACH DUOC MUA:");
        if (billDetails.isEmpty()) {
            System.out.println("(Khong co chi tiet hoa don)");
        } else {
            System.out.printf("| %-15s | %-10s | %-15s | %-15s |\n", 
                "Ma sach", "So luong", "Gia/cuon", "Thanh tien");
            System.out.println("---------------------------------------------------------------------");
            for (BillDetail detail : billDetails) {
                detail.showINFO();
            }
            System.out.println("---------------------------------------------------------------------");
        }
        
        System.out.printf("TONG CONG: %,.0f VND\n", total);
        System.out.println("===============================");
    }
}