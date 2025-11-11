package Bill;

import dataService.DataService;
import java.util.*;
import java.io.*;
import java.time.*;
import java.time.format.*;
// Sửa import theo package Person đã cung cấp
import Person.ReaderManager; 
import Person.LibrarianManager;
import Person.BookManager; // Sửa package import
import book.Book; // Class Book nằm trong package book

public class BillManager implements DataService {
    private ArrayList<Bill> list = loadListBill();
    Scanner sc = new Scanner(System.in);
    private final int maxAttempts = 3; 

    public ArrayList<Bill> getList() { return list; }
    public void setList(ArrayList<Bill> list) { this.list = list; }

    public void menu() {
        int choose = -1;
        do {
            System.out.println("\n================== MENU BILL MANAGER ==================");
            System.out.println("1. In danh sach hoa don");
            System.out.println("2. Tim kiem hoa don");
            System.out.println("3. Them hoa don moi");
            System.out.println("4. Xoa hoa don");
            System.out.println("5. Xem chi tiet hoa don");
            System.out.println("0. Thoat");
            System.out.println("=======================================================");
            System.out.print("Nhap lua chon: ");
            if (sc.hasNextInt()) {
                choose = sc.nextInt();
                sc.nextLine(); 
            } else {
                sc.nextLine();
                choose = -1;
                System.out.println("Lua chon khong hop le!");
                continue;
            }
            switch (choose) {
                case 1 -> showList();
                case 2 -> search();
                case 3 -> add();
                case 4 -> remove();
                case 5 -> viewDetail();
                case 0 -> System.out.println("Thoat trinh quan ly hoa don!\n");
                default -> System.out.println("Lua chon khong hop le!");
            }
        } while (choose != 0);
    }

    // Hiển thị ID (sử dụng getReaderID(), getLibrarianID() đã sửa trong Bill.java)
    public void showList(ArrayList<Bill> list1) {
        if (list1.isEmpty()) {
            System.out.println("Danh sach hoa don trong!");
            return;
        }
        System.out.printf("| %-10s | %-12s | %-12s | %-15s | %-10s |\n",
                "Ma HD", "Ma Nguoi doc", "Ma Thu thu", "Ngay lap", "Tong tien");
        System.out.println("--------------------------------------------------------------------------------");
        for (Bill b : list1) {
            System.out.printf("| %-10s | %-12s | %-12s | %-15s | %-10.2f |\n",
                    b.getBill_ID(),
                    b.getReaderID() != null ? b.getReaderID() : "Khong ro",
                    b.getLibrarianID() != null ? b.getLibrarianID() : "Khong ro",
                    b.getDate(),
                    b.getTotal());
        }
    }

    public void showList() {
        if (list.isEmpty()) {
            System.out.println("Danh sach hoa don trong!");
            return;
        }
        System.out.printf("| %-10s | %-12s | %-12s | %-15s | %-10s |\n",
                "Ma HD", "Ma Nguoi doc", "Ma Thu thu", "Ngay lap", "Tong tien");
        System.out.println("--------------------------------------------------------------------------------");
        for (Bill b : list) {
            System.out.printf("| %-10s | %-12s | %-12s | %-15s | %-10.2f |\n",
                    b.getBill_ID(),
                    b.getReaderID() != null ? b.getReaderID() : "Khong ro",
                    b.getLibrarianID() != null ? b.getLibrarianID() : "Khong ro",
                    b.getDate(),
                    b.getTotal());
        }
    }

    // Hàm thêm hóa đơn: Kiểm tra ID người đọc, thủ thư, và sách. Dùng ngày hệ thống
    public void add() {
        String librarianID = null, readerID = null, billID = null;
        int count = 0;

        // 1. Nhập và kiểm tra ID hóa đơn (Kiểm tra trùng lặp)
        while(count < maxAttempts){
            System.out.print("Nhap ma hoa don: ");
            billID = sc.nextLine();
            if( checkID(billID) ){
                System.out.println("Ma BillID nay da co. Hay tao ma BillID moi!");
                ++count;
            }
            else{
                break; 
            }
            if( count >= maxAttempts){
                System.out.println("Da qua so lan nhap ma hoa don! Dung them hoa don.");
                return;
            }
        }
        if (count >= maxAttempts) return;


        // 2. Nhập và kiểm tra ID người đọc (Kiểm tra tồn tại)
        ReaderManager RM = new ReaderManager();
        count = 0;
        while(count < maxAttempts){
            System.out.print("Nhap ma nguoi doc: ");
            readerID = sc.nextLine();
            if(!RM.checkID(readerID)){ // Giả định RM có checkID
                System.out.println("Khong co ma nguoi doc nay. Hay nhap lai!");
                ++count;
            }
            else{ break; }
            if( count >= maxAttempts){
                System.out.println("Da qua so lan nhap ma nguoi doc! Dung them hoa đơn.");
                return;
            }
        }
        if (count >= maxAttempts) return;

        
        // 3. Nhập và kiểm tra ID thủ thư (Kiểm tra tồn tại)
        LibrarianManager LM = new LibrarianManager();
        count = 0;
        while(count < maxAttempts){
            System.out.print("Nhap ma thu thu: ");
            librarianID = sc.nextLine();
            if(!LM.checkID(librarianID)){ // Giả định LM có checkID
                System.out.println("Khong tim thay ma thu thu nay. Hay nhap lai ma khac!");
                ++count;
            }
            else{ break; }
            if( count >= maxAttempts){
                System.out.println("Da qua so lan nhap ma thu thu! Dung them hoa đơn.");
                return;
            }
        }
        if (count >= maxAttempts) return;


        // 4. Tạo Bill và chi tiết Bill
        // Lấy ngày hệ thống
        LocalDate d = LocalDate.now();
        String date = d.toString();
        Bill bill = new Bill(billID, readerID, librarianID, date);

        String bookID = "";
        int quantity = 0; 
        BookManager BM = new BookManager();
        int choose = 1;

        while(choose==1){
            count = 0;
            boolean bookFound = false;

            // Vòng lặp kiểm tra ID sách (Kiểm tra tồn tại)
            while(count < maxAttempts){
                System.out.print("Nhap vao ma sach: ");
                bookID = sc.nextLine();
                
                // Kiểm tra sự tồn tại của sách
                for( Book b : BM.getAllBooks() ){ // Giả định BM.getAllBooks() hoạt động
                    System.out.println(b.getBookID());
                    if( b.getBookID().equals(bookID)){
                        bookFound = true;
                        break; 
                    }
                }
                
                if(!bookFound){
                    System.out.println("Khong co sach nao co ma nay. Hay nhap lai!");
                    ++count;
                }
                else break; 
                
                if( count >= maxAttempts){
                    System.out.println("Da qua so lan nhap ma sach! Ket thuc them chi tiet.");
                    break; 
                }
            }
            if (!bookFound) break; // Thoát khỏi vòng lặp thêm chi tiết nếu sách không được tìm thấy
            Book.resetAllCounters();
            
            System.out.print("Nhap vao so luong cuon sach: ");
            if (sc.hasNextInt()) {
                quantity = sc.nextInt();
                sc.nextLine(); 
            } else {
                System.out.println("So luong khong hop le, chi tiet hoa don nay se bi bo qua.");
                sc.nextLine();
                continue;
            }
            
            // Tạo BillDetail và thêm vào Bill
            BillDetail bd = new BillDetail(bookID, quantity);
            bill.addBillDetail(bd);

            System.out.println("Ban van muon them mot chi tiet hoa don (1: Co, 0: Khong): ");
            if (sc.hasNextInt()) {
                choose = sc.nextInt();
                sc.nextLine(); 
            } else {
                choose = 0; 
                sc.nextLine();
            }
        }
        
        // Thêm Bill vào danh sách và lưu file
        list.add(bill);
        updateBillFile();
        System.out.println("Them hoa don thanh cong!");
    }
    
    // check mã hóa đơn đã có hay chưa
    private boolean checkID(String billID){
        for(Bill b  : list){
            if( b.getBill_ID().equals(billID)){
                return true;
            }
        }
        return false;
    }


    public void remove() {
        System.out.print("Nhap ma hoa don can xoa: ");
        String id = sc.nextLine();
        Iterator<Bill> iterator = list.iterator();
        while (iterator.hasNext()) {
            Bill b = iterator.next();
            if (b.getBill_ID().equals(id)) {
                System.out.print("Ban co chac muon xoa? (1 = Co, 0 = Khong): ");
                int c = sc.nextInt();
                sc.nextLine();
                if (c == 1) {
                    iterator.remove();
                    updateBillFile();
                    System.out.println("Xoa hoa don thanh cong!");
                }
                return;
            }
        }
        System.out.println("Khong tim thay hoa don!");
    }

    public void search() {
        System.out.print("Nhap ma hoa don can tim: ");
        String id = sc.nextLine();
        for (Bill b : list) {
            if (b.getBill_ID().equalsIgnoreCase(id)) {
                b.showINFO();
                return;
            }
        }
        System.out.println("Khong tim thay hoa don!");
    }

    public void viewDetail() {
        System.out.print("Nhap ma hoa don can xem: ");
        String id = sc.nextLine();
        for (Bill b : list) {
            if (b.getBill_ID().equalsIgnoreCase(id)) {
                b.showINFO();
                return;
            }
        }
        System.out.println("Khong tim thay hoa don!");
    }

    private ArrayList<Bill> loadListBill() {
        ArrayList<Bill> list = new ArrayList<>();
        File file = new File("./data/Bill.csv");
        if (!file.exists()) return list;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 5) continue;
                // Bill đã được sửa để nhận ID
                Bill bill = new Bill(data[0], data[1], data[2], data[3]); 
                // Sửa lỗi: Khối try cần có catch
                try {
                    bill.setTotal(Double.parseDouble(data[4]));
                } catch (NumberFormatException e) {
                    bill.setTotal(0);
                    System.out.println("Loi du lieu total o hoa don: " + data[0]);
                }
                list.add(bill);
            }
        } catch (IOException e) { // Thêm khối catch cho BufferedReader
            e.printStackTrace();
        }
        return list;
    }

    // Hàm cập nhật file
    private void updateBillFile() {
        File dataDir = new File("./data");
        if (!dataDir.exists()) dataDir.mkdir();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("./data/Bill.csv"))) {
            bw.write("bill_ID,readerID,librarianID,date,total");
            bw.newLine();
            for (Bill b : list) {
                // Sử dụng getReaderID() và getLibrarianID()
                bw.write(b.getBill_ID() + "," + b.getReaderID() + "," + b.getLibrarianID() + "," + b.getDate() + "," + b.getTotal());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}