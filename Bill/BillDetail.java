package Bill;

import book.*;
import Person.*;

public class BillDetail {
    private String bookID;     
    private int quantity;  
    private double cost;   
 
    public String getItem() { return bookID; }
    public void setItem(String bookID) { this.bookID = bookID; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getCost() { return cost; }
    public void setCost(double cost) { this.cost = cost; }

 
    public BillDetail() {}

    public BillDetail(String bookID, int quantity) {
        this.bookID = bookID;
        this.quantity = quantity;
        this.cost = this.quantity * GiaSach(bookID);
    }

    private double GiaSach(String bookID){
        BookManager BM = new BookManager();
        for( Book b : BM.getAllBooks()){
            if( b.getBookID().equals(bookID)){
                return b.getPrice();
            }
        }
    }


    public void showINFO() {
        System.out.println("=== Thông tin chi tiết hóa đơn ===");
        System.out.println("Ma sach: " + this.bookID);
        System.out.println("So luong: " + this.quantity);
        System.out.println("Gia: " + this.GiaSach(bookID));
        System.out.println("Tong tien: " + this.cost);
        System.out.println("----------------------------------");
    }
}
