import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import Person.Librarian;

public class Main{
    // các biến cục bộ
    private static ArrayList<Librarian> list_librarians = loadListLib("./data/Librarian.csv");
    public static void main( String[] args){
        // login
        Librarian lib = LoginLibrarian();
        if(lib == null) return;
        System.out.println("Chao mung quay lai, "+ lib.getName());
        // show menu
        lib.menuMain();
    }

    // hàm đọc dữ liệu từ file
    public static ArrayList<Librarian> loadListLib (String filename){
        ArrayList<Librarian> list = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(filename))){
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

    // hàm đăng nhập
    public static Librarian LoginLibrarian(){
        int count = 0;
        String librarianID = null, password = null;
        boolean flag = false;
        Scanner sc = new Scanner(System.in);
        while(count<5){
            if(!flag){
                System.out.print("Nhap id: ");
                librarianID = sc.nextLine();
                if(!checkID(librarianID)){
                    System.out.println("Loi Id!");
                    System.out.println("So lan nhap id con lai " + (5-count) + "/5");
                    ++count;

                    if(count<5){ System.out.println("Hay nhap lai id!\n"); }
                    else{
                        System.out.println("\nDA QUA SO LAN DUOC SAI!");
                        return null;
                    }
                    continue;
                }
                else{ flag = true; count = 0;}
            }

            System.out.print("Nhap password: ");
            password = sc.nextLine();
            if(!checkPassword(password, librarianID)){
                System.out.println("Loi password!");
                System.out.println("So lan nhap password con lai " + (5-count) + "/5");
                ++count;
            }
            else{
                System.out.println("\nDANG NHAP THANH CONG!");
                for( Librarian lib: list_librarians){
                    if(lib.getLibrarianID().equals(librarianID)){
                        return lib;
                    }
                }
            }

            if(count<5){ System.out.println("Hay nhap lai password!\n"); }
            else{
                System.out.println("\nDA QUA SO LAN DUOC SAI!");
                return null;
            }
        }
        return null;
    }

    // hàm kiểm tra id
    public static boolean checkID(String librarianID) {
        for(Librarian lib : list_librarians) {
            if(lib.getLibrarianID().equals(librarianID)) {
                return true;  // Tìm thấy ID khớp
            }
        }
        return false;  // Không tìm thấy ID nào khớp
    }

    // hàm kiểm tra password
    public static boolean checkPassword(String password, String librarianID){
        for(Librarian lib: list_librarians) {
            if(lib.getLibrarianID().equals(librarianID)  && lib.getPassword().equals(password)){
                return true;  // Tìm thấy password khớp
            }
        }
        return false;  // Không tìm thấy password nào khớp
    }
}


