package Person;

import book.Book;
import dataService.DataService;

import java.util.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
                    int quantity = Integer.parseInt(data[2]); // Sửa từ data[3] → data[2]

                    // Xử lý actualReturnDate (có thể rỗng)
                    LocalDate returnDate = (data.length > 3 && !data[3].isEmpty())
                            ? LocalDate.parse(data[3]) // Sửa từ data[4] → data[3]
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

    // 3. Hàm thêm phiếu mượn mới (add)
    public void add() {
        sc.nextLine(); // Clear buffer
        System.out.println("\n--- THEM PHIEU MUON MOI ---");

        // 1. Nhập và kiểm tra ID phiếu mượn
        String ticketID;
        LoanTicket existingTicket;
        do {
            System.out.print("Nhap ID phieu muon (vi du: LT001): ");
            ticketID = sc.nextLine();
            existingTicket = findTicketByID(ticketID); // Cần tạo hàm findTicketByID
            if (existingTicket != null) {
                System.out.println("ID phieu muon da ton tai. Vui long nhap ID khac.");
            }
        } while (existingTicket != null);

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
}
