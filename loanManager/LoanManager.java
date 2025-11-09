package loanManager;

import java.util.ArrayList;
import java.util.List;
import loanTicket.LoanTicket;

public class LoanManager {
    // - <list>(LoanTicket) loanTicketList
    private List<LoanTicket> loanTicketList;

    // Constructor
    public LoanManager() {
        this.loanTicketList = new ArrayList<>();
    }

    public LoanManager(List<LoanTicket> initialList) {
        this.loanTicketList = initialList;
    }

    // Phương thức thêm một phiếu mượn mới vào danh sách
    public void addLoanTicket(LoanTicket ticket) {
        if (ticket != null) {
            this.loanTicketList.add(ticket);
            System.out.println("Đã thêm phiếu mượn ID: " + ticket.getTicketID());
        }
    }

    // Phương thức hiển thị tất cả phiếu mượn trong danh sách
    public void showAllLoanTickets() {
        if (loanTicketList.isEmpty()) {
            System.out.println("Danh sách phiếu mượn hiện đang trống.");
            return;
        }
        
        System.out.println("\n--- DANH SÁCH TẤT CẢ PHIẾU MƯỢN (" + loanTicketList.size() + " phiếu) ---");
        for (LoanTicket ticket : loanTicketList) {
            ticket.showLoanTicket(); 
        }
        System.out.println("----------------------------------------------");
    }
}
