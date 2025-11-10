package loanDetail;

import java.time.LocalDate;

import book.Book;


public class LoanDetail  {
    private int quantity;    // - quantity
    public Book item;       // - (Book) item; cập nhật sau
    private LocalDate actualReturnDate; // - actualReturnDate

    // Constructor
    public LoanDetail() {
        this.quantity = 0;
        this.item = null;
        this.actualReturnDate = null;
    }

    public LoanDetail(int quantity, Book item, LocalDate actualReturnDate) {
        if (quantity <= 0) 
            throw new IllegalArgumentException("Quantity must be a positive number.");    
        if (item == null ) 
            throw new IllegalArgumentException("Item (Book) must not be null or empty.");     
        if (actualReturnDate != null && actualReturnDate.isAfter(LocalDate.now())) 
            throw new IllegalArgumentException("Actual return date cannot be in the future.");
        
        this.quantity = quantity;
        this.item = item;
        this.actualReturnDate = actualReturnDate;
    }

    // Getters và Setters
    public int getQuantity() { return this.quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public Book getItem() { return this.item; }
    public void setItem(Book item) { this.item = item; }
    public LocalDate getActualReturnDate() { return this.actualReturnDate; }
    public void setActualReturnDate(LocalDate actualReturnDate) { this.actualReturnDate = actualReturnDate; }

    // + showLoanDetail()
    public void showLoanDetail() {
        
        String itemName = (this.item != null) ? this.item.getName() : "Không rõ sách/vật phẩm";
        
        System.out.println("\t- Sách/Vật phẩm: " + itemName + " - Số lượng: " + this.quantity);
        if (this.actualReturnDate != null) {
        
            System.out.println("\t  Đã trả ngày: " + this.getActualReturnDate()); 
        } else {
            System.out.println("\t  Trạng thái: Chưa trả");
        }
    }

}