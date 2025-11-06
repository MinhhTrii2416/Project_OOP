import java.util.Scanner;
public class Librarian extends Person {
    private String librarianID;
    private String shift;
    private double salary;
    private String password;
    // set & get
    public void setLibrarianID(String librarianID){this.librarianID = librarianID;}
    public String getLibrarianID(){return this.librarianID;}    
    public void setShift(String shift){this.shift = shift;}
    public String getShift(){return this.shift;}
    public void setSalary(double salary){this.salary = salary;}
    public double getSalary(){return this.salary;}
    public void setPassword(String password){this.password = password;}
    public String getPassword(){return this.password;}
    // constructor  
    public Librarian(){}
    public Librarian(String Name, String gender, String phoneNumber, String email, String address, String librarianID, String shift, double salary, String password){
        super(Name, gender, phoneNumber, email, address);
        this.librarianID = librarianID;     
        this.shift = shift;
        this.salary = salary;
        this.password = password;   
    }
    @Override
    public void showINFO(){
        System.out.println("Thong tin thu thu");
        System.out.println("Name: " + this.Name);
        System.out.println("Gender: " + this.gender);
        System.out.println("Phone Number: " + this.phoneNumber);        
        System.out.println("Email: " + this.email);    
        System.out.println("Address: " + this.address);
        System.out.println("Librarian ID: " + this.librarianID);     
        System.out.println("Shift: " + this.shift);     
        System.out.println("Salary: " + this.salary);
        System.out.println("Password: " + this.password);
    }
    // đăng nhập
    public void Login(){
          
    }
    // làm menu chính cho thủ thư
    public void menuMain(){
        Scanner sc = new Scanner(System.in);
        int lua_chon = 0;
        do{
            System.out.println("1. Quan li nguoi doc.");
            System.out.println("2. Quan li sach.");
            System.out.println("3. Quan li don hang.");
            System.out.println("4. Quan li phieu muon.");
            System.out.println("5. Quan li thu thu.");
            System.out.println("0. Dang xuat.");
            System.out.println("Hay nhap vao lua chon cua ban: ");
            lua_chon = sc.nextInt();
            // lọc lựa chọn
            switch(lua_chon){
                case 1: break;
                case 2: break;
                case 3: break;
                case 4: break;
                case 5: break;
                case 0: System.out.println("Xac nhan thhoat"); return;
                default:
                 System.out.println("Ban da nhap sai hay nhap lai!");
                 lua_chon = -1;
            }
        }while(lua_chon == -1);
    }
}
