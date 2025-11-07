package Person;

import java.time.LocalDate;
import book.Book;


public class LoanDetail  {
    private int quantity;    
    private Book item;       
    private LocalDate actualReturnDate; 

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
    public Book getItem() { return this.item; }  
    public LocalDate getActualReturnDate() { return this.actualReturnDate; }
    public void setActualReturnDate(LocalDate actualReturnDate) { this.actualReturnDate = actualReturnDate; }

    // + showLoanDetail()
    public void showLoanDetail() {
        
        String itemName = (this.item != null) ? this.item.getName() : "Unknown book";
        
        System.out.println("\t- Book: " + itemName + " - Quantity: " + this.quantity);
        if (this.actualReturnDate != null) {
            System.out.println("\t  Returned on: " + this.getActualReturnDate());
        } else {
            System.out.println("\t  Status: Not returned");
        }
    }

}