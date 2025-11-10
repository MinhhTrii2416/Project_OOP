package Person;
import java.util.Scanner;
import Bill.*;
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
        int lua_chon = -1;
        do{
            System.out.println("----------------MENU----------------------");
            System.out.println("-       1. Quan li nguoi doc.            -");
            System.out.println("-       2. Quan li sach.                 -");
            System.out.println("-       3. Quan li don hang.             -");
            System.out.println("-       4. Quan li phieu muon.           -");
            System.out.println("-       5. Quan li thu thu.              -");
            System.out.println("-       6. Doi password.                 -");
            System.out.println("-       7. Xem thong tin ca nhan.        -");
            System.out.println("-       0. Dang xuat.                    -");
            System.out.println("------------------------------------------");
            System.out.print("Hay nhap vao lua chon: ");
            lua_chon = sc.nextInt();
            // try {
            //     lua_chon = Integer.parseInt(s); // chuyển sang int
            // } catch (NumberFormatException e) {
            //     System.out.println("Loi! Ban phai nhap so nguyen!");
            // }
            // lọc lựa chọn
            switch(lua_chon){
                case 1:
                    ReaderManager RM = new ReaderManager();
                    RM.menu(); 
                    break;
                case 2:
                    BookManager BM = new BookManager();
                    BM.menu();
                    break;
                case 3:
                    BillManager BM1 = new BillManager(); 
                    BM1.menu();
                    break;
                case 4:
                    LoanManager LM1 = new LoanManager();
                    LM1.menu(); 
                    break;
                case 5: 
                    LibrarianManager LM = new LibrarianManager(); 
                    LM.menu();
                    break;
                case 6:
                    updatePassword();
                    break;
                case 7:
                    System.out.println("----------------THONG TIN CA NHAN CUA THU THU----------------");
                    showINFO();
                    break;
                case 0: 
                    System.out.println("Xac nhan thoat"); 
                    return;
                default:
                    System.out.println("Ban da nhap sai hay nhap lai!");
                    lua_chon = -1;
            }
        }while(lua_chon != 0);
    }

    // hàm đổi password thủ thư
    private void updatePassword(){
        Scanner sc = new Scanner(System.in);
        String passwordOld = null;
        String password1 = null, password2 = null;
        // xac nhan mat khau cu
        int count = 0; // gioi han nhap mat khau cu 5 lan
        while(count<5){
            System.out.print("Nhap vao password cu cua ban: ");
            passwordOld = sc.nextLine();
            if(checkPassword(passwordOld)){
                System.out.println("Khop voi mat khau cu!");
                break;
            }
            else{
                ++count;
                System.out.println("Canh bao sai mat khau! Hay nhap lai! Con "+(5-count)+"/5 lan nhap!");
            }
        }

        // tạo mật khẩu mới
        if(count<5){    // Kiểm tra xem nhập password cũ có đúng không
            count = 0;
            while(count<5){
                System.out.print("Nhap vao password moi: ");
                password1 = sc.nextLine();
                System.out.print("Xac nhan password moi: ");
                password2 = sc.nextLine();
                if( password1.equals(password2) ){
                    System.out.println("Chuc mung ban da doi mat khau thanh cong!");
                    setPassword(password1);

                    // cập nhập vô file csv
                    LibrarianManager LM = new LibrarianManager();
                    for(Librarian l: LM.list){
                        System.out.println("TEST!");
                        if(l.getLibrarianID().equals(this.librarianID)){
                            l.setPassword(password1);
                            break;
                        }
                    }
                    LM.updateLib();
                    break;
                }
                else{
                    ++count;
                    System.out.println("Hai lan nhap khong khop! Hay nhap lai! Con "+(5-count)+"/5 lan nhap!");
                }
            }
        }
    }
    // check password cũ
    private boolean checkPassword(String id){
        if( id.equals(this.password) ){
            return true;
        }
        else return false;
    }
    
}
