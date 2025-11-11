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
        BookManager BM = new BookManager();
        // Giả định BM.getAllBooks() trả về List<Book>
        for( Book b : BM.getAllBooks()){
            if( b.getBookID().equals(bookID)){
                return b.getPrice();
            }
        }
        return 0.0; // Sửa lỗi: Phải có return 0.0 nếu không tìm thấy sách
    }


    public void showINFO() {
        double price = GiaSach(bookID);
        System.out.println("=== Thông tin chi tiết hóa đơn ===");
        System.out.println("Ma sach: " + this.bookID);
        System.out.println("So luong: " + this.quantity);
        System.out.println("Gia/cuon: " + price); 
        System.out.println("Tong tien chi tiet: " + this.cost); 
        System.out.println("----------------------------------");
    }
}