package loanTicket;

import java.time.LocalDate;
import java.util.List;

import loanDetail.LoanDetail;

public class LoanTicket {
    private String ticketID; // - ticketID
    private LocalDate dueDate; // - dueDate
    public String reader; // + (Reader) reader ;cập nhật sau
    public String librarian; // + (Librarian) librarian; cập nhật sau
    private LocalDate borrowDate; // - borrowDate

    // Mối quan hệ Composition/Aggregation từ LoanTicket đến LoanDetail
    private List<LoanDetail> loanDetails;

    // Constructor
    public LoanTicket() {}

    public LoanTicket(String ticketID, LocalDate dueDate, String reader, String librarian, LocalDate borrowDate,
            List<LoanDetail> loanDetails) {
        this.ticketID = ticketID;
        this.dueDate = dueDate;
        this.reader = reader;
        this.librarian = librarian;
        this.borrowDate = borrowDate;
        this.loanDetails = loanDetails;
    }

    // Getters và Setters
    public String getTicketID() { return this.ticketID; }
    public void setTicketID(String ticketID) { this.ticketID = ticketID; }
    public LocalDate getDueDate() { return this.dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public LocalDate getBorrowDate() { return this.borrowDate; }
    public void setBorrowDate(LocalDate borrowDate) { this.borrowDate = borrowDate; }

    // + showLoanTicket()
    public void showLoanTicket() {
        System.out.println("--- Chi tiết Phiếu Mượn ---");
        System.out.println("ID Phiếu: " + this.getTicketID());
        System.out.println("Người Mượn: " + (this.reader != null ? this.reader : "Chưa xác định"));
        System.out.println("Người Cho Mượn: " + (this.librarian != null ? this.librarian : "Chưa xác định"));
        System.out.println("Ngày Mượn: " + this.getBorrowDate()); 
        System.out.println("Ngày Hết Hạn: " + this.getDueDate());

        System.out.println("\nChi tiết Sách Mượn:");
        if (this.loanDetails != null && !this.loanDetails.isEmpty()) {
            for (LoanDetail detail : this.loanDetails) {
                detail.showLoanDetail(); // Gọi phương thức của LoanDetail
            }
        } else {
            System.out.println("Không có chi tiết mượn.");
        }
        System.out.println("-------------------------");
    }

}