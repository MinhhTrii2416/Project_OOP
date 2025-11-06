import Person.*;
import java.util.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import Person.Librarian;
public class Main{


// các biến cục bộ

    private static ArrayList<Librarian> list_librarians = loadListLib("./data/Librarian.csv");



    public static void main( String[] args){

        for(Librarian librarian : list_librarians) {
            librarian.showINFO();
            System.out.println();
        }

        // login
        Librarian lib = LoginLibrarian();


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
                String[] data = line.split(",");
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
        String librarianID;
        String password;
        Scanner sc = new Scanner(System.in);
        int count = 1;
        boolean flag = false;
        do{
            System.out.print("Nhap vao id cua thu thu: ");
            librarianID = sc.nextLine();
            if(!checkID(librarianID)){
                System.out.println("Khong tim thay id nay! Try again\n");
                count ++;
                continue;
            }


            System.out.print("Nhap vao password: ");
            password = sc.nextLine();
            if(CheckPassword(password)){
                System.out.println("Dang nhap thanh cong!!!\n");
                flag = true;
            }
            else {
                System.out.println("Mat khau khong dung! Try again\n");        
                count ++;
            }


            if(count >= 5) {
                flag = true;
            }
        }while(!flag);
        for(int i=0; i<list_librarians.size(); i++){
            if( list_librarians.get(i).getLibrarianID().equals(librarianID)){
                return list_librarians.get(i);
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
    public static boolean CheckPassword(String password){
        for(Librarian lib: list_librarians) {
            if(lib.getPassword().equals(password)) {
                return true;  // Tìm thấy password khớp
            }
        }
        return false;  // Không tìm thấy password nào khớp
    }
}


