package Person;
import dataService.DataService;
import java.util.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class ReaderManager implements DataService{
    private ArrayList<Reader> list = loadListReader();
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
        Scanner sc = new Scanner(System.in);
        do{
            System.out.println("---------------Menu ReaderManager----------------");
            System.out.println("-          1.In danh sach nguoi doc.            -");
            System.out.println("-          2.Tim thong tin nguoi doc.           -");
            System.out.println("-          3.Them nguoi doc.                    -");
            System.out.println("-          4.Xoa nguoi doc.                     -");
            System.out.println("-          0. Thoat.                            -");
            System.out.println("-------------------------------------------------");
            System.out.println("Nhap vao lua chon: ");
            choose  = sc.nextInt();
        }while(choose != 0 );
    }

    // hàm load dữ liệu file Reader 
    public ArrayList<Reader> loadListReader(){
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
    public void updateReader(){
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
