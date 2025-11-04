package loanDetail;

import java.time.LocalDate;

public class LoanDetail  {
    private int quantity;    // - quantity
    public String item;       // - (Book) item; cập nhật sau
    private LocalDate actualReturnDate; // - actualReturnDate

    
    public LoanDetail() {}

    public LoanDetail(int quantity, String item, LocalDate actualReturnDate) {
        this.quantity = quantity;
        this.item = item;
        this.actualReturnDate = actualReturnDate;
    }

    public int getQuantity() { return this.quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public String getItem() { return this.item; }
    public void setItem(String item) { this.item = item; }
    public LocalDate getActualReturnDate() { return this.actualReturnDate; }
    public void setActualReturnDate(LocalDate actualReturnDate) { this.actualReturnDate = actualReturnDate; }

    // + showLoanDetail()
    public void showLoanDetail() {
        
        String itemName = (this.item != null) ? this.item : "Không rõ sách/vật phẩm";
        
        System.out.println("\t- Sách/Vật phẩm: " + itemName + " - Số lượng: " + this.quantity);
        if (this.actualReturnDate != null) {
           
            System.out.println("\t  Đã trả ngày: " + this.getActualReturnDate()); 
        } else {
            System.out.println("\t  Trạng thái: Chưa trả");
        }
    }

}