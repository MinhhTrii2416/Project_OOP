package loan;

import book.Book;
import book.BookManager;
import dataService.DataService;

import java.util.*;

import Person.Librarian;
import Person.Reader;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class LoanManager implements DataService {
    // - <list>(LoanTicket) loanTicketList
    private ArrayList<LoanTicket> list = new ArrayList<>();
    private BookManager bookManager; // Thêm BookManager
    Scanner sc = new Scanner(System.in);
    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // set & get
    public void setList(ArrayList<LoanTicket> list) {
        this.list = list;
    }

    public ArrayList<LoanTicket> getList() {
        return this.list;
    }

    // Constructor
    public LoanManager() {
        this.bookManager = new BookManager();
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
                    int quantity = Integer.parseInt(data[2]); 

                    // Xử lý actualReturnDate (có thể rỗng)
                    LocalDate returnDate = (data.length > 3 && !data[3].isEmpty())
                            ? LocalDate.parse(data[3]) 
                            : null;

                    // Tìm Book object theo bookID
                    Book book = findBookByID(bookID);

                    if(book == null) {
                        System.out.println("Read book from loan detail is null");
                    }

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

    // Hàm tìm Book theo ID (sử dụng BookManager)
    private Book findBookByID(String bookID) {
        return bookManager.findBookByID(bookID);
    }

    // Hàm tìm Reader theo ID
    private Reader findReaderByID(String readerID) {
        try (BufferedReader br = new BufferedReader(new FileReader("./data/Reader.csv"))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
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

    // Tìm Librarian theo ID
    private Librarian findLibrarianByID(String librarianID) {
        try (BufferedReader br = new BufferedReader(new FileReader("./data/Librarian.csv"))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";"); // Sửa từ "," thành ";"
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

    // Hàm load danh sách phiếu mượn từ file
    private ArrayList<LoanTicket> loadListLoanTicket() {
        ArrayList<LoanTicket> tickets = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("./data/LoanTicket.csv"))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                // ticketID,readerID,librarianID,borrowDate,dueDate

                // Tìm Reader và Librarian objects
                Reader reader = findReaderByID(data[1]);
                Librarian librarian = findLibrarianByID(data[2]);

                // Load LoanDetails
                List<LoanDetail> loanDetails = loadLoanDetailsByTicketID(data[0]);

                // Tạo LoanTicket với objects (không phải String)
                LoanTicket ticket = new LoanTicket(
                        data[0], // ticketID
                        LocalDate.parse(data[4]), // dueDate
                        reader, // Reader object
                        librarian, // Librarian object
                        LocalDate.parse(data[3]), // borrowDate
                        loanDetails // loanDetails
                );
                tickets.add(ticket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    // Hàm ghi danh sách phiếu mượn vào file LoanTicket.csv
    public void updateLoanTicket() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("./data/LoanTicket.csv"))) {
            // Ghi header
            bw.write("ticketID,readerID,librarianID,borrowDate,dueDate\n");

            for (LoanTicket t : list) {
                String line = String.format("%s,%s,%s,%s,%s\n",
                        t.getTicketID(),
                        (t.getReader() != null) ? t.getReader().getReaderID() : "", // Lấy ID
                        (t.getLibrarian() != null) ? t.getLibrarian().getLibrarianID() : "", // Lấy ID
                        t.getBorrowDate().format(DATE_FORMAT),
                        t.getDueDate().format(DATE_FORMAT));
                bw.write(line);
            }
            // System.out.println("Cập nhật danh sách phiếu mượn thành công.");
        } catch (IOException e) {
            System.err.println("Lỗi khi ghi file LoanTicket.csv: " + e.getMessage());
        }
    }

    // Hàm ghi danh sách chi tiết phiếu mượn vào file LoanDetail.csv
    public void updateLoanDetail() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("./data/LoanDetail.csv"))) {
            // Ghi header (bỏ cột bookName)
            bw.write("ticketID,bookID,quantity,actualReturnDate\n");

            for (LoanTicket ticket : list) {
                String ticketID = ticket.getTicketID();
                for (LoanDetail detail : ticket.getLoanDetails()) {
                    String actualReturnDateStr = (detail.getActualReturnDate() != null)
                            ? detail.getActualReturnDate().format(DateTimeFormatter.ISO_LOCAL_DATE)
                            : "";

                    // Bỏ cột bookName
                    String line = String.format("%s,%s,%d,%s\n",
                            ticketID,
                            (detail.getBook() != null) ? detail.getBook().getBookID() : "",
                            detail.getQuantity(),
                            actualReturnDateStr);
                    bw.write(line);
                }
            }
            // System.out.println("Cập nhật danh sách chi tiết mượn thành công.");
        } catch (IOException e) {
            System.err.println("Lỗi khi ghi file LoanDetail.csv: " + e.getMessage());
        }
    }

    // menu
    public void menu() {
        int choose = 0;
        do {
            System.out.println("---------------Menu LoanManager------------------");
            System.out.println("-             1.In danh sach phieu muon.        -");
            System.out.println("-             2.Tim thong tin phieu muon.       -");
            System.out.println("-             3.Tao phieu muon moi.             -");
            System.out.println("-             4.Xoa phieu muon.                 -");
            System.out.println("-             5.Tra sach.                       -");
            System.out.println("-             6.Xem danh sach phieu phat.       -");
            System.out.println("-             7.Dong phat.                      -");
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

            switch (choose) {
                case 1:
                    showList();
                    break;
                case 2:
                    search();
                    break;
                case 3:
                    add();
                    break;
                case 4:
                    remove();
                    break;
                case 5:
                    TraSach();
                    break;
                case 6:
                    viewFineList();
                    break;
                case 7:
                    payFine();
                    break;
                case 0:
                    // Reset Book ID counters khi thoát
                    Book.resetAllCounters();
                    System.out.println("Thoat chuc nang quan li phieu muon!\n");
                    break;
                default:
                    System.out.println("Khon co lua chon ban nhap hay nhap lai!");
            }
        } while (choose != 0);
    }

    // 1. Hàm in danh sách phiếu mượn (showList)
    public void showList() {
        System.out.println("\n--- DANH SÁCH PHIẾU MƯỢN ---");
        System.out.printf("| %-10s | %-20s | %-20s | %-20s | %-20s |\n",
                "TicketID", "Borrower", "Librarian", "BorrowDate", "DueDate");
        System.out.println(
                "--------------------------------------------------------------------------------------------------------------");

        if (list.isEmpty()) {
            System.out.println(
                    "| Không có phiếu mượn nào trong danh sách.                                                                 |");
        } else {
            for (LoanTicket t : list) {
                String borrowerName = (t.getReader() != null) ? t.getReader().getName() : "N/A";
                String librarianName = (t.getLibrarian() != null) ? t.getLibrarian().getName() : "N/A";

                System.out.printf("| %-10s | %-20s | %-20s | %-20s | %-20s |\n",
                        t.getTicketID(),
                        borrowerName,
                        librarianName,
                        t.getBorrowDate(),
                        t.getDueDate());
            }
        }
        System.out.println(
                "--------------------------------------------------------------------------------------------------------------");
    }

    // 2. Hàm tìm thông tin phiếu mượn (search)
    public void search() {
        sc.nextLine(); // Clear buffer
        String ticketID;
        System.out.print("Hay nhap vao ID phieu muon can tim: ");
        ticketID = sc.nextLine();

        LoanTicket foundTicket = null;
        for (LoanTicket t : list) {
            if (t.getTicketID().equalsIgnoreCase(ticketID)) {
                foundTicket = t;
                break;
            }
        }

        if (foundTicket != null) {
            System.out.println("---------------THONG TIN PHIEU MUON-------------");
            foundTicket.showLoanTicket();
        } else {
            System.out.println("Khong tim thay phieu muon nao co ID nay!");
        }
    }

    // hàm tự tạo mã phiếu mượn mới
    private String idNew(){
          if (list.isEmpty()) {
            return "LT_001"; // Nếu chưa có phiếu mượn nào, bắt đầu từ LT_001
        }
        
        // Tìm số thứ tự lớn nhất hiện có
        int maxNumber = 0;
        for (LoanTicket ticket : list) {
            String id = ticket.getTicketID();
            // Lấy phần số từ mã (bỏ chữ "LT_")
            if (id != null && id.startsWith("LT_") && id.length() > 3) {
                try {
                    int number = Integer.parseInt(id.substring(3)); // Bỏ "LT_"
                    if (number > maxNumber) {
                        maxNumber = number;
                    }
                } catch (NumberFormatException e) {
                    // Bỏ qua nếu format không đúng
                }
            }
        }
        
        // Tạo mã mới = số lớn nhất + 1
        int newNumber = maxNumber + 1;
        return String.format("LT_%03d", newNumber); // Format: LT_001, LT_002,...
    } 

    // 3. Hàm thêm phiếu mượn mới (add)
    public void add() {
        sc.nextLine(); // Clear buffer
        System.out.println("\n--- THEM PHIEU MUON MOI ---");

        // 1. Nhập và kiểm tra ID phiếu mượn
        String ticketID = idNew();

        // 2. Nhập và kiểm tra Reader
        Reader reader = null;
        String readerID;
        do {
            System.out.print("Nhap ID doc gia: ");
            readerID = sc.nextLine();
            reader = findReaderByID(readerID);
            if (reader == null) {
                System.out.println("Khong tim thay doc gia voi ID nay. Vui long nhap lai.");
            }
        } while (reader == null);

        // 3. Nhập và kiểm tra Librarian
        Librarian librarian = null;
        String librarianID;
        do {
            System.out.print("Nhap ID thu thu: ");
            librarianID = sc.nextLine();
            librarian = findLibrarianByID(librarianID);
            if (librarian == null) {
                System.out.println("Khong tim thay thu thu voi ID nay. Vui long nhap lai.");
            }
        } while (librarian == null);

        // 4. Thiết lập ngày mượn và ngày đáo hạn
        LocalDate borrowDate = LocalDate.now();
        int songay;
        System.out.print("Nhap so ngay se tra: ");
        songay = sc.nextInt();
        sc.nextLine();
        LocalDate dueDate = borrowDate.plusDays(songay);
        System.out.println("Ngay muon mac dinh: " + borrowDate.format(DATE_FORMAT));
        System.out.println("Ngay dao han: " + dueDate.format(DATE_FORMAT));

        // 5. Nhập chi tiết sách mượn
        List<LoanDetail> loanDetails = new ArrayList<>();
        boolean addingBooks = true;

        while (addingBooks) {
            System.out.print("Nhap ID sach muon (hoac 'stop' de ket thuc): ");
            String bookID = sc.nextLine();
            if (bookID.equalsIgnoreCase("stop")) {
                addingBooks = false;
                continue;
            }

            Book book = findBookByID(bookID);
            if (book == null) {
                System.out.println("Khong tim thay sach voi ID nay.");
                continue;
            }

            int quantity = -1;
            do {
                System.out.print(
                        "Nhap so luong sach '" + book.getName() + "' muon (toi da: " + book.getRemaining() + "): ");
                quantity = sc.nextInt();
                sc.nextLine(); // Clear buffer
                if (quantity <= 0 || quantity > book.getRemaining()) {
                    System.out.println("So luong khong hop le hoac vuot qua so luong con lai.");
                    quantity = -1; // Đặt lại để lặp lại
                }
            } while (quantity <= 0);

            // Trừ số lượng sách khỏi BookManager
            if (!bookManager.borrowBook(bookID, quantity)) {
                System.out.println("Khong the muon sach. Vui long thu lai.");
                continue;
            }

            LoanDetail detail = new LoanDetail(quantity, book, null); // actualReturnDate là null
            loanDetails.add(detail);
            System.out.println("Da them " + quantity + " cuon sach '" + book.getName() + "'.");
        }

        if (loanDetails.isEmpty()) {
            System.out.println("Phieu muon khong co sach. Da huy tao phieu.");
            return;
        }

        // 6. Tạo LoanTicket và thêm vào list
        LoanTicket newTicket = new LoanTicket(ticketID, dueDate, reader, librarian, borrowDate, loanDetails);
        list.add(newTicket);

        // 7. Cập nhật files
        updateLoanTicket();
        updateLoanDetail();
        bookManager.saveAllBooks(); // Lưu thay đổi số lượng sách vào file

        System.out.println("Tao phieu muon **" + ticketID + "** thanh cong!");
    }

    // Hàm hỗ trợ tìm phiếu mượn theo ID (dùng trong hàm add)
    private LoanTicket findTicketByID(String ticketID) {
        for (LoanTicket t : list) {
            if (t.getTicketID().equalsIgnoreCase(ticketID)) {
                return t;
            }
        }
        return null;
    }

    
    // 4. Hàm xóa phiếu mượn (remove)
    public void remove() {
        sc.nextLine(); // Clear buffer
        String ticketID;
        System.out.print("Hay nhap ID phieu muon ban muon xoa: ");
        ticketID = sc.nextLine();

        LoanTicket ticketToRemove = null;
        for (LoanTicket t : list) {
            if (t.getTicketID().equalsIgnoreCase(ticketID)) {
                ticketToRemove = t;
                break;
            }
        }

        if (ticketToRemove == null) {
            System.out.println("Khong tim thay phieu muon nao co ID nay!");
            return;
        }

        // Hiển thị thông tin trước khi xóa
        System.out.println("----------------THONG TIN PHIEU MUON---------------------");
        ticketToRemove.showLoanTicket();

        int xac_nhan = -1;
        while (xac_nhan != 0) {
            System.out.print("Neu muon XOA phieu muon nay hay bam 1, bam 0 neu muon huy: ");
            if (sc.hasNextInt()) {
                xac_nhan = sc.nextInt();
            } else {
                System.out.println("Du lieu nhap khong hop le. Hay nhap lai!");
                sc.next();
                continue;
            }

            switch (xac_nhan) {
                case 0:
                    System.out.println("Xac nhan huy xoa phieu muon!");
                    return;
                case 1:
                    // Hoàn lại số lượng sách trước khi xóa phiếu
                    for (LoanDetail detail : ticketToRemove.getLoanDetails()) {
                        if (detail.getBook() != null) {
                            bookManager.returnBook(detail.getBook().getBookID(), detail.getQuantity());
                        }
                    }
                    
                    list.remove(ticketToRemove);
                    updateLoanTicket();
                    updateLoanDetail();
                    bookManager.saveAllBooks(); // Lưu thay đổi số lượng sách
                    System.out.println("Xac nhan xoa phieu muon " + ticketID + " khoi danh sach!");
                    return;
                default:
                    System.out.println("Loi! Hay nhap lai!");
            }
        }
    } 

    // hàm check mã phiếu mượn có trong danh sách mượn không
    public boolean checkID(String id){
        for(LoanTicket t : list){
            if(t.getTicketID().equals(id)){
                return true;
            }
        }
        return false;        
    }
    // hàm kiểm tra mã sách đó có mượn trong phiếu mượn không
    public boolean checkIdBook(LoanTicket lt, String idBook){
        for( LoanDetail ld : lt.getLoanDetails() ){
            Book item = ld.getBook();
            if(item.getBookID().equals(idBook)){
                return true;
            }
        }
        return false;
    }



    // 5. Hàm trả sách (cập nhật lại)
    public void TraSach() {
        sc.nextLine(); // Clear buffer
        String loanTicketId;
        
        // Nhập mã phiếu mượn
        LoanTicket lt = null;
        do {
            System.out.print("Hay nhap vao ma phieu muon ban muon tra: ");
            loanTicketId = sc.nextLine();
            
            if (!checkID(loanTicketId)) {
                System.out.println("Khong co ma phieu muon nay trong danh sach! Hay nhap lai!");
            } else {
                lt = findTicketByID(loanTicketId);
                break;
            }
        } while (true);
        
        // Hiển thị thông tin phiếu mượn
        System.out.println("\n========== THONG TIN PHIEU MUON ==========");
        lt.showLoanTicket();
        
        // Kiểm tra xem có sách nào chưa trả không
        boolean hasUnreturnedBooks = false;
        for (LoanDetail detail : lt.getLoanDetails()) {
            if (detail.getActualReturnDate() == null) {
                hasUnreturnedBooks = true;
                break;
            }
        }
        
        if (!hasUnreturnedBooks) {
            System.out.println("\nTat ca sach trong phieu muon nay da duoc tra!");
            return;
        }
        
        // Chọn sách muốn trả
        String idBook;
        LoanDetail selectedDetail = null;
        do {
            System.out.print("\nNhap vao ma sach ban muon tra: ");
            idBook = sc.nextLine();
            
            if (!checkIdBook(lt, idBook)) {
                System.out.println("Khong co sach nay trong phieu muon! Hay nhap lai!");
            } else {
                // Tìm detail tương ứng
                for (LoanDetail detail : lt.getLoanDetails()) {
                    if (detail.getBook() != null && 
                        detail.getBook().getBookID().equals(idBook)) {
                        
                        // Kiểm tra xem sách này đã trả chưa
                        if (detail.getActualReturnDate() != null) {
                            System.out.println("Sach nay da duoc tra truoc do vao ngay: " 
                                + detail.getActualReturnDate());
                            selectedDetail = null;
                            break;
                        }
                        
                        selectedDetail = detail;
                        break;
                    }
                }
                
                if (selectedDetail != null) {
                    break;
                }
            }
        } while (true);
        
        // Xác nhận trả sách
        System.out.println("\n========== XAC NHAN TRA SACH ==========");
        System.out.println("Sach: " + selectedDetail.getBook().getName());
        System.out.println("So luong: " + selectedDetail.getQuantity());
        System.out.println("Ngay muon: " + lt.getBorrowDate());
        System.out.println("Ngay dao han: " + lt.getDueDate());
        
        System.out.print("\nXac nhan tra sach? (Y/N): ");
        String confirm = sc.nextLine();
        
        if (!confirm.equalsIgnoreCase("Y")) {
            System.out.println("Da huy tra sach!");
            return;
        }
        
        // Ghi nhận ngày trả
        LocalDate returnDate = LocalDate.now();
        selectedDetail.setActualReturnDate(returnDate);
        
        // Cập nhật số lượng sách trong kho
        bookManager.returnBook(selectedDetail.getBook().getBookID(), 
                            selectedDetail.getQuantity());
        
        // Kiểm tra trễ hạn và tạo phạt nếu cần
        checkAndCreateFine(lt, selectedDetail, returnDate);
        
        // Lưu vào file
        updateLoanDetail();
        bookManager.saveAllBooks();
        
        System.out.println("\n========== TRA SACH THANH CONG ==========");
        System.out.println("Da tra " + selectedDetail.getQuantity() + " cuon sach: " 
            + selectedDetail.getBook().getName());
        System.out.println("Ngay tra: " + returnDate);
    }

    // Hàm kiểm tra và tạo phạt nếu trả trễ
    private void checkAndCreateFine(LoanTicket ticket, LoanDetail detail, 
                                    LocalDate returnDate) {
        LocalDate dueDate = ticket.getDueDate();
        
        // Kiểm tra xem có trả trễ không
        if (returnDate.isAfter(dueDate)) {
            long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);
            
            if (daysLate > 0) {
                // Tính tiền phạt
                double finePerDay = detail.getBook().calcFine(1);
                double totalFine = finePerDay * daysLate;
                
                System.out.println("\n========== CANH BAO: TRA TRE ==========");
                System.out.println("So ngay tre: " + daysLate + " ngay");
                System.out.println("Tien phat moi ngay: " + String.format("%,.0f", finePerDay) + " VND");
                System.out.println("Tong tien phat: " + String.format("%,.0f", totalFine) + " VND");
                System.out.println("=======================================");
                
                // Tạo bản ghi phạt
                createFineRecord(ticket.getTicketID(), 
                            detail.getBook().getBookID(),
                            detail.getQuantity(),
                            finePerDay,
                            (int) daysLate,
                            "Chua dong phat");
            }
        } else {
            System.out.println("\nSach duoc tra dung han. Khong co phat!");
        }
    }

    // Hàm tạo bản ghi phạt vào file Fine.csv
    private void createFineRecord(String ticketID, String bookID, int quantity,
                                double finePerDay, int daysLate, String status) {
        try {
            File fineFile = new File("./data/Fine.csv");
            boolean fileExists = fineFile.exists();
            
            // Mở file ở chế độ append
            BufferedWriter bw = new BufferedWriter(new FileWriter(fineFile, true));
            
            // Nếu file chưa tồn tại, ghi header
            if (!fileExists) {
                bw.write("MaPhieuMuon,MaSach,SoLuong,GiaPhatMotNgay,SoNgayTre,TrangThai");
                bw.newLine();
            }
            
            // Ghi dữ liệu phạt
            String line = String.format("%s,%s,%d,%.0f,%d,%s",
                ticketID, bookID, quantity, finePerDay, daysLate, status);
            bw.write(line);
            bw.newLine();
            
            bw.close();
            System.out.println("Da luu thong tin phat vao file Fine.csv");
            
        } catch (IOException e) {
            System.err.println("Loi khi ghi file Fine.csv: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Hàm xem danh sách phạt
   public void viewFineList() {
        System.out.println("\n========== DANH SACH PHAT ==========");
        
        try (BufferedReader br = new BufferedReader(new FileReader("./data/Fine.csv"))) {
            String line = br.readLine();
            
            if (line == null) {
                System.out.println("Chua co ban ghi phat nao!");
                return;
            }
            
            System.out.println("+---------------+---------------+----------+------------------+--------------+------------------+-------------------+");
            System.out.printf("| %-13s | %-13s | %-8s | %-16s | %-12s | %-16s | %-17s |\n",
                "Ma Phieu", "Ma Sach", "So Luong", "Phat/Ngay (VND)", "Ngay Tre", "Tong Phat (VND)", "Trang Thai");
            System.out.println("+---------------+---------------+----------+------------------+--------------+------------------+-------------------+");
            
            boolean hasData = false;
            while ((line = br.readLine()) != null) {
                hasData = true;
                String[] data = line.split(",");
                
                if (data.length >= 6) {
                    // TÍNH TongPhat = GiaPhatMotNgay * SoNgayTre * SoLuong
                    int quantity = Integer.parseInt(data[2]);
                    double finePerDay = Double.parseDouble(data[3]);
                    int daysLate = Integer.parseInt(data[4]);
                    double totalFine = finePerDay * daysLate * quantity;
                    
                    System.out.printf("| %-13s | %-13s | %-8s | %,16.0f | %-12s | %,16.0f | %-17s |\n",
                        data[0], data[1], data[2], 
                        finePerDay,
                        data[4], 
                        totalFine,  // TÍNH RA
                        data[5]);   // TrangThai ở INDEX 5
                }
            }
            
            if (!hasData) {
                System.out.println("|                              Chua co ban ghi phat nao!                                |");
            }
            
            System.out.println("+---------------+---------------+----------+------------------+--------------+------------------+-------------------+");
            
        } catch (FileNotFoundException e) {
            System.out.println("Chua co file Fine.csv. Chua co ban ghi phat nao!");
        } catch (IOException e) {
            System.err.println("Loi khi doc file Fine.csv: " + e.getMessage());
        }
    }

    // Hàm đóng phạt
    public void payFine() {
        sc.nextLine();
        String ticketID;
        LoanTicket ticket = null;
        
        do {
            System.out.print("Nhap ma phieu muon can dong phat: ");
            ticketID = sc.nextLine();
            
            if (!checkID(ticketID)) {
                System.out.println("Khong tim thay phieu muon nay! Hay nhap lai!");
            } else {
                ticket = findTicketByID(ticketID);
                break;
            }
        } while (true);
        
        ArrayList<String[]> finesForTicket = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("./data/Fine.csv"))) {
            br.readLine();
            String line;
            
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 6 && data[0].equals(ticketID)) {
                    if (data[5].trim().equals("Chua dong phat")) {
                        finesForTicket.add(data);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Loi khi doc file Fine.csv!");
            return;
        }
        
        if (finesForTicket.isEmpty()) {
            System.out.println("\nPhieu muon nay khong co phat nao hoac da dong het phat!");
            return;
        }
        
        System.out.println("\n========== THONG TIN PHIEU MUON ==========");
        ticket.showLoanTicket();
        
        System.out.println("\n========== DANH SACH PHAT CHUA DONG ==========");
        System.out.println("+--------------+--------------------------------+----------+------------+--------------------+");
        System.out.printf("| %-12s | %-30s | %-8s | %-10s | %-18s |\n",
            "Ma Sach", "Ten Sach", "So Luong", "Ngay Tre", "Tien Phat (VND)");
        System.out.println("+--------------+--------------------------------+----------+------------+--------------------+");
        
        for (String[] fine : finesForTicket) {
            String bookID = fine[1];
            
            // TÍNH TongPhat
            int qty = Integer.parseInt(fine[2]);
            double finePerDay = Double.parseDouble(fine[3]);
            int days = Integer.parseInt(fine[4]);
            double totalFine = finePerDay * days * qty;
            
            String bookName = "Unknown";
            Book book = bookManager.findBookByID(bookID);
            if (book != null) {
                bookName = book.getName();
            }
            
            if (bookName.length() > 30) {
                bookName = bookName.substring(0, 27) + "...";
            }
            
            System.out.printf("| %-12s | %-30s | %-8s | %-10s | %,18.0f |\n",
                bookID, bookName, fine[2], days, totalFine);
        }
        System.out.println("+--------------+--------------------------------+----------+------------+--------------------+");
        
        String bookID;
        boolean validBook = false;
        
        do {
            System.out.print("\nNhap ma sach can dong phat (hoac 'all' de dong het): ");
            bookID = sc.nextLine();
            
            if (bookID.equalsIgnoreCase("all")) {
                validBook = true;
                break;
            }
            
            boolean found = false;
            for (String[] fine : finesForTicket) {
                if (fine[1].equals(bookID)) {
                    found = true;
                    break;
                }
            }
            
            if (!found) {
                System.out.println("Ma sach khong co trong danh sach phat! Hay nhap lai!");
            } else {
                validBook = true;
            }
        } while (!validBook);
        
        try {
            List<String> lines = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader("./data/Fine.csv"));
            
            String header = br.readLine();
            lines.add(header);
            
            String line;
            int updateCount = 0;
            double totalPaid = 0.0;
            
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                
                if (data.length >= 6 && data[0].equals(ticketID)) {
                    if (bookID.equalsIgnoreCase("all") || data[1].equals(bookID)) {
                        if (data[5].trim().equals("Chua dong phat")) {  // INDEX 5
                            // TÍNH TongPhat
                            double finePerDay = Double.parseDouble(data[3]);
                            int days = Integer.parseInt(data[4]);
                            int qty = Integer.parseInt(data[2]);
                            totalPaid += (finePerDay * days * qty);
                            
                            data[5] = "Da dong phat";  // CẬP NHẬT INDEX 5
                            line = String.join(",", data);
                            updateCount++;
                        }
                    }
                }
                
                lines.add(line);
            }
            br.close();
            
            // kiểm tra xem có cập nhập đúng không
            if (updateCount == 0) {
                System.out.println("Khong co phat nao duoc cap nhat!");
                return;
            }
            
            BufferedWriter bw = new BufferedWriter(new FileWriter("./data/Fine.csv"));
            for (String l : lines) {
                bw.write(l);
                bw.newLine();
            }
            bw.close();
            
            System.out.println("\n========== DONG PHAT THANH CONG ==========");
            System.out.println("Ma phieu muon: " + ticketID);
            if (bookID.equalsIgnoreCase("all")) {
                System.out.println("Da dong tat ca phat cua phieu nay");
            } else {
                System.out.println("Ma sach: " + bookID);
            }
            System.out.println("So luong phat da dong: " + updateCount);
            System.out.println("Tong so tien da dong: " + String.format("%,.0f", totalPaid) + " VND");
            System.out.println("==========================================");
            
        } catch (IOException e) {
            System.err.println("Loi khi xu ly file Fine.csv: " + e.getMessage());
        }
    }
}