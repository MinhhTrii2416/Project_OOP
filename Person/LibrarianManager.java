package Person;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class LibrarianManager {
    Scanner sc = new Scanner(System.in);
    protected ArrayList<Librarian> list = loadListLib();    // cập nhập mật khẩu mới của thủ thư bên Librarian.java
    public void menu(){
        int choose = -1;
        while(choose!=0){
            System.out.println("--------------Libraria Manager----------");
            System.out.println("-         1.In danh sach thu thu.      -");
            System.out.println("-            2.Tim thu thu.            -");
            System.out.println("-            3.Them thu thu.           -");
            System.out.println("-            4.Xoa thu thu.            -");
            System.out.println("-        5.Sua thong tin thu thu.      -");
            System.out.println("-              0.Thoat.                -");
            System.out.println("----------------------------------------");
            System.out.print("Hay nhap vao lua chon cua ban: ");
            choose  = sc.nextInt();
            switch(choose){
                case 1: showList(); break;
                case 2: search(); break;
                case 3: add(); break;
                case 4: remove(); break;
                case 5: update(); break;
                case 0: break;
                default: 
                    System.out.println("Khong co lua chon nay hay nhap lai!");
            }
        }
    }
    
    // hàm in danh sách
    private void showList(ArrayList<Librarian> list1) {
        System.out.printf("| %-20s | %-10s | %-10s | %-15s | %-48s | %-10s | %-10s | %-10s | %-10s |\n",
            "Name", "Gender", "Phone", "Email", "Address", "ID", "Shift", "Salary", "Password");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");
        for (Librarian l : list1) {
            System.out.printf("| %-20s | %-10s | %-10s | %-15s | %-48s | %-10s | %-10s | %-10.2f | %-10s |\n",
                l.getName(), l.getGender(), l.getPhoneNumber(), l.getEmail(), l.getAddress(),
                l.getLibrarianID(), l.getShift(), l.getSalary(), l.getPassword());
        }
    }
    
    public void showList() {
        System.out.printf("| %-20s | %-10s | %-10s | %-15s | %-48s | %-10s | %-10s | %-10s | %-10s |\n",
            "Name", "Gender", "Phone", "Email", "Address", "ID", "Shift", "Salary", "Password");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");
        for (Librarian l : list) {
            System.out.printf("| %-20s | %-10s | %-10s | %-15s | %-48s | %-10s | %-10s | %-10.2f | %-10s |\n",
                l.getName(), l.getGender(), l.getPhoneNumber(), l.getEmail(), l.getAddress(),
                l.getLibrarianID(), l.getShift(), l.getSalary(), l.getPassword());
        }
    }

    // hàm tìm thủ thư
    public void search(){
        int choose = -1;
        while(choose!=0){
            System.out.println("--------------------Search Menu--------------------");
            System.out.println("-                  1.Tim theo ma.                 -");
            System.out.println("-                  2.Tim theo ten.                -");
            System.out.println("-                  0.Thoat.                       -");
            System.out.println("---------------------------------------------------");
            System.out.print("Nhap vao lua chon cua ban: ");
            choose = sc.nextInt();
            switch(choose){
                case 1:  searchID(); break;
                case 2:  searchName(); break;
                case 0: break;
                default:
                    System.out.println("Khong tim thay lua chon cua ban hay nhap lai!");
            }
        }
    }
    // tim kiem theo id
    public void searchID(){
        String  id;
        sc.nextLine();
        System.out.print("Nhap vao id thu thu ban can tim: ");
        id = sc.nextLine();
        for(Librarian l: list){
            if( l.getLibrarianID().equals(id)){
                System.out.println("-------------THONG TIN THU THU-----------");
                l.showINFO();
                return;
            }
        }
        System.out.println("Khong tim thay id thu thu ban nhap: ");
    }
    // tim kiem theo ten
    private void searchName(){
        String name;
        sc.nextLine();
        ArrayList<Librarian> kq = new ArrayList<>();
        System.out.print("Nhap vao ten tu thu ban can tim kiem: ");
        name = sc.nextLine();
        for( Librarian l: list){
            if(l.getName().equals(name)){
                kq.add(l);
            }
        }
        if( kq.size() == 0){
            System.out.println("Khong tim thay thu thu nao co ten nay!");
        }
        else showList(kq);
    }

    //hàm thêm thủ thư
    private void add(){
        sc.nextLine();
        String name, gender, phoneNumber, address, email, librarianID = null, shift, password = null;
        Double salary;
        boolean flag = false;
        while(!flag){
            System.out.print("Nhap vao id thu thu ban can tao: ");  
            librarianID = sc.nextLine();
            if(checkID(librarianID)){
                System.out.println("Id nay da duoc tao hay nhap ID khac!");
            }
            else flag = true;
        }
        System.out.print("Nhap vao ten thu thu can tao: ");
        name = sc.nextLine();
        System.out.print("Nhap vao gioi tinh thu thu can tao: ");
        gender = sc.nextLine();
        System.out.print("Nhap vao so dien thoai thu thu can tao: ");
        phoneNumber = sc.nextLine();
        System.out.print("Nhap vao dia chi thu thu can tao: ");
        address = sc.nextLine();
        System.out.print("Nhap vao emai cua thu thu: ");
        email = sc.nextLine();
        System.out.print("Nhap vao chuc vu cua thu thu: ");
        shift = sc.nextLine();
        System.out.print("Hay nhap vao luong cua thu thu nay: ");
        salary = sc.nextDouble();
        sc.nextLine();
        // nhập password hai lần
        flag = false;
        String password1;
        while(!flag){
            System.out.print("Nhap password ban muon tao: "); password = sc.nextLine();
            System.out.print("Nhap lai password ban muon tao lan nua: "); password1 = sc.nextLine();
            if( password.equals(password1)){
                System.out.println("Xac nhan password nay!");
                flag = true;
            }
            else System.out.println("Hai lan nhap password khong khop hay nhap lai!");
        }
        Librarian lib = new Librarian(name, gender, phoneNumber, email, address, librarianID, shift, salary, password);
        // in ra thông tin thủ thư mới tạp
        ArrayList<Librarian> add = new ArrayList<>();
        add.add(lib);
        showList(add);
        int xac_nhan = -1;
        while(xac_nhan != 0){
            System.out.print("Neu muon them thu thu nay hay nhap 1, 0 neu khong muon: ");
            xac_nhan = sc.nextInt();
            switch(xac_nhan){
                case 1: 
                    this.list.add(lib);
                    updateLib();
                    System.out.println("Xac nhan them thu thu nay vo danh sach thu thu!"); 
                    return;
                case 0:
                    System.out.println("Xac nhan khong them thu thu nay vo danh sach thu thu!");
                    break;
                default:
                    System.out.println("Không co lua chon nay hay nhap lai!");
            }
        }
    }
    //checkID
    public boolean checkID(String id){
        for(Librarian l: this.list){
            if(l.getLibrarianID().equals(id)){
                return true;
            }
        }
        return false;
    }

    // hàm xóa thủ thư
    public void remove() {
        int choose = -1;
        while (choose != 0) {
            System.out.println("----------------Remove Librarian-------------");
            System.out.println("-                1.Theo ma.                 -");
            System.out.println("-                2.Theo ten.                -");
            System.out.println("-                0.Thoat.                   -");
            System.out.println("---------------------------------------------");
            System.out.print("Ban muon xoa thu thu theo gi: ");
            choose = sc.nextInt();
            switch (choose) {
                case 0:
                    break;
                case 1:
                    RemoveID();
                    break;
                case 2:
                    RemoveName();
                    break;
                default:
                    System.out.println("Khong co lua chon nao nhu vay, hay nhap lai!");
            }
        }
    }
    // xóa theo id
    private void RemoveID() {
        sc.nextLine();
        String id;
        System.out.print("Hay nhap id ban muon xoa: ");
        id = sc.nextLine();
        for (Librarian l : this.list) {
            if (l.getLibrarianID().equals(id)) {
                System.out.println("-------------THONG TIN THU THU--------------");
                l.showINFO();
                int xac_nhan = -1;
                while (xac_nhan != 0) {
                    System.out.print("Neu muon xoa thu thu nay bam 1, bam 0 neu khong muon: ");
                    xac_nhan = sc.nextInt();
                    switch (xac_nhan) {
                        case 0:
                            System.out.println("Huy xoa thu thu nay!");
                            return;
                        case 1:
                            System.out.println("Xoa thanh cong thu thu khoi danh sach!");
                            this.list.remove(l);
                            updateLib(); // cập nhật file CSV
                            return;
                        default:
                            System.out.println("Loi! Hay nhap lai!");
                    }
                }
                return;
            }
        }
        System.out.println("Khong tim thay thu thu nao co id nay!");
    }
    // xóa theo tên
    private void RemoveName() {
        sc.nextLine();
        String name;
        ArrayList<Librarian> kq = new ArrayList<>();
        boolean flag = false;
        System.out.print("Hay nhap ten thu thu ban muon xoa: ");
        name = sc.nextLine();
        for (Librarian l : this.list) {
            if (l.getName().equalsIgnoreCase(name)) {
                flag = true;
                kq.add(l);
            }
        }
        if (!flag) {
            System.out.println("Khong tim thay thu thu nao co ten nay!");
            return;
        } else {
            System.out.println("So luong thu thu ten " + name + " tim thay duoc la: " + kq.size());
            showList(kq);

            boolean flag1 = false;
            int choose1;
            while (!flag1) {
                System.out.println("----------------Select Remove-----------------");
                System.out.println("-               1.Xoa tat ca.                -");
                System.out.println("-            2.Xoa theo ma ban chon.         -");
                System.out.println("----------------------------------------------");
                System.out.print("Hay nhap vao lua chon cua ban: ");
                choose1 = sc.nextInt();
                switch (choose1) {
                    case 1:
                        int xac_nhan = -1;
                        while (xac_nhan != 0) {
                            System.out.print("Neu muon xoa tat ca thu thu nay bam 1, bam 0 neu khong: ");
                            xac_nhan = sc.nextInt();
                            switch (xac_nhan) {
                                case 0:
                                    System.out.println("Huy xoa tat ca thu thu nay!");
                                    return;
                                case 1:
                                    System.out.println("Xoa thanh cong tat ca thu thu co ten " + name);
                                    this.list.removeAll(kq);
                                    updateLib();
                                    return;
                                default:
                                    System.out.println("Loi! Hay nhap lai!");
                            }
                        }
                        flag1 = true;
                        break;

                    case 2:
                        int xac_nhan1 = -1;
                        String selectID;
                        Librarian RemoveLibrarian = null;
                        sc.nextLine();
                        boolean flag2 = false;
                        while (!flag2) {
                            System.out.print("Hay nhap vao ma muon xoa o bang tren: ");
                            selectID = sc.nextLine();
                            for (Librarian l : this.list) {
                                if (l.getLibrarianID().equals(selectID)) {
                                    flag2 = true;
                                    RemoveLibrarian = l;
                                }
                            }
                            if (!flag2) {
                                System.out.println("Khong tim thay id nay o bang tren, hay nhap lai!");
                            }
                        }
                        while (xac_nhan1 != 0) {
                            System.out.print("Neu muon xoa thu thu nay bam 1, bam 0 neu khong muon: ");
                            xac_nhan1 = sc.nextInt();
                            switch (xac_nhan1) {
                                case 0:
                                    System.out.println("Huy xoa thu thu nay!");
                                    return;
                                case 1:
                                    System.out.println("Xoa thanh cong thu thu khoi danh sach!");
                                    this.list.remove(RemoveLibrarian);
                                    updateLib();
                                    return;
                                default:
                                    System.out.println("Loi! Hay nhap lai!");
                            }
                        }
                        flag1 = true;
                        break;

                    default:
                        System.out.println("Loi! Hay nhap lai!");
                }
            }
        }
    }

    // hàm sửa thông tin thủ thư
    public void update(){
        String id = null;
        boolean flag = false;
        sc.nextLine();
        while(!flag){
            System.out.print("Nhap vao ma thu thu ban muon sua: ");
            id = sc.nextLine();
            if(!checkID(id)){
                System.out.println("Khong tim thay id thu thu nay!Hay nhap lai!");
            }
            else flag = true;
        }
        Librarian ul  = new Librarian(); 
        for( Librarian l: this.list){
            if( l.getLibrarianID().equals(id)){
                ul = l; 
                break;
            }
        }

        int choose = -1;
        while(choose!=0){
            System.out.println("---------------------Update Librarian-----------------");
            System.out.println("-                  1.Sua ma thu thu.                 -");
            System.out.println("-                  2.Sua ten thu thu.                -");
            System.out.println("-                  3.Sua gioi tinh thu thu.          -");
            System.out.println("-                  4.Sua dia chi thu thu.            -");
            System.out.println("-                  5.Sua so dien thoai thu thu.      -");
            System.out.println("-                  6.Sua email thu thu.              -");
            System.out.println("-                  7.Sua ca lam thu thu.             -");
            System.out.println("-                  8.Sua luong thu thu.              -");
            System.out.println("-                  0.Thoat.                          -");
            System.out.println("------------------------------------------------------");
            System.out.print("Nhap vao lua chon cua ban: ");
            choose = sc.nextInt();
            sc.nextLine();
            switch(choose){
                case 1:
                    updateLibrarianID(ul); 
                    break;
                case 2: 
                    updateName(ul);
                    break;
                case 3:
                    updateGender(ul); 
                    break;
                case 4:
                    updateAddress(ul); 
                    break;
                case 5:
                    updatePhoneNumber(ul); 
                    break;
                case 6:
                    updateEmail(ul); 
                    break;
                case 7:
                    updateShift(ul); 
                    break;
                case 8:
                    updateSalary(ul); 
                    break;
                default:
                    System.out.println("Khong co lua chon nay ban hay nhap lai!");
            }
            updateLib();
        }
    }
    // update ma
    private void updateLibrarianID(Librarian ul){
        String id;
        boolean flag = false;
        while(!flag){
            System.out.print("Nhap vao ma ban muon sua: ");
            id = sc.nextLine();
            if(checkID(id)){
                System.out.println("Ma id nay da ton tai hay sua thanh ma id khac!");
            }
            else{
                ul.setLibrarianID(id);  
                flag = true;
            }
        }
    }
    //update ten
    private void updateName(Librarian ul){
        String name;
        System.out.print("Hay nhap ten ban muôn sua: ");
        name = sc.nextLine();
        ul.setName(name);
    }
    // update sua gioi tinh
    private void updateGender(Librarian ul){
        String gender;
        System.out.print("Nhap vao gioi tinh ban muon sua: ");
        gender = sc.nextLine();
        ul.setGender(gender);
    }
    // update dia chi
    private void updateAddress(Librarian ul){
        String address;
        System.out.print("Nhap vao dia chi moi ban muon sua: ");
        address = sc.nextLine();
        ul.setAddress(address);
    }
    // update so dien thoai 
    private void updatePhoneNumber(Librarian ul){
        String phoneNumber;
        System.out.print("Nhap vao so dien thoai ban muon sua: ");
        phoneNumber = sc.nextLine();
        ul.setPhoneNumber(phoneNumber);
    }
    // update email
    private void updateEmail(Librarian ul){
        String email;
        System.out.print("Nhap vao email moi: ");
        email = sc.nextLine();
        ul.setEmail(email);
    }
    //update ca lam
    private void updateShift(Librarian ul){
        String shift;
        System.out.print("Nhap vao ca lam ban muon doi: ");
        shift = sc.nextLine();
        ul.setShift(shift);
    }
    // update luong
    private void updateSalary(Librarian ul){
        double salary = 0;
        while(true) {
            try {
                System.out.print("Hay nhap vao luong cua thu thu nay: ");
                salary = Double.parseDouble(sc.nextLine());
                ul.setSalary(salary);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Luong phai la so, hay nhap lai!");
            }
        }
    }
    // hàm đọc dữ liệu thủ thư
    public ArrayList<Librarian> loadListLib (){
        ArrayList<Librarian> list = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader("./data/Librarian.csv"))){
            br.readLine();
            String line;
            while( (line = br.readLine()) != null ){
                String[] data = line.split(";");
                Librarian l = new Librarian( data[0], data[1], data[2], data[3], data[4], data[5], data[6], Double.parseDouble(data[7]), data[8]);
                list.add(l);
            }
        }
        catch( IOException e){
            e.printStackTrace();
        }
        return list;
    }
    // hàm cập nhập data thủ thư
    public void updateLib(){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("./data/Librarian.csv"))){
            bw.write("Name;gender;phoneNumber;email;address;librarianID;shift;salary;password");
            bw.newLine();
            for( Librarian l : list){
                bw.write(l.getName() + ";" + l.getGender() + ";" + l.getPhoneNumber() + ";" + l.getEmail() + ";" + l.getAddress() + ";" + l.getLibrarianID() + ";" + l.getShift() + ";" + l.getSalary() + ";" + l.getPassword());
                bw.newLine();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
