package bill;

import book.Book;

public class BillDetail {
    private Book item;     
    private int quantity;  
    private double cost;   
 
    public Book getItem() { return item; }
    public void setItem(Book item) { this.item = item; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getCost() { return cost; }
    public void setCost(double cost) { this.cost = cost; }

 
    public BillDetail() {}

    public BillDetail(Book item, int quantity) {
        this.item = item;
        this.quantity = quantity;
        this.cost = item.getQuantity() * quantity; 
    }

    public void showINFO() {
        System.out.println("=== Thông tin chi tiết hóa đơn ===");
        System.out.println("Tên sách: " + item.getName());
        System.out.println("Tác giả: " + item.getAuthor());
        System.out.println("Số lượng: " + quantity);
        System.out.println("Thành tiền: " + cost);
        System.out.println("----------------------------------");
    }
}
