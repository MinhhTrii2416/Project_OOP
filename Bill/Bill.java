package Bill;

import java.util.*;
import Person.Reader;
import Person.Librarian;

public class Bill {
    private String bill_ID;
    private double total;
    private Reader reader;
    private Librarian librarian;
    private String date;
    private ArrayList<BillDetail> billDetails = new ArrayList<>();


    public String getBill_ID() { return bill_ID; }
    public void setBill_ID(String bill_ID) { this.bill_ID = bill_ID; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public Reader getReader() { return reader; }
    public void setReader(Reader reader) { this.reader = reader; }

    public Librarian getLibrarian() { return librarian; }
    public void setLibrarian(Librarian librarian) { this.librarian = librarian; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public ArrayList<BillDetail> getBillDetails() { return billDetails; }

    public Bill() {}

    public Bill(String bill_ID, Reader reader, Librarian librarian, String date) {
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
        System.out.println("\n===== HÓA ĐƠN MUA SÁCH =====");
        System.out.println("Mã hóa đơn: " + bill_ID);
        System.out.println("Ngày lập: " + date);
        System.out.println("Người đọc: " + (reader != null ? reader.getName() : "Không rõ"));
        System.out.println("Thủ thư: " + (librarian != null ? librarian.getName() : "Không rõ"));
        System.out.println("----------------------------------");
        for (BillDetail detail : billDetails) {
            detail.showINFO();
        }
        System.out.println("TỔNG CỘNG: " + total);
        System.out.println("===============================");
    }
}
