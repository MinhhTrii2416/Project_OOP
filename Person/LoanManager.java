
package Person; 

import book.Book;

import java.util.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LoanManager {
    // - <list>(LoanTicket) loanTicketList
    private ArrayList<LoanTicket> list = new ArrayList<>();
    Scanner sc = new Scanner(System.in);
    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // set & get
    public void setList(ArrayList<LoanTicket> list){
        this.list = list;
    }
    public ArrayList<LoanTicket> getList(){
        return this.list;
    }
    
    // Constructor
    public LoanManager() {
        this.list = loadListLoanTicket();
    }
    

    // hàm lấy danh sách chi tiết phiếu mượn theo ID
    private List<LoanDetail> loadLoanDetailsByTicketID(String ticketID) {
        List<LoanDetail> details = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader("./data/LoanDetail.csv"))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                
                String csvTicketID = data[0];
                // Chỉ lấy details của ticketID này
                if (csvTicketID.equals(ticketID)) {
                    String bookID = data[1];
                    int quantity = Integer.parseInt(data[3]);
                    
                    // Xử lý actualReturnDate (có thể rỗng)
                    LocalDate returnDate = (data.length > 4 && !data[4].isEmpty()) 
                                        ? LocalDate.parse(data[4]) 
                                        : null;
                    
                    // Tìm Book object theo bookID
                    Book book = findBookByID(bookID);
                    
                    // Tạo LoanDetail
                    LoanDetail detail = new LoanDetail(quantity, book, returnDate);
                    details.add(detail);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();    
        }
        
        return details;
    }


    // Hàm tìm Book theo ID (cần có BookManager)
    private Book findBookByID(String bookID) {
        // TODO: Implement BookManager để tìm sách
        // Tạm thời return null, cần tích hợp với BookManager sau
        return null;
    }
    
    // Hàm tìm Reader theo ID
    private Reader findReaderByID(String readerID) {
        try (BufferedReader br = new BufferedReader(new FileReader("./data/Reader.csv"))) {
            br.readLine(); 
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(readerID)) {
                    // readerID,Name,gender,address,phoneNumber,email
                    return new Reader(data[0], data[1], data[2], data[3], data[4], data[5]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Hàm tìm Librarian theo ID
    private Librarian findLibrarianByID(String librarianID) {
        try (BufferedReader br = new BufferedReader(new FileReader("./data/Librarian.csv"))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[5].equals(librarianID)) {
                    // Name,gender,phoneNumber,email,address,librarianID,shift,salary,password
                    return new Librarian(data[0], data[1], data[2], data[3], data[4], 
                                       data[5], data[6], Double.parseDouble(data[7]), data[8]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Hàm load danh sách phiếu mượn từ file (cần implement)
    private ArrayList<LoanTicket> loadListLoanTicket() {
        ArrayList<LoanTicket> tickets = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader("./data/LoanTicket.csv"))){
            br.readLine();
            String line;
            while( (line = br.readLine()) != null ){
                String[] data = line.split(",");
                // ticketID,readerID,librarianID,borrowDate,dueDate,status
                
                // Tìm Reader và Librarian objects
                Reader reader = findReaderByID(data[1]);
                Librarian librarian = findLibrarianByID(data[2]);
                
                // Load LoanDetails
                List<LoanDetail> loanDetails = loadLoanDetailsByTicketID(data[0]);
                
                // Tạo LoanTicket với objects (không phải String)
                LoanTicket ticket = new LoanTicket(
                    data[0],                    // ticketID
                    LocalDate.parse(data[4]),   // dueDate
                    reader,                     // Reader object
                    librarian,                  // Librarian object
                    LocalDate.parse(data[3]),   // borrowDate
                    loanDetails                 // loanDetails
                );
                tickets.add(ticket);
            }
        }
        catch( IOException e){
            e.printStackTrace();
        } 
        return tickets;
    }

    // menu
    public void menu(){
        int choose = 0;
        do{
            System.out.println("---------------Menu LoanManager------------------");
            System.out.println("-             1.In danh sach phieu muon.        -");
            System.out.println("-             2.Tim thong tin phieu muon.       -");
            System.out.println("-             3.Tao phieu muon moi.             -");
            System.out.println("-             4.Xoa phieu muon.                 -");
            System.out.println("-             0. Thoat.                         -");
            System.out.println("-------------------------------------------------");
            System.out.print("Nhap vao lua chon: ");
            if (sc.hasNextInt()) {
                choose = sc.nextInt();
            } else {
                System.out.println("Du lieu nhap khong hop le. Hay nhap lai!");
                sc.next(); // Clear invalid input
                continue;
            }
            
            switch(choose){
                case 1: showList(list); break;
                case 2: search(); break;
                case 3: add(); break;
                case 4: remove(); break;
                case 0: System.out.println("Thoat chuc nang quan li phieu muon!\n"); break;
                default:
                    System.out.println("Khon co lua chon ban nhap hay nhap lai!");
            }
        }while(choose != 0 );
    }

    // 1. Hàm in danh sách phiếu mượn (showList)
    public void showList(List<LoanTicket> list1) {
        System.out.println("\n--- DANH SÁCH PHIẾU MƯỢN ---");
        System.out.printf("| %-10s | %-20s | %-20s | %-20s | %-20s |\n",
            "TicketID", "Borrower", "Librarian", "BorrowDate", "DueDate");
        System.out.println("--------------------------------------------------------------------------------------------------------------");

        if (list1.isEmpty()) {
            System.out.println("| Không có phiếu mượn nào trong danh sách.                                                                 |");
        } else {
            for (LoanTicket t : list1) {
                String borrowerName = (t.getReader() != null) ? t.getReader().getName() : "N/A";
                String librarianName = (t.getLibrarian() != null) ? t.getLibrarian().getName() : "N/A";

                System.out.printf("| %-10s | %-20s | %-20s | %-20s | %-20s |\n",
                    t.getTicketID(),
                    borrowerName,
                    librarianName,
                    t.getBorrowDate(),
                    t.getDueDate()
                );
            }
        }
        System.out.println("--------------------------------------------------------------------------------------------------------------");
    }

    // 2. Hàm tìm thông tin phiếu mượn (search)
    public void search(){
        sc.nextLine(); // Clear buffer
        String ticketID;
        System.out.print("Hay nhap vao ID phieu muon can tim: ");
        ticketID = sc.nextLine();
        
        LoanTicket foundTicket = null;
        for( LoanTicket t: list){
            if(t.getTicketID().equalsIgnoreCase(ticketID)){
                foundTicket = t;
                break;
            }
        }
        
        if(foundTicket != null){
            System.out.println("---------------THONG TIN PHIEU MUON-------------");
            // Sử dụng hàm showLoanTicket đã định dạng đẹp mắt trước đó
            foundTicket.showLoanTicket(); 
        } else {
            System.out.println("Khong tim thay phieu muon nao co ID nay!");
        }
    }

    // 3. Hàm thêm phiếu mượn mới (add)
    public void add(){
        sc.nextLine(); // Clear buffer
        System.out.println("\n--- THÊM PHIẾU MƯỢN MỚI ---");
        
        // TODO: Implement logic để tạo phiếu mượn mới
        // - Nhập thông tin reader, librarian, books
        // - Tạo LoanTicket object
        // - Thêm vào list
        // - Ghi vào file
        
        System.out.println("Chức năng đang được phát triển!");
    }

    // 4. Hàm xóa phiếu mượn (remove)
    public void remove(){
        sc.nextLine(); // Clear buffer
        String ticketID;
        System.out.print("Hay nhap ID phieu muon ban muon xoa: ");
        ticketID = sc.nextLine();
        
        LoanTicket ticketToRemove = null;
        for(LoanTicket t: list){
            if(t.getTicketID().equalsIgnoreCase(ticketID)){
                ticketToRemove = t;
                break;
            }
        }
        
        if(ticketToRemove == null){
            System.out.println("Khong tim thay phieu muon nao co ID nay!");
            return;
        }

        // Hiển thị thông tin trước khi xóa
        System.out.println("----------------THONG TIN PHIEU MUON---------------------");
        ticketToRemove.showLoanTicket();
        
        int xac_nhan = -1;
        while(xac_nhan != 0){
            System.out.print("Neu muon XOA phieu muon nay hay bam 1, bam 0 neu muon huy: ");
            if (sc.hasNextInt()) {
                xac_nhan = sc.nextInt();
            } else {
                System.out.println("Du lieu nhap khong hop le. Hay nhap lai!");
                sc.next();
                continue;
            }
            
            switch(xac_nhan){
                case 0:
                    System.out.println("Xac nhan huy xoa phieu muon!");
                    return;
                case 1:
                    list.remove(ticketToRemove);
                    // updateLoanTicket(); // Cần uncomment khi có hàm ghi file
                    System.out.println("Xac nhan xoa phieu muon " + ticketID + " khoi danh sach!");
                    return;
                default: 
                    System.out.println("Loi! Hay nhap lai!");
            }
        }
    }

   
     
}
