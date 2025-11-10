package Person;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoanTicket {
    private String ticketID; 
    private LocalDate dueDate; 
    private Reader reader; 
    private Librarian librarian;
    private LocalDate borrowDate;

    
    private List<LoanDetail> loanDetails;

    // Constructor
    public LoanTicket() {       
        this.ticketID = "null";
        this.dueDate = null;
        this.reader = null;
        this.librarian = null;
        this.borrowDate = null;
        this.loanDetails = new ArrayList<>();
    }

    public LoanTicket(String ticketID, LocalDate dueDate, Reader reader, Librarian librarian, LocalDate borrowDate,
            List<LoanDetail> loanDetails) {
        if(ticketID == null || ticketID.trim().isEmpty())
            throw new IllegalArgumentException("TicketID must not be null or empty."); 

        if(dueDate == null) 
            throw new IllegalArgumentException("Due date must not be null.");
        // Ngày đáo hạn mới phải sau ngày đáo hạn cũ/ngày mượn
        if (this.dueDate != null && dueDate.isBefore(this.dueDate)) 
            throw new IllegalArgumentException("New due date must not be before the current due date.");
        
        if (reader == null)
            throw new IllegalArgumentException("Reader must not be null.");
        if (librarian == null)
            throw new IllegalArgumentException("Librarian must not be null.");
        if (borrowDate == null) 
            throw new IllegalArgumentException("Borrow date must not be null.");
        if (loanDetails == null || loanDetails.isEmpty()) 
            throw new IllegalArgumentException("Loan details list must not be null and must contain at least one item.");
        
        this.ticketID = ticketID;
        this.dueDate = dueDate;
        this.reader = reader;
        this.librarian = librarian;
        this.borrowDate = borrowDate;
        this.loanDetails = loanDetails;
    }

    // Getters & setters
    public String getTicketID() { return this.ticketID; }    
    public LocalDate getBorrowDate() { return this.borrowDate; }
    public Reader getReader() { return this.reader; }
    public Librarian getLibrarian() { return this.librarian; }
    public LocalDate getDueDate() { return this.dueDate; }
    public void setDueDate(LocalDate dueDate) {this.dueDate = dueDate;}

    // + showLoanTicket()
    public void showLoanTicket() {
        System.out.println("--- Loan Ticket Details ---");
        System.out.println("Ticket ID: " + this.getTicketID());
        System.out.println("Borrower: " + (this.reader != null ? this.reader.getName() : "Unknown"));
        System.out.println("Librarian: " + (this.librarian != null ? this.librarian.getName() : "Unknown"));
        System.out.println("Borrow Date: " + this.getBorrowDate()); 
        System.out.println("Due Date: " + this.getDueDate());

        System.out.println("\nLoan Details:");
        if (this.loanDetails != null && !this.loanDetails.isEmpty()) {
            for (LoanDetail detail : this.loanDetails) {
                detail.showLoanDetail(); 
            }
        } else {
            System.out.println("No loan details.");
        }
        System.out.println("-------------------------");
    }

}