package bill;

import dataService.DataService;
import java.util.*;
import java.io.*;
import Person.Reader;
import Person.Librarian;

public class BillManager implements DataService {
    private ArrayList<Bill> list = loadListBill();
    private Scanner sc = new Scanner(System.in);

    
    public ArrayList<Bill> getList() { return list; }
    public void setList(ArrayList<Bill> list) { this.list = list; }

    
    public void menu() {
        int choose = -1;
        do {
            System.out.println("\n================== MENU BILL MANAGER ==================");
            System.out.println("1. In danh sách hóa đơn");
            System.out.println("2. Tìm kiếm hóa đơn");
            System.out.println("3. Thêm hóa đơn mới");
            System.out.println("4. Xóa hóa đơn");
            System.out.println("5. Xem chi tiết hóa đơn");
            System.out.println("0. Thoát");
            System.out.println("=======================================================");
            System.out.print("Nhập lựa chọn: ");
            choose = sc.nextInt();
            sc.nextLine();
            switch (choose) {
                case 1 -> showList(list);
                case 2 -> search();
                case 3 -> add();
                case 4 -> remove();
                case 5 -> viewDetail();
                case 0 -> System.out.println("Thoát trình quản lý hóa đơn!\n");
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (choose != 0);
    }

   
    public void showList(ArrayList<Bill> list1) {
        if (list1.isEmpty()) {
            System.out.println("Danh sách hóa đơn trống!");
            return;
        }
        System.out.printf("| %-10s | %-20s | %-20s | %-15s | %-10s |\n",
                "Mã HD", "Người đọc", "Thủ thư", "Ngày lập", "Tổng tiền");
        System.out.println("--------------------------------------------------------------------------------");
        for (Bill b : list1) {
            System.out.printf("| %-10s | %-20s | %-20s | %-15s | %-10.2f |\n",
                    b.getBill_ID(),
                    b.getReader() != null ? b.getReader().getName() : "Không rõ",
                    b.getLibrarian() != null ? b.getLibrarian().getName() : "Không rõ",
                    b.getDate(),
                    b.getTotal());
        }
    }

    
    public void add() {
        System.out.print("Nhập mã hóa đơn: ");
        String id = sc.nextLine();
        System.out.print("Nhập ngày lập (dd/mm/yyyy): ");
        String date = sc.nextLine();

        System.out.print("Nhập tên người đọc: ");
        Reader reader = new Reader();
        reader.setName(sc.nextLine());

        System.out.print("Nhập tên thủ thư: ");
        Librarian librarian = new Librarian();
        librarian.setName(sc.nextLine());

        Bill bill = new Bill(id, reader, librarian, date);

        int addMore;
        do {
            System.out.print("Nhập tên sách: ");
            String bookName = sc.nextLine();
            System.out.print("Tác giả: ");
            String author = sc.nextLine();
            System.out.print("Số lượng sách: ");
            int quantity = sc.nextInt();
            System.out.print("Đơn giá (mỗi cuốn): ");
            double price = sc.nextDouble();
            sc.nextLine();

            
            book.Book tempBook = new book.Book(author, bookName, 0, 0) {
                @Override public double calcFine() { return 0; }
                @Override public void showINFO() {}
                public double getPrice() { return price; }
            };

            BillDetail detail = new BillDetail(tempBook, quantity);
            detail.setCost(price * quantity);
            bill.addBillDetail(detail);

            System.out.print("Thêm sách khác? (1 = Có, 0 = Không): ");
            addMore = sc.nextInt();
            sc.nextLine();
        } while (addMore == 1);

        bill.calculateTotal();
        list.add(bill);
        updateBillFile();
        System.out.println("Thêm hóa đơn thành công!");
    }

    
    public void remove() {
        System.out.print("Nhập mã hóa đơn cần xóa: ");
        String id = sc.nextLine();
        Iterator<Bill> iterator = list.iterator();
        while (iterator.hasNext()) {
            Bill b = iterator.next();
            if (b.getBill_ID().equals(id)) {
                System.out.print("Bạn có chắc muốn xóa? (1 = Có, 0 = Không): ");
                int c = sc.nextInt();
                sc.nextLine();
                if (c == 1) {
                    iterator.remove();
                    updateBillFile();
                    System.out.println("Xóa hóa đơn thành công!");
                }
                return;
            }
        }
        System.out.println("Không tìm thấy hóa đơn!");
    }

    
    public void search() {
        System.out.print("Nhập mã hóa đơn cần tìm: ");
        String id = sc.nextLine();
        for (Bill b : list) {
            if (b.getBill_ID().equalsIgnoreCase(id)) {
                b.showINFO();
                return;
            }
        }
        System.out.println("Không tìm thấy hóa đơn!");
    }

    
    public void viewDetail() {
        System.out.print("Nhập mã hóa đơn cần xem: ");
        String id = sc.nextLine();
        for (Bill b : list) {
            if (b.getBill_ID().equalsIgnoreCase(id)) {
                b.showINFO();
                return;
            }
        }
        System.out.println("Không tìm thấy hóa đơn!");
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
                Bill bill = new Bill(data[0], null, null, data[1]);
                bill.setTotal(Double.parseDouble(data[2]));
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
            bw.write("bill_ID,date,total");
            bw.newLine();
            for (Bill b : list) {
                bw.write(b.getBill_ID() + "," + b.getDate() + "," + b.getTotal());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
