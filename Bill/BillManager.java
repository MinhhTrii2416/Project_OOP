package Bill;

import dataService.DataService;
import java.util.*;
import java.io.*;
import java.time.*;
import java.time.format.*;
import Person.*;
import Person.Librarian;
import book.Book;

public class BillManager implements DataService {
    private ArrayList<Bill> list = loadListBill();
    Scanner sc = new Scanner(System.in);

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
            choose = sc.nextInt();
            sc.nextLine();
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

    public void showList(ArrayList<Bill> list1) {
        if (list1.isEmpty()) {
            System.out.println("Danh sach hoa don trong!");
            return;
        }
        System.out.printf("| %-10s | %-20s | %-20s | %-15s | %-10s |\n",
                "Ma HD", "Nguoi doc", "Thu thu", "Ngay lap", "Tong tien");
        System.out.println("--------------------------------------------------------------------------------");
        for (Bill b : list1) {
            System.out.printf("| %-10s | %-20s | %-20s | %-15s | %-10.2f |\n",
                    b.getBill_ID(),
                    b.getReader() != null ? b.getReader().getName() : "Khong ro",
                    b.getLibrarian() != null ? b.getLibrarian().getName() : "Khong ro",
                    b.getDate(),
                    b.getTotal());
        }
    }

    public void showList() {
        if (list.isEmpty()) {
            System.out.println("Danh sach hoa don trong!");
            return;
        }
        System.out.printf("| %-10s | %-20s | %-20s | %-15s | %-10s |\n",
                "Ma HD", "Nguoi doc", "Thu thu", "Ngay lap", "Tong tien");
        System.out.println("--------------------------------------------------------------------------------");
        for (Bill b : list) {
            System.out.printf("| %-10s | %-20s | %-20s | %-15s | %-10.2f |\n",
                    b.getBill_ID(),
                    b.getReader() != null ? b.getReader().getName() : "Khong ro",
                    b.getLibrarian() != null ? b.getLibrarian().getName() : "Khong ro",
                    b.getDate(),
                    b.getTotal());
        }
    }

    public void add() {
        String librarianID, readerID, billID=null;
        int count = 0;
        while(count<3){
            System.out.print("Nhap ma hoa don: ");
            billID = sc.nextLine();
            if( checkID(billID) ){
                System.out.println("Ma BillID nay da co hay tao ma BillID moi!");
                ++count;
            }
            else{
                break;
            }
            if( count>3){
                System.out.println("Da qua so lan nhap!");
                return;
            }
        }
    
        ReaderManager RM = new ReaderManager();
        count = 0;
        while(count<3){
            System.out.print("Nhap id nguoi doc: ");
            readerID = sc.nextLine();
            if(!RM.checkID(readerID)){
                System.out.println("Khong co ma nguoi doc nay hay nhap lai!");
            }
            else{ break; }
            if( count>3){
                System.out.println("Da qua so lan nhap!");
                return;
            }
        }

        
        LibrarianManager LM = new LibrarianManager();
        count = 0;
        while(count<3){
            System.out.print("Nhap ten thu thu: ");
            librarianID = sc.nextLine();
            if(!LM.checkID(librarianID)){
                System.out.println("Khong tm thay ma thu thu nay hay nhap lai ma khac!");
            }
            else{ break; }
            if( count>3){
                System.out.println("Da qua so lan nhap!");
                return;
            }
        }

        // tạo ngày tạo bill
        LocalDate d = LocalDate.now();
        String date = d.toString();
        Bill bill = new Bill(billID, readerID, librarianID, date);

        String bookID;
        int quantity; 
        BookManager BM = new BookManager();
        int choose = 1;
        while(choose==1){
            boolean flag = false;
            while(true){
                System.out.print("Nhap vao ma sach: ");
                bookID = sc.nextLine();
                for( Book b : BM.getAllBooks() ){
                    if( b.getBookID().equals(bookID)){
                        flag = true;
                    }
                    else flag = false;
                }
                if(!flag){
                    System.out.println("Khong cao sach nao co ma nay hay nhap lai!");
                }
                else break;
            }

            System.out.print("Nhap vao so luong cuon sach: ");
            quantity = sc.nextInt();

            BillDetail bd = new BillDetail(bookID, quantity);
            bill.addBillDetail(bd);

            System.out.println("Ban van muon them mot chi tiet hoa don (1: Co, 0: Khong): ");
            choose = sc.nextInt();
            sc.nextLine();
        }



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
                Bill bill = new Bill(data[0], null, null, data[3]);
                try {
                    bill.setTotal(Double.parseDouble(data[4]));
                } catch (NumberFormatException e) {
                    bill.setTotal(0);
                    System.out.println("Loi du lieu total o hoa don: " + data[0]);
                }
                list.add(bill);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private void updateBillFile() {
        File dataDir = new File("./data");
        if (!dataDir.exists()) dataDir.mkdir();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("./data/Bill.csv"))) {
            bw.write("bill_ID,readerID,librarianID,date,total");
            bw.newLine();
            for (Bill b : list) {
                String readerID = b.getReader() != null ? b.getReader().getReaderID() : "";
                String librarianID = b.getLibrarian() != null ? b.getLibrarian().getLibrarianID() : "";
                bw.write(b.getBill_ID() + "," + readerID + "," + librarianID + "," + b.getDate() + "," + b.getTotal());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
