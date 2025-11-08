package Person;
import dataService.DataService;
import java.util.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class ReaderManager implements DataService{
    private ArrayList<Reader> list = loadListReader();
    Scanner sc = new Scanner(System.in);
    // set & get
    public void setList(ArrayList<Reader> list){
        this.list = list;
    }
    public ArrayList<Reader> getList(){
        return this.list;
    }
    // menu
    public void menu(){
        int choose  = 0;
        do{
            System.out.println("---------------Menu ReaderManager----------------");
            System.out.println("-          1.In danh sach nguoi doc.            -");
            System.out.println("-          2.Tim thong tin nguoi doc.           -");
            System.out.println("-          3.Them nguoi doc.                    -");
            System.out.println("-          4.Xoa nguoi doc.                     -");
            System.out.println("-          0. Thoat.                            -");
            System.out.println("-------------------------------------------------");
            System.out.print("Nhap vao lua chon: ");
            choose  = sc.nextInt();
            switch(choose){
                case 1:  showList(list); break;
                case 2: search(); break;
                case 3: add(); break;
                case 4: remove(); break;
                case 0: System.out.println("Thoat chuc nang quan li nguoi doc!\n"); break;
                default:
                    System.out.println("Khon co lua chon ban nhap hay nhap lai!");
            }
        }while(choose != 0 );
    }

    // hàm in danh sách người đọc
    private void showList(ArrayList<Reader> list1){
        System.out.printf("| %-10s | %-20s | %-10s | %-40s | %-15s | %-30s |\n", "ID", "Name", "Gender", "Address", "PhoneNumber", "Email");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------");
        for(Reader r : list1){
            System.out.printf("| %-10s | %-20s | %-10s | %-40s | %-15s | %-30s |\n", r.getReaderID(), r.getName(), r.getGender(), r.getAddress(), r.getPhoneNumber(), r.getEmail());
        }
    }

    // hàm tìm thông tin người đọc
    private void search(){
        int choose = -1;
        while(choose!=0){
            System.out.println("----------------Search Reader-------------");
            System.out.println("-                1.Theo ma.              -");
            System.out.println("-                2.Theo ten.             -");
            System.out.println("-                0.Thoat.                -");
            System.out.println("------------------------------------------");
            System.out.print("Ban muon tim kiem nguoi doc theo gi: ");
            choose = sc.nextInt();
            switch(choose){
                case 0: break;
                case 1: SearchID(); break;
                case 2: SearchName(); break;
                default: 
                    System.out.println("Khong co lua chon nao nhu vay hay nhap lai!");
            }
        }
    }
    // tìm kiếm theo mã
    private void SearchID(){
        String readerID;
        sc.nextLine();
        System.out.print("Hay nhap vao ma ban muon tim: ");
        readerID = sc.nextLine();
        for( Reader r: list){
            if(r.getReaderID().equals(readerID)){
                r.showINFO();
                return;
            }
        }
        System.out.println("Khong tim thay nguoi doc nao co ma nay!");
    }

    // tìm kiếm theo tên
    private void SearchName(){
        String name;
        ArrayList<Reader> kq = new ArrayList<>();
        sc.nextLine();
        System.out.print("Hay nhap vao ten ban muon tim: ");
        name = sc.nextLine();
        boolean flag = false;
        for( Reader r: list){
            if(r.getName().equals(name)){
                kq.add(r);
                flag = true;
            }
        }
        if(!flag){
            System.out.println("Khong tim thay nguoi doc nao co ten nay!");
        }
        else{
            showList(kq);
        }
    }

    // hàm thêm người đọc
    private void add(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Hay nhap vao cac thong tin nguoi doc moi");
        String readerID, name, gender, address, phoneNumber, email;
        boolean flag = false;
        do{
            System.out.print("Nhap vao ID can tao: ");
            readerID = sc.nextLine();
            if(!checkID(readerID)){
                flag = true;
            }
            else{
                System.out.println("Id nay da co hay nhap lai!");
            }
        }while(!flag);
        System.out.print("Nhap vao ten nguoi doc: ");
        name = sc.nextLine();
        System.out.print("Nhap vao gioi tinh nguoi doc: ");
        gender = sc.nextLine();
        System.out.print("Nhap vao dia chi cua nguoi doc: ");
        address = sc.nextLine();
        System.out.print("Nhap vao so dien thoai nguoi doc: ");
        phoneNumber = sc.nextLine();
        System.out.print("Nhap vao email nguoi doc: ");
        email = sc.nextLine();
        System.out.printf("| %-10s | %-20s | %-10s | %-40s | %-15s | %-30s |\n", readerID, name, gender, address, phoneNumber, email);
        int xac_nhan = -1;
        while(xac_nhan != 0){
            System.out.print("Neu muon them nguoi doc nay hay bam 1 va bam 0 neu khong muon them: ");
            xac_nhan = sc.nextInt();
            switch(xac_nhan){
                case 0:
                    System.out.println("Xac nhan huy nguoi doc moi nay!");
                    return;
                case 1:
                    System.out.println("Xac nhan them nguoi doc nay vo danh sach!");
                    Reader newReader = new Reader(readerID, name, gender, address, phoneNumber, email);
                    list.add(newReader);
                    updateReader();
                    return;
                default: 
                    System.out.println("Loi! Hay nhap lai!");
            }
        }
    }

    // hàm kiểm tra xem mã người đọc có bị trùng không
    private boolean checkID(String readerID){
        for( Reader r: list){
            if(r.getReaderID().equals(readerID)){
                return true;
            }
        }
        return false;
    }

    // hàm xóa người đọc
    private void remove(){
        int choose = -1;
        while(choose!=0){
            System.out.println("----------------Remove Reader-------------");
            System.out.println("-                1.Theo ma.              -");
            System.out.println("-                2.Theo ten.             -");
            System.out.println("-                0.Thoat.                -");
            System.out.println("------------------------------------------");
            System.out.print("Ban muon xoa nguoi doc theo gi: ");
            choose = sc.nextInt();
            switch(choose){
                case 0: break;
                case 1: RemoveID(); break;
                case 2: RemoveName(); break;
                default: 
                    System.out.println("Khong co lua chon nao nhu vay hay nhap lai!");
            }
        }
    }
    // xóa theo id
    private void RemoveID(){
        sc.nextLine();
        String id;
        System.out.print("Hay nhap id ban muon xoa: ");
        id = sc.nextLine();
        for(Reader r: list){
            if(r.getReaderID().equals(id)){
                r.showINFO();
                int xac_nhan = -1;
                while(xac_nhan != 0){
                System.out.print("Neu muon xoa nguoi doc nay hay bam 1 va bam 0 neu khong muon them: ");
                xac_nhan = sc.nextInt();
                    switch(xac_nhan){
                        case 0:
                            System.out.println("Xac nhan huy xoa nguoi doc nay!");
                            return;
                        case 1:
                            System.out.println("Xac nhan xoa nguoi doc nay khoai danh sach!");
                            list.remove(r);
                            updateReader();
                            return;
                        default: 
                            System.out.println("Loi! Hay nhap lai!");
                    }
                }
                return;
            }
        }
        System.out.println("Khong tim thay nguoi doc nao co id nay!");
    }

    // xóa theo id
    private void RemoveName(){
        sc.nextLine();
        String name;
        ArrayList<Reader> kq = new ArrayList<>();
        boolean flag = false;
        System.out.print("Hay nhap ten ban muon xoa: ");
        name = sc.nextLine();
        for(Reader r: list){
            if(r.getName().equals(name)){
                flag = true;
                kq.add(r);
            }
        }
        if(!flag){
            System.out.println("Khong tim thay nguoi doc nao co ten nay!");
            return;
        }
        else{
            System.out.println("So luong nguoi ten "+name+" tim thay duoc la: "+kq.size());
            showList(kq);
            boolean flag1 = false;
            int choose1;
            while(!flag1){
                System.out.println("----------------Select Remove-----------------");
                System.out.println("-               1.Xoa tat ca.                -");
                System.out.println("-            2.Xoa theo ma ban chon.         -");
                System.out.println("----------------------------------------------");
                System.out.print("Hay nhap vao lua chon cua ban: ");
                choose1 = sc.nextInt();
                switch(choose1){
                    case 1:
                        int xac_nhan = -1;
                        while(xac_nhan != 0){
                            System.out.print("Neu muon xoa tat ca nguoi doc nay hay bam 1 va bam 0 neu khong muon them: ");
                            xac_nhan = sc.nextInt();
                            switch(xac_nhan){
                            case 0:
                                System.out.println("Xac nhan huy xoa tat ca nguoi doc nay!");
                                return;
                            case 1:
                                System.out.println("Xac nhan xoa tat ca nguoi doc nay khoai danh sach!");
                                list.removeAll(kq);
                                updateReader();
                                return;
                            default: 
                                System.out.println("Loi! Hay nhap lai!");
                            }
                        }
                        flag1 = true;
                        break;
                    case 2:
                        int xac_nhan1 = -1;
                        //chọn mã cần xóa trong bảng tìm đc
                        String selectID;
                        Reader RemoveReader = new Reader();
                        sc.nextLine();
                        boolean flag2 = false;
                        while(!flag2){
                            System.out.print("Hay nhap vao ma muon xoa o bang tren: ");
                            selectID = sc.nextLine();
                            for(Reader r: list){
                                if(r.getReaderID().equals(selectID)){
                                    flag2 = true;
                                    RemoveReader = r;
                                }
                            }
                            if(!flag2){
                                System.out.println("Khong tim thay id nay o bang tren hay nhap lai!");
                            }
                        }
                        while(xac_nhan1 != 0){
                            System.out.print("Neu muon xoa nguoi doc nay hay bam 1 va bam 0 neu khong muon them: ");
                            xac_nhan1 = sc.nextInt();
                            switch(xac_nhan1){
                            case 0:
                                System.out.println("Xac nhan huy xoa nguoi doc nay!");
                                return;
                            case 1:
                                System.out.println("Xac nhan xoa nguoi doc nay khoai danh sach!");
                                list.remove(RemoveReader);
                                updateReader();
                                return;
                            default: 
                                System.out.println("Loi! Hay nhap lai!");
                            }
                        }
                        flag = true;
                        break;
                    default:
                        System.out.println("Loi! Hay nhap lai!");
                }
            }
        }
    }


    // hàm load dữ liệu file Reader 
    private ArrayList<Reader> loadListReader(){
        ArrayList<Reader> list = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader("./data/Reader.csv"))){
            br.readLine();
            String line;
            while( (line = br.readLine()) != null ){
                String[] data = line.split(",");
                Reader r = new Reader( data[0], data[1], data[2], data[3], data[4], data[5]);
                list.add(r);
            }
        }
        catch( IOException e){
            e.printStackTrace();
        }
        return list;
    }
    // hàm cập nhập dữ liệu file Librarian
    private void updateReader(){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("./data/Reader.csv"))){
        bw.write("readerID,Name,gender,address,phoneNumber,email");
        bw.newLine();
        for(Reader r : list){
            bw.write(r.getReaderID() + "," + r.getName() + "," + r.getGender() + "," + r.getAddress() + "," + r.getPhoneNumber() + "," + r.getEmail());
            bw.newLine();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
