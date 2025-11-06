import Person.*;
import java.util.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import Person.Librarian;
public class Main{
    public static void main( String[] args){
        Librarian lib = LoginLibrarian();
        lib.menuMain();
    }

    // hàm load dữ liệu
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
        boolean flag = false;
        do{
            System.out.print("Nhap vao id cua thu thu: ");
            librarianID = sc.nextLine();
            System.out.print("Nhap vao password: ");
            password = sc.nextLine();
            if(CheckPassword(librarianID, password)){
                flag = true;
            }
        }while(!flag);
        ArrayList<Librarian> list = new ArrayList<>();
        list = loadListLib("./data/Librarian.csv");
        for(int i=0; i<list.size(); i++){
            if( list.get(i).getLibrarianID().equals(librarianID)){
                return list.get(i);
            }
        } 
        return null;  
    }


    // hàm kiểm tra password
    public static boolean CheckPassword(String librarianID, String password){
        ArrayList<Librarian> list = new ArrayList<>();
        list = loadListLib("./data/Librarian.csv");
        for( int i=0; i<list.size(); i++){
            if( list.get(i).getLibrarianID().equals(librarianID)){
                if( list.get(i).getPassword().equals(password)){
                    System.out.println("Dang nhap thanh cong");
                    return true;
                }
                else{
                    System.out.println("BAN NHAP SAI PASSWORD");
                    return false;
                }
            }
        }
        System.out.println("BAN NHAP SAI TEN TAI KHOAN");
        return false;
    }
}


