package Bill;

import java.util.*;
import Person.*;

public class Bill {
    private String bill_ID;
    private double total;
    private String reader;
    private String librarian;
    private String date;
    private ArrayList<BillDetail> billDetails = new ArrayList<>();


    public String getBill_ID() { return bill_ID; }
    public void setBill_ID(String bill_ID) { this.bill_ID = bill_ID; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public String getReader() { return reader; }
    public void setReader(String reader) { this.reader = reader; }

    public String getLibrarian() { return librarian; }
    public void setLibrarian(String librarian) { this.librarian = librarian; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public ArrayList<BillDetail> getBillDetails() { return billDetails; }

    public Bill() {}

    public Bill(String bill_ID, String reader, String librarian, String date) {
        this.bill_ID = bill_ID;
        this.reader = reader;
        this.librarian = librarian;
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
        System.out.println("Ma nguoi doc: " + this.reader);
        System.out.println("Ma thu thu: " + this.librarian);
        System.out.println("----------------------------------");
        for (BillDetail detail : billDetails) {
            detail.showINFO();
        }
        System.out.println("TONG CONG: " + total);
        System.out.println("===============================");
    }
}
