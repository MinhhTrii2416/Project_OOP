package Bill;

import Person.BookManager; // Đổi import thành Person.BookManager
import book.Book; // Giữ nguyên import Book
import Person.ReaderManager; 
import Person.LibrarianManager;
// Bỏ Person.* vì đã import cụ thể

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
        double price = GiaSach(bookID); // Lấy giá sách
        this.cost = this.quantity * price; // Tính tổng tiền
    }

    // Hàm lấy giá sách, trả về 0 nếu không tìm thấy
    private double GiaSach(String bookID){
        Book.resetAllCounters();
        BookManager BM = new BookManager();
        // Tìm sách theo ID và lấy giá
        for( Book b : BM.getAllBooks()){
            if( b.getBookID().equals(bookID)){
                return b.getPrice();
            }
        }
        return 0.0; // Trả về 0 nếu không tìm thấy sách
    }


    public void showINFO() {
        double price = GiaSach(bookID);
        System.out.printf("| %-15s | %-10d | %,15.0f | %,15.0f |\n",
            this.bookID, this.quantity, price, this.cost);
    }
}