package Person;

import book.*;
import java.util.*;

import org.w3c.dom.Text;

import java.io.*;

public class BookManager {

    private List<Book> allBooks = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);

    public BookManager() {
        loadAllBooks();
    }

    public List<Book> getAllBooks() {
        return allBooks;
    }

    // Load tất cả sách từ 4 file CSV
    private void loadAllBooks() {
        allBooks.clear();
        allBooks.addAll(loadTextBooks());
        allBooks.addAll(loadFictionBooks());
        allBooks.addAll(loadComicBooks());
        allBooks.addAll(loadReferenceBooks());
        System.out.println("Loaded " + allBooks.size() + " books in total.");
    }

    // Load TextBooks từ CSV
    private List<TextBook> loadTextBooks() {
        List<TextBook> books = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("./data/TextBook.csv"))) {
            br.readLine(); 
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                // name,author,quantity,remaining,price,subject,edition
                TextBook book = new TextBook(data[0], data[1], 
                    Integer.parseInt(data[2]), Integer.parseInt(data[3]), 
                    Double.parseDouble(data[4]),
                    data[5], Integer.parseInt(data[6]));
                books.add(book);
            }
        } catch (IOException e) {
            System.out.println("Error loading TextBooks!");
            e.printStackTrace();
        }
        return books;
    }

    // Load FictionBooks từ CSV
    private List<FictionBook> loadFictionBooks() {
        List<FictionBook> books = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("./data/FictionBook.csv"))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                // name,author,quantity,remaining,price,subGenre
                FictionBook book = new FictionBook(data[0], data[1],
                    Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                    Double.parseDouble(data[4]), data[5]);
                books.add(book);
            }
        } catch (IOException e) {
            System.out.println("Error loading FictionBooks!");
            e.printStackTrace();
        }
        return books;
    }

    // Load ComicBooks từ CSV
    private List<ComicBook> loadComicBooks() {
        List<ComicBook> books = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("./data/ComicBook.csv"))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                // name,author,quantity,remaining,price,artist,volumeNumber
                ComicBook book = new ComicBook(data[0], data[1], 
                    Integer.parseInt(data[2]), Integer.parseInt(data[3]), 
                    Double.parseDouble(data[4]),
                    data[5], Integer.parseInt(data[6]));
                book.setPrice(Double.parseDouble(data[4]));
                books.add(book);
            }
        } catch (IOException e) {
            System.out.println("Error loading ComicBooks!");
            e.printStackTrace();
        }
        return books;
    }

    // Load ReferenceBooks từ CSV
    private List<ReferenceBook> loadReferenceBooks() {
        List<ReferenceBook> books = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("./data/ReferenceBook.csv"))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                // name,author,quantity,remaining,price,isReadOnly
                ReferenceBook book = new ReferenceBook(data[0], data[1], 
                    Integer.parseInt(data[2]), Integer.parseInt(data[3]), 
                    Double.parseDouble(data[4]),
                    Boolean.parseBoolean(data[5]));
                book.setPrice(Double.parseDouble(data[4]));
                books.add(book);
            }
        } catch (IOException e) {
            System.out.println("Error loading ReferenceBooks!");
            e.printStackTrace();
        }
        return books;
    }

    // Menu chính
    public void menu() {
        int choice = 0;
        do {
            System.out.println("\n=============== MENU BOOK MANAGER ===============");
            System.out.println("-         1. Hien thi tat ca sach                 -");
            System.out.println("-         2. Tim kiem sach                        -");
            System.out.println("-         3. Them sach moi                        -");
            System.out.println("-         4. Xoa sach                             -");
            System.out.println("-         5. Cap nhat thong tin sach              -");
            System.out.println("-         6. Loc sach theo loai                   -");
            System.out.println("-         7. Kiem tra sach con trong kho          -");
            System.out.println("-         0. Thoat                                -");
            System.out.println("===================================================");
            System.out.print("Nhap lua chon: ");
            
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
            } else {
                System.out.println("Lua chon khong hop le!");
                sc.next();
                continue;
            }

            switch (choice) {
                case 1: showList(); break;
                case 2: search(); break;
                case 3: add(); break;
                case 4: remove(); break;
                case 5: updateBook(); break;
                case 6: filterByType(); break;
                case 7: checkAvailability(); break;
                case 0: 
                    resetBookIDs(); // Reset các bộ đếm ID trước khi thoát
                    System.out.println("Thoat quan ly sach!");
                    break;
                default: System.out.println("Lua chon khong hop le!");
            }
        } while (choice != 0);
    }

    // reset ID
    private void resetBookIDs() {
        Book.resetAllCounters();
    }

    // 1. Hiển thị tất cả sách
    public void showList() {
        if (allBooks.isEmpty()) {
            System.out.println("Khong co sach nao trong thu vien!");
            return;
        }

        System.out.println("\n======= DANH SACH TAT CA SACH (" + allBooks.size() + " cuon) =======");
        boolean once = true;
        for (Book book : allBooks) {
                if(book instanceof ComicBook && once){
                    System.out.println("+-----------------+-------------------------------------+---------------------------+----------+----------+--------------+-----------------+----------------------+-------+");
                    System.out.printf(formatCB, "Book ID", "Ten sach", "Tac gia", "So luong", "Con lai", "Gia (VND)", "Loai sach", "Hoa si", "vol");
                    System.out.println("+-----------------+-------------------------------------+---------------------------+----------+----------+--------------+-----------------+----------------------+-------+");
                    once = false;
                }
                if(book instanceof ComicBook){
                    book.showINFO();
                }
            }
            if(!once) System.out.println("+-----------------+-------------------------------------+---------------------------+----------+----------+--------------+-----------------+----------------------+-------+\n\n");
            once = true;
            for (Book book : allBooks) {
                if(book instanceof FictionBook && once){
                    System.out.println("+-----------------+-------------------------------------+---------------------------+----------+----------+--------------+-----------------+---------------------------+");
                    System.out.printf(formatFB, "Book ID", "Ten sach", "Tac gia", "So luong", "Con lai", "Gia (VND)", "Loai sach", "Sub genre");
                    System.out.println("+-----------------+-------------------------------------+---------------------------+----------+----------+--------------+-----------------+---------------------------+");
                    once = false;
                }
                if(book instanceof FictionBook){
                    book.showINFO();
                }
            }
            if(!once) System.out.println("+-----------------+-------------------------------------+---------------------------+----------+----------+--------------+-----------------+---------------------------+\n\n");
            once = true;
            for (Book book : allBooks) {
                if(book instanceof ReferenceBook && once){
                    System.out.println("+-----------------+-------------------------------------+---------------------------+----------+----------+--------------+-----------------+----------------------+");
                    System.out.printf(formatRB, "Book ID", "Ten sach", "Tac gia", "So luong", "Con lai", "Gia (VND)", "Loai sach", "Muon/Mua");
                    System.out.println("+-----------------+-------------------------------------+---------------------------+----------+----------+--------------+-----------------+----------------------+");
                    once = false;
                }
                if(book instanceof ReferenceBook){
                    book.showINFO();
                }
            }
            if(!once) System.out.println("+-----------------+-------------------------------------+---------------------------+----------+----------+--------------+-----------------+----------------------+\n\n");
            once = true;
            for (Book book : allBooks) {
                if(book instanceof TextBook && once){
                    System.out.println("+-----------------+-------------------------------------+---------------------------+----------+----------+--------------+-----------------+---------------------------+------------+");
                    System.out.printf(formatTB, "Book ID", "Ten sach", "Tac gia", "So luong", "Con lai", "Gia (VND)", "Loai sach", "Mon hoc", "Xuat ban");
                    System.out.println("+-----------------+-------------------------------------+---------------------------+----------+----------+--------------+-----------------+---------------------------+------------+");
                    once = false;
                }
                if(book instanceof TextBook){
                    book.showINFO();
                }
            }
            if(!once) System.out.println("+-----------------+-------------------------------------+---------------------------+----------+----------+--------------+-----------------+---------------------------+------------+\n\n");
    }

    // 2. Tìm kiếm sách
    public void search() {
        sc.nextLine(); // Clear buffer
        System.out.println("\n--- TIM KIEM SACH ---");
        System.out.println("1. Tim theo ten");
        System.out.println("2. Tim theo tac gia");
        System.out.println("3. Tim theo ID");
        System.out.print("Chon phuong thuc tim kiem: ");
        
        int choice = sc.nextInt();
        sc.nextLine();
        
        switch (choice) {
            case 1: searchByName(); break;
            case 2: searchByAuthor(); break;
            case 3: searchByID(); break;
            default: System.out.println("Lua chon khong hop le!");
        }
    }

    // Tìm theo tên
    private void searchByName() {
        System.out.print("Nhap ten sach: ");
        String name = sc.nextLine().toLowerCase();
        
        List<Book> results = new ArrayList<>();
        for (Book book : allBooks) {
            if (book.getName().toLowerCase().contains(name)) {
                results.add(book);
            }
        }
        
        displaySearchResults(results);
    }

    // Tìm theo tác giả
    private void searchByAuthor() {
        System.out.print("Nhap ten tac gia: ");
        String author = sc.nextLine().toLowerCase();
        
        List<Book> results = new ArrayList<>();
        for (Book book : allBooks) {
            if (book.getAuthor().toLowerCase().contains(author)) {
                results.add(book);
            }
        }
        
        displaySearchResults(results);
    }

    // Tìm theo ID
    private void searchByID() {
        System.out.print("Nhap ID sach: ");
        String id = sc.nextLine();
        
        Book book = findBookByID(id);
        if (book != null) {
            System.out.println("\n--- THONG TIN SACH ---");
            book.showInfo_1();
        } else {
            System.out.println("Khong tim thay sach co ID: " + id);
        }
    }

    // Header bảng
    //                  book id - name - author - soluong - remain - price - type - ...
    String formatCB = "| %-15s | %-35s | %-25s | %-8s | %-8s | %-12s | %-15s | %-20s | %-5s |\n";
    String formatTB = "| %-15s | %-35s | %-25s | %-8s | %-8s | %-12s | %-15s | %-25s | %-10s |\n";
    String formatRB = "| %-15s | %-35s | %-25s | %-8s | %-8s | %-12s | %-15s | %-20s |\n";
    String formatFB = "| %-15s | %-35s | %-25s | %-8s | %-8s | %-12s | %-15s | %-25s |\n";
    // Hiển thị kết quả tìm kiếm
    private void displaySearchResults(List<Book> results) {
        if (results.isEmpty()) {
            System.out.println("Khong tim thay sach nao!");
        } else {
            System.out.println("\n========== TIM THAY " + results.size() + " SACH ==========");
            
            boolean once = true;
            // Dữ liệu từng sách
            for (Book book : results) {
                if(book instanceof ComicBook && once){
                    System.out.println("+-----------------+-------------------------------------+---------------------------+----------+----------+--------------+-----------------+----------------------+-------+");
                    System.out.printf(formatCB, "Book ID", "Ten sach", "Tac gia", "So luong", "Con lai", "Gia (VND)", "Loai sach", "Hoa si", "vol");
                    System.out.println("+-----------------+-------------------------------------+---------------------------+----------+----------+--------------+-----------------+----------------------+-------+");
                    once = false;
                }
                if(book instanceof ComicBook){
                    book.showINFO();
                }
            }
            if(!once) System.out.println("+-----------------+-------------------------------------+---------------------------+----------+----------+--------------+-----------------+----------------------+-------+\n\n");
            once = true;
            for (Book book : results) {
                if(book instanceof FictionBook && once){
                    System.out.println("+-----------------+-------------------------------------+---------------------------+----------+----------+--------------+-----------------+---------------------------+");
                    System.out.printf(formatFB, "Book ID", "Ten sach", "Tac gia", "So luong", "Con lai", "Gia (VND)", "Loai sach", "Sub genre");
                    System.out.println("+-----------------+-------------------------------------+---------------------------+----------+----------+--------------+-----------------+---------------------------+");
                    once = false;
                }
                if(book instanceof FictionBook){
                    book.showINFO();
                }
            }
            if(!once) System.out.println("+-----------------+-------------------------------------+---------------------------+----------+----------+--------------+-----------------+---------------------------+\n\n");
            once = true;
            for (Book book : results) {
                if(book instanceof ReferenceBook && once){
                    System.out.println("+-----------------+-------------------------------------+---------------------------+----------+----------+--------------+-----------------+----------------------+");
                    System.out.printf(formatRB, "Book ID", "Ten sach", "Tac gia", "So luong", "Con lai", "Gia (VND)", "Loai sach", "Muon/Mua");
                    System.out.println("+-----------------+-------------------------------------+---------------------------+----------+----------+--------------+-----------------+----------------------+");
                    once = false;
                }
                if(book instanceof ReferenceBook){
                    book.showINFO();
                }
            }
            if(!once) System.out.println("+-----------------+-------------------------------------+---------------------------+----------+----------+--------------+-----------------+----------------------+\n\n");
            once = true;
            for (Book book : results) {
                if(book instanceof TextBook && once){
                    System.out.println("+-----------------+-------------------------------------+---------------------------+----------+----------+--------------+-----------------+---------------------------+------------+");
                    System.out.printf(formatTB, "Book ID", "Ten sach", "Tac gia", "So luong", "Con lai", "Gia (VND)", "Loai sach", "Mon hoc", "Xuat ban");
                    System.out.println("+-----------------+-------------------------------------+---------------------------+----------+----------+--------------+-----------------+---------------------------+------------+");
                    once = false;
                }
                if(book instanceof TextBook){
                    book.showINFO();
                }
            }
            if(!once) System.out.println("+-----------------+-------------------------------------+---------------------------+----------+----------+--------------+-----------------+---------------------------+------------+\n\n");
        }
    }

    // Tìm sách theo ID (public để LoanManager dùng)
    public Book findBookByID(String bookID) {
        for (Book book : allBooks) {
            if (book.getBookID().equals(bookID)) {
                return book;
            }
        }
        return null;
    }

    // 3. Thêm sách mới
    public void add() {
        sc.nextLine();
        System.out.println("\n--- THEM SACH MOI ---");
        System.out.println("1. Text Book");
        System.out.println("2. Fiction Book");
        System.out.println("3. Comic Book");
        System.out.println("4. Reference Book");
        System.out.print("Chon loai sach: ");
        
        int type = sc.nextInt();
        sc.nextLine();
        
        // TODO: Implement logic thêm sách
        switch (type) {
            case 1: addTextBook();         break;
            case 2: addFictionBook();      break;
            case 3: addComicBook();        break;
            case 4: addReferenceBook();    break;
            default:System.out.println(type + " khong hop le\n");
                    return;
        }
    }

    //Thêm text book
    public void addTextBook() {
        TextBook tb = new TextBook();
        tb.enterInfo();
        allBooks.add(tb);
        saveTextBooks(); // lưu vào file csv
        System.out.println("Them thanh cong!\n");
    }
    // thêm Comic Book
    public void addComicBook() {
        ComicBook cb = new ComicBook();
        cb.enterInfo();
        allBooks.add(cb);
        saveComicBooks();
        System.out.println("Them thanh cong!\n");
    }
    // thêm Fiction Book
    public void addFictionBook() {
        FictionBook fb = new FictionBook();
        fb.enterInfo();
        allBooks.add(fb);
        saveComicBooks();
        System.out.println("Them thanh cong!\n");
    }
    // thêm Reference Book
    public void addReferenceBook() {
        ReferenceBook rb = new ReferenceBook();
        rb.enterInfo();
        allBooks.add(rb);
        saveReferenceBooks();
        System.out.println("Them thanh cong!\n");
    }


    // 4. Xóa sách
    public void remove() {
        sc.nextLine();
        System.out.print("Nhap ID sach can xoa: ");
        String id = sc.nextLine();
        
        Book book = findBookByID(id);
        if (book == null) {
            System.out.println("Khong tim thay sach!");
            return;
        }
        System.out.println("======== Thong tin sach ============");
        book.showInfo_1();
        System.out.print("Xac nhan xoa (Y/N)? ");
        String confirm = sc.nextLine();
        
        if (confirm.equalsIgnoreCase("Y")) {
            allBooks.remove(book);
            saveAllBooks();  // Tự động lưu vào file
            System.out.println("Da xoa sach thanh cong!");
        } else {
            System.out.println("Huy xoa!");
        }
    }

    // 5. Cập nhật thông tin sách
    public void updateBook() {
        sc.nextLine();
        System.out.print("Nhap ID sach can cap nhat: ");
        String id = sc.nextLine();
        
        Book book = findBookByID(id);
        if (book == null) {
            System.out.println("Khong tim thay sach!");
            return;
        }
        
        System.out.println("\n--- THONG TIN SACH HIEN TAI ---");
        book.showInfo_1();
        
        System.out.println("\n--- CAP NHAT THONG TIN ---");
        System.out.println("1. Cap nhat ten sach");
        System.out.println("2. Cap nhat tac gia");
        System.out.println("3. Cap nhat so luong");
        System.out.println("4. Cap nhat so luong con lai");
        System.out.println("5. Cap nhat gia sach");
        System.out.println("6. Cap nhat thong tin chi tiet (theo loai sach)");
        System.out.println("0. Huy");
        System.out.print("Chon thong tin can cap nhat: ");
        
        int choice = sc.nextInt();
        sc.nextLine(); // Clear buffer
        
        switch (choice) {
            case 1:
                System.out.print("Nhap ten sach moi: ");
                String newName = sc.nextLine();
                book.setName(newName);
                System.out.println("Da cap nhat ten sach!\n");
                break;
                
            case 2:
                System.out.print("Nhap tac gia moi: ");
                String newAuthor = sc.nextLine();
                book.setAuthor(newAuthor);
                System.out.println("Da cap nhat tac gia!\n");
                break;
                
            case 3:
                System.out.print("Nhap so luong moi: ");
                int newQuantity = sc.nextInt();
                book.setQuantity(newQuantity);
                System.out.println("Da cap nhat so luong!\n");
                break;
                
            case 4:
                System.out.print("Nhap so luong con lai moi: ");
                int newRemaining = sc.nextInt();
                if (newRemaining > book.getQuantity()) {
                    System.out.println("Loi: So luong con lai khong the lon hon tong so luong!\n");
                } else {
                    book.setRemaining(newRemaining);
                    System.out.println("Da cap nhat so luong con lai!\n");
                }
                break;
                
            case 5:
                System.out.print("Nhap gia moi: ");
                double newPrice = sc.nextDouble();
                if (newPrice < 0) {
                    System.out.println("Loi: Gia khong the am!\n");
                } else {
                    book.setPrice(newPrice);
                    System.out.println("Da cap nhat gia sach!\n");
                }
                break;
                
            case 6:
                updateSpecificBookInfo(book);
                break;
                
            case 0:
                System.out.println("Huy cap nhat!");
                return;
                
            default:
                System.out.println("Lua chon khong hop le!");
                return;
        }
        
        // Lưu thay đổi vào file
        saveAllBooks();
        System.out.println("\n--- THONG TIN SAU KHI CAP NHAT ---");
        book.showInfo_1();
    }
    
    // Cập nhật thông tin đặc thù theo từng loại sách
    private void updateSpecificBookInfo(Book book) {
        if (book instanceof TextBook) {
            TextBook tb = (TextBook) book;
            System.out.println("1. Cap nhat mon hoc (Subject)");
            System.out.println("2. Cap nhat ban (Edition)");
            System.out.print("Chon: ");
            int choice = sc.nextInt();
            sc.nextLine();
            
            switch (choice) {
                case 1:
                    System.out.print("Nhap mon hoc moi: ");
                    String subject = sc.nextLine();
                    tb.setSubject(subject);
                    System.out.println("Da cap nhat mon hoc!\n");
                    break;
                case 2:
                    System.out.print("Nhap ban moi: ");
                    int edition = sc.nextInt();
                    tb.setEdition(edition);
                    System.out.println("Da cap nhat ban!\n");
                    break;
                default:
                    System.out.println("Lua chon khong hop le!\n");
            }
            
        } else if (book instanceof FictionBook) {
            FictionBook fb = (FictionBook) book;
            System.out.print("Nhap the loai phu moi (Sub Genre): ");
            String subGenre = sc.nextLine();
            fb.setSubGenre(subGenre);
            System.out.println("Da cap nhat the loai phu!\n");
            
        } else if (book instanceof ComicBook) {
            ComicBook cb = (ComicBook) book;
            System.out.println("1. Cap nhat hoa si (Artist)");
            System.out.println("2. Cap nhat so tap (Volume Number)");
            System.out.print("Chon: ");
            int choice = sc.nextInt();
            sc.nextLine();
            
            switch (choice) {
                case 1:
                    System.out.print("Nhap ten hoa si moi: ");
                    String artist = sc.nextLine();
                    cb.setArtist(artist);
                    System.out.println("Da cap nhat hoa si!\n");
                    break;
                case 2:
                    System.out.print("Nhap so tap moi: ");
                    int volume = sc.nextInt();
                    cb.setVolumeNumber(volume);
                    System.out.println("Da cap nhat so tap!\n");
                    break;
                default:
                    System.out.println("Lua chon khong hop le!\n");
            }
            
        } else if (book instanceof ReferenceBook) {
            ReferenceBook rb = (ReferenceBook) book;
            System.out.println("Cap nhat trang thai chi doc:");
            System.out.println("1. Co (Read Only)");
            System.out.println("2. Khong (Cho muon)");
            System.out.print("Chon: ");
            int choice = sc.nextInt();
            rb.setReadOnly(choice == 1);
            System.out.println("Da cap nhat trang thai!\n");
        }
    }

    // 6. Lọc theo loại sách
    public void filterByType() {
        System.out.println("\n--- LOC THEO LOAI SACH ---");
        System.out.println("1. Text Books");
        System.out.println("2. Fiction Books");
        System.out.println("3. Comic Books");
        System.out.println("4. Reference Books");
        System.out.print("Chon loai: ");
        
        int type = sc.nextInt();
        List<Book> filtered = new ArrayList<>();
        
        for (Book book : allBooks) {
            switch (type) {
                case 1: if (book instanceof TextBook) filtered.add(book); break;
                case 2: if (book instanceof FictionBook) filtered.add(book); break;
                case 3: if (book instanceof ComicBook) filtered.add(book); break;
                case 4: if (book instanceof ReferenceBook) filtered.add(book); break;
            }
        }
        displaySearchResults(filtered);
    }

    // 7. Kiểm tra sách còn trong kho
    public void checkAvailability() {
        System.out.println("\n--- SACH CON TRONG KHO ---");
        List<Book> available = new ArrayList<>();
        
        for (Book book : allBooks) {
            if (book.getRemaining() > 0) {
                available.add(book);
            }
        }
        
        if (available.isEmpty()) {
            System.out.println("Khong co sach nao con trong kho!");
        } else {
            System.out.println("Co " + available.size() + " sach con trong kho:");
            System.out.println("+-------------------------------------+---------------------------+------------+");
            System.out.printf("| %-35s | %-25s | %-10s |\n", "Ten sach", "Loai sach", "Con lai");
            System.out.println("+-------------------------------------+---------------------------+------------+");
            for (Book book : available) {
                System.out.printf("| %-35s | %-25s | %-10s |\n", book.getName(), book.getBookType(), book.getRemaining());
            }
            System.out.println("+-------------------------------------+---------------------------+------------+");
        }
    }

    // Cập nhật số lượng sách khi mượn
    public boolean borrowBook(String bookID, int quantity) {
        Book book = findBookByID(bookID);
        if (book == null) {
            System.out.println("Khong tim thay sach!");
            return false;
        }
        
        if (book.getRemaining() < quantity) {
            System.out.println("Khong du sach! Chi con " + book.getRemaining() + " cuon.");
            return false;
        }
        
        book.setRemaining(book.getRemaining() - quantity);
        System.out.println("Da muon " + quantity + " cuon sach: " + book.getName());
        return true;
    }

    // Cập nhật số lượng sách khi trả
    public void returnBook(String bookID, int quantity) {
        Book book = findBookByID(bookID);
        if (book != null) {
            book.setRemaining(book.getRemaining() + quantity);
            System.out.println("Da tra " + quantity + " cuon sach: " + book.getName());
        }
    }

    // Lưu tất cả sách vào CSV
    public void saveAllBooks() {
        saveTextBooks();
        saveFictionBooks();
        saveComicBooks();
        saveReferenceBooks();
        System.out.println("Da luu tat ca sach vao file CSV!");
    }

    // Lưu TextBooks vào CSV
    private void saveTextBooks() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("./data/TextBook.csv"))) {
            // Ghi header
            bw.write("name,author,quantity,remaining,price,subject,edition");
            bw.newLine();
            
            // Ghi từng sách
            for (Book book : allBooks) {
                if (book instanceof TextBook) {
                    TextBook tb = (TextBook) book;
                    bw.write(tb.getName() + "," +
                            tb.getAuthor() + "," +
                            tb.getQuantity() + "," +
                            tb.getRemaining() + "," +
                            tb.getPrice() + "," +
                            tb.getSubject() + "," +
                            tb.getEdition());
                    bw.newLine();
                }
            }
            System.out.println("Da luu TextBooks vao file!");
        } catch (IOException e) {
            System.out.println("Loi khi luu TextBooks!");
            e.printStackTrace();
        }
    }

    // Lưu FictionBooks vào CSV
    private void saveFictionBooks() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("./data/FictionBook.csv"))) {
            // Ghi header
            bw.write("name,author,quantity,remaining,price,subGenre");
            bw.newLine();
            
            // Ghi từng sách
            for (Book book : allBooks) {
                if (book instanceof FictionBook) {
                    FictionBook fb = (FictionBook) book;
                    bw.write(fb.getName() + "," +
                            fb.getAuthor() + "," +
                            fb.getQuantity() + "," +
                            fb.getRemaining() + "," +
                            fb.getPrice() + "," +
                            fb.getSubGenre());
                    bw.newLine();
                }
            }
            System.out.println("Da luu FictionBooks vao file!");
        } catch (IOException e) {
            System.out.println("Loi khi luu FictionBooks!");
            e.printStackTrace();
        }
    }

    // Lưu ComicBooks vào CSV
    private void saveComicBooks() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("./data/ComicBook.csv"))) {
            // Ghi header
            bw.write("name,author,quantity,remaining,price,artist,volumeNumber");
            bw.newLine();
            
            // Ghi từng sách
            for (Book book : allBooks) {
                if (book instanceof ComicBook) {
                    ComicBook cb = (ComicBook) book;
                    bw.write(cb.getName() + "," +
                            cb.getAuthor() + "," +
                            cb.getQuantity() + "," +
                            cb.getRemaining() + "," +
                            cb.getPrice() + "," +
                            cb.getArtist() + "," +
                            cb.getVolumeNumber());
                    bw.newLine();
                }
            }
            System.out.println("Da luu ComicBooks vao file!");
        } catch (IOException e) {
            System.out.println("Loi khi luu ComicBooks!");
            e.printStackTrace();
        }
    }

    // Lưu ReferenceBooks vào CSV
    private void saveReferenceBooks() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("./data/ReferenceBook.csv"))) {
            // Ghi header
            bw.write("name,author,quantity,remaining,price,isReadOnly");
            bw.newLine();
            
            // Ghi từng sách
            for (Book book : allBooks) {
                if (book instanceof ReferenceBook) {
                    ReferenceBook rb = (ReferenceBook) book;
                    bw.write(rb.getName() + "," +
                            rb.getAuthor() + "," +
                            rb.getQuantity() + "," +
                            rb.getRemaining() + "," +
                            rb.getPrice() + "," +
                            rb.isReadOnly());
                    bw.newLine();
                }
            }
            System.out.println("Da luu ReferenceBooks vao file!");
        } catch (IOException e) {
            System.out.println("Loi khi luu ReferenceBooks!");
            e.printStackTrace();
        }
    }
}
