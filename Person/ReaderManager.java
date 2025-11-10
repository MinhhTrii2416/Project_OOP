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
            System.out.println("-          5.Sua thong tin nguoi doc.           -");
            System.out.println("-          0. Thoat.                            -");
            System.out.println("-------------------------------------------------");
            System.out.print("Nhap vao lua chon: ");
            choose  = sc.nextInt();
            switch(choose){
                case 1:  showList(); break;
                case 2: search(); break;
                case 3: add(); break;
                case 4: remove(); break;
                case 5: update(); break;
                case 0: System.out.println("Thoat chuc nang quan li nguoi doc!\n"); break;
                default:
                    System.out.println("Khon co lua chon ban nhap hay nhap lai!");
            }
        }while(choose != 0 );
    }

    // hàm hỗ trợ
    public void showList(ArrayList<Reader> list1){
        System.out.printf("| %-10s | %-20s | %-10s | %-40s | %-15s | %-30s |\n", "ID", "Name", "Gender", "Address", "PhoneNumber", "Email");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------");
        for(Reader r : list1){
            System.out.printf("| %-10s | %-20s | %-10s | %-40s | %-15s | %-30s |\n", r.getReaderID(), r.getName(), r.getGender(), r.getAddress(), r.getPhoneNumber(), r.getEmail());
        }
    }
    @Override
    // hàm in danh sách người đọc
    public void showList(){
        System.out.printf("| %-10s | %-20s | %-10s | %-40s | %-15s | %-30s |\n", "ID", "Name", "Gender", "Address", "PhoneNumber", "Email");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------");
        for(Reader r : list){
            System.out.printf("| %-10s | %-20s | %-10s | %-40s | %-15s | %-30s |\n", r.getReaderID(), r.getName(), r.getGender(), r.getAddress(), r.getPhoneNumber(), r.getEmail());
        }
    }

    // hàm tìm thông tin người đọc
    public void search(){
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
                System.out.println("---------------THONG TIN NGUOI DOC-------------");
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
    public void add(){
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
    public void remove(){
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
                System.out.println("----------------THONG TIN NGUOI DOC---------------------");
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

    // hàm update thông tin người đọc
    private void update(){
        String id = null;
        boolean flag = false;
        sc.nextLine();
        while(!flag){
            System.out.print("Nhap vao ma nguoi doc ban muon sua: ");
            id = sc.nextLine();
            if(checkID(id)){
                flag = true;
            }
            else{
                System.out.println("Khong tim thay ma nguoi doc nay!");
                return;
            }
        }

        Reader ur = new Reader();
        for(Reader r: list){
            if( r.getReaderID().equals(id)){
                ur = r;
            }
        }
        // in menu chinh sua
        int choose = -1;
        while(choose!=0){
            System.out.println("------------------UPDATE READER-----------------");
            System.out.println("-              1.Sua ma nguoi doc.             -");
            System.out.println("-              2.Sua ten nguoi doc.            -");
            System.out.println("-              3.Sua gioi tinh nguoi doc.      -");
            System.out.println("-              4.Sua dia chi nguoi doc.        -");
            System.out.println("-              5.Sua so dien thoai nguoi doc.  -");
            System.out.println("-              6.Sua email nguoi doc.          -");
            System.out.println("-              0.Thoat.                        -");
            System.out.println("------------------------------------------------");
            System.out.print("Nhap vao lua chon cua ban: ");
            choose = sc.nextInt();
            sc.nextLine();
            switch(choose){
                case 0: break;
                case 1:
                    updateReaderID(ur); 
                    break;
                case 2:
                    updateName(ur); 
                    break;
                case 3: 
                    updateGender(ur);
                    break;
                case 4:
                    updateAddress(ur); 
                    break;
                case 5:
                    updatePhoneNumber(ur); 
                    break;
                case 6: 
                    updateEmail(ur);
                    break;
                default: 
                    System.out.println("Khong co lua chon nay hay nhap lai!");
            }
            updateReader();
        }
    }
    // update ma
    private void updateReaderID(Reader ur){
        String id;
        boolean flag = false;
        while(!flag){
            System.out.print("Nhap vao ma ban muon sua: ");
            id = sc.nextLine();
            if(checkID(id)){
                System.out.println("Ma id nay da ton tai hay sua thanh ma id khac!");
            }
            else{
                ur.setReaderID(id);
                flag = true;
            }
        }
    }
    //update ten
    private void updateName(Reader ur){
        String name;
        System.out.print("Hay nhap ten ban muôn sua: ");
        name = sc.nextLine();
        ur.setName(name);
    }
    // update sua gioi tinh
    private void updateGender(Reader ur){
        String gender;
        System.out.print("Nhap vao gioi tinh ban muon sua: ");
        gender = sc.nextLine();
        ur.setGender(gender);
    }
    // update dia chi
    private void updateAddress(Reader ur){
        String address;
        System.out.print("Nhap vao dia chi moi ban muon sua: ");
        address = sc.nextLine();
        ur.setAddress(address);
    }
    // update so dien thoai 
    private void updatePhoneNumber(Reader ur){
        String phoneNumber;
        System.out.print("Nhap vao so dien thoai ban muon sua: ");
        phoneNumber = sc.nextLine();
        ur.setPhoneNumber(phoneNumber);
    }
    // update email
    private void updateEmail(Reader ur){
        String email;
        System.out.print("Nhap vao email moi: ");
        email = sc.nextLine();
        ur.setEmail(email);
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
